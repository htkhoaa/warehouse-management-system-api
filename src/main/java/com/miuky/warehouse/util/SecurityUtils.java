package com.miuky.warehouse.util;

import com.miuky.warehouse.domain.entity.User;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Function;

public class SecurityUtils {

    public static String getUsername() {
        return String.valueOf(get(CustomUserDetails::getUsername));
    }

    public static String getFullName() {
        return String.valueOf(get(CustomUserDetails::getFullName));
    }

    public static User getUser() {
        return (User) get(CustomUserDetails::getUser);
    }

    private static Object get(Function<CustomUserDetails, Object> func) {
        CustomUserDetails userDetails = getUserDetails();
        return func.apply(userDetails);
    }

    private static CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) throw new AppException(ErrorCode.UNAUTHENTICATED);
        return (CustomUserDetails) auth.getPrincipal();
    }
}




