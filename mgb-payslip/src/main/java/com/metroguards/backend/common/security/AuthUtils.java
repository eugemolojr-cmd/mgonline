package com.metroguards.backend.common.security;

import org.springframework.security.core.Authentication;

public final class AuthUtils {
    private AuthUtils() {}

    public static boolean isHrAdmin(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HR_ADMIN"));
    }
}
