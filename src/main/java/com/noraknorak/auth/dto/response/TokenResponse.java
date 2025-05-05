package com.noraknorak.auth.dto.response;

import com.noraknorak.user.domain.User;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String userName
) {
    public static TokenResponse of(String accessToken, String refreshToken, User user) {
        return new TokenResponse(accessToken, refreshToken, user.getName());
    }
}
