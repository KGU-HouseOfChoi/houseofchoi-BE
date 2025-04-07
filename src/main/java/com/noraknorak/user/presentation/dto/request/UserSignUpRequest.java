package com.noraknorak.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequest(
    @NotBlank(message = "")
    String name,

    @NotBlank(message = "")
    String phone,

    @NotBlank(message = "")
    String birth
){}
