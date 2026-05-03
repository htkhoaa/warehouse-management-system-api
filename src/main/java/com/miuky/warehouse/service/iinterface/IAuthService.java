package com.miuky.warehouse.service.iinterface;

import com.miuky.warehouse.domain.dto.auth.LoginRequest;
import com.miuky.warehouse.domain.dto.auth.PasswordChangeRequest;
import com.miuky.warehouse.domain.dto.auth.RegisterRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;

public interface IAuthService {
    ApiResponse<?> login(LoginRequest req);

    ApiResponse<?> register(RegisterRequest req);

    ApiResponse<?> changePassword(PasswordChangeRequest req);
}
