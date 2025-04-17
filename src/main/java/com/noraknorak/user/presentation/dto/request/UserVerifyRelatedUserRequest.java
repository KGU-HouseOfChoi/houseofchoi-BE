package com.noraknorak.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserVerifyRelatedUserRequest(

        @NotNull(message = "부모/자식 정보를 입력해 주세요")
        @Schema(description = "부모")
        String role,

        @NotNull(message = "인증 코드를 입력해 주세요")
        @Schema(description = "6자리 글자")
        String code
) {
}
