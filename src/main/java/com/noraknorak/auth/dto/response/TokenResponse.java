package com.noraknorak.auth.dto.response;

import com.noraknorak.user.domain.User;

public record TokenResponse(
        String accessToken,
//        String refreshToken,
        
        // TODO: 프론트와 상의 후 무슨 정보 줄지 정하기
        User user
) {
    public static TokenResponse of(String accessToken, User user) {
        return new TokenResponse(accessToken, user);
    }
}
