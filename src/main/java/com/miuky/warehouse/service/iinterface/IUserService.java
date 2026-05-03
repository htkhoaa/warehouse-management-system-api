package com.miuky.warehouse.service.iinterface;

import com.miuky.warehouse.domain.dto.common.ApiResponse;

public interface IUserService {
    ApiResponse<?> getAllUsers();

    ApiResponse<?> getUserById(Long id);

    ApiResponse<?> getMyProfile();
}
