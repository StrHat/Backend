package com.konkuk.strhat.global.util;

import com.konkuk.strhat.domain.user.entity.CustomUserDetails;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundUserException();
        }

        if (!(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new NotFoundUserException();
        }

        return customUserDetails.getId();
    }

    public static Long getCurrentUserKakaoId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundUserException();
        }

        if (!(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new NotFoundUserException();
        }

        return customUserDetails.getKakaoId();
    }
}

