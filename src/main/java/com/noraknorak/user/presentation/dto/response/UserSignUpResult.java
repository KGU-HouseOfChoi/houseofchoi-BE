package com.noraknorak.user.presentation.dto.response;

import com.noraknorak.user.domain.User;

public record UserSignUpResult(
        User user,
        boolean isNewUser
) {}