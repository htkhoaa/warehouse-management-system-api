package com.miuky.warehouse.service.impl;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.user.UserResponse;
import com.miuky.warehouse.domain.entity.User;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.repository.UserRepository;
import com.miuky.warehouse.service.iinterface.IUserService;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepo;

    public ApiResponse<?> getAllUsers() {
        List<UserResponse> res = userRepo.findAll().stream().map(UserResponse::from).toList();
        return ApiResponse.success(res);
    }

    public ApiResponse<?> getUserById(Long id) {
        User userToFind = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return ApiResponse.success(UserResponse.from(userToFind));
    }

    public ApiResponse<?> getMyProfile() {
        String username = SecurityUtils.getUsername();
        User currUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return ApiResponse.success(UserResponse.from(currUser));
    }
}
