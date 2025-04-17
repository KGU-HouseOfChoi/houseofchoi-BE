package com.noraknorak.user.presentation.dto.response;

import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;

public record UserSignUpResponse(
        String accessToken,
        Long userId,
        String name,
        String birth,
        String gender,
        String userCode,
        Role role
) {
    public static UserSignUpResponse from(String accessToken, User user) {
        return new UserSignUpResponse(
                accessToken,
                user.getId(),
                user.getName(),
                user.getBirth(),
                user.getGender(),
                user.getUserCode(),
                user.getRole()
        );
    }
}
