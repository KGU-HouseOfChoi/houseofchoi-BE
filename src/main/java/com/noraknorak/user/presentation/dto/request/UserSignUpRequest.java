package com.noraknorak.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserSignUpRequest(
    @NotBlank(message = "")
    String name,

    @NotBlank(message = "")
    String phone,

    @NotBlank(message = "")
    String birth,

    @Pattern(regexp = "\\d{6}", message = "인증번호는 6자리 숫자입니다.")
    @NotBlank(message = "인증번호를 입력해야 합니다.")
    String code
){}
