package com.noraknorak.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoginReqeust(
        @Schema(description = "전화번호 특수문자 제외 11글자")
        @NotNull(message = "전화번호는 필수 입력 필드입니다.")
        String phone
) {}
