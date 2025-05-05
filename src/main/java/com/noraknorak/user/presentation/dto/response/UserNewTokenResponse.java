package com.noraknorak.user.presentation.dto.response;

public record UserNewTokenResponse(
        String accessToken,
        String refreshToken
) {

    public static UserNewTokenResponse of(String accessToken, String refreshToken) {
        return new UserNewTokenResponse(accessToken, refreshToken);
    }

}
