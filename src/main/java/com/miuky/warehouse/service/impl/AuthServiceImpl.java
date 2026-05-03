package com.miuky.warehouse.service.impl;

import com.miuky.warehouse.domain.dto.auth.*;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.user.UserResponse;
import com.miuky.warehouse.domain.entity.Role;
import com.miuky.warehouse.domain.entity.User;
import com.miuky.warehouse.exception.AppException;
import static com.miuky.warehouse.exception.ErrorCode.*;

import com.miuky.warehouse.jwt.JwtService;
import com.miuky.warehouse.repository.RoleRepository;
import com.miuky.warehouse.repository.UserRepository;
import com.miuky.warehouse.security.CustomUserDetails;
import com.miuky.warehouse.service.iinterface.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepo;

    @Transactional @Override
    public ApiResponse<?> register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) throw new AppException(USERNAME_ALREADY_EXISTED);
        Role staffRole = roleRepo.findByName("STAFF").orElseThrow(() -> new AppException(ROLE_NOT_FOUND));

        User newUser = User.builder().username(req.username()).fullName(req.fullName())
                .password(encoder.encode(req.password())).role(staffRole).build();
        userRepo.save(newUser);

        String token = jwtService.generateToken(req.username());
        return ApiResponse.success(new AuthResponse(req.fullName(), token), "Create successfully");
    }

    @Override
    public ApiResponse<?> login(LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username(), req.password()));
            SecurityContextHolder.getContext().setAuthentication(auth);

            String fullName = ((CustomUserDetails) auth.getPrincipal()).getFullName();
            String token = jwtService.generateToken(req.username());

            return ApiResponse.success(new AuthResponse(fullName, token));
        } catch (BadCredentialsException ex) {
            throw new AppException(INVALID_CREDENTIALS);
        }
    }

    @Transactional @Override
    public ApiResponse<?> changePassword(PasswordChangeRequest req) {
        System.out.println(req.username());
        User currUser = userRepo.findByUsername(req.username()).orElseThrow(() -> new AppException(USER_NOT_FOUND));

        if (!encoder.matches(req.currentPassword(), currUser.getPassword())){
            throw new AppException(INVALID_CREDENTIALS);
        }

        currUser.setPassword(encoder.encode(req.newPassword()));
        return ApiResponse.success(null, "Change password successfully");
    }
}
