package com.noraknorak.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @Schema(description = "전화번호 특수문자 제외 11글자")
        @NotNull(message = "전화번호는 필수 입력 필드입니다.")
        @Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 특수문자 없이 숫자 11자리여야 합니다.")
        String phone
) {}
