package com.noraknorak.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserVerifyRelatedUserRequest(
        // TODO: JWT 적용시 이 부분은 사라질 예정
        @NotNull(message = "현재 내 정보를 입력해주세요")
        @Schema(description = "현재 등록을 하고 있는 자신의 DB 고유 ID")
        Long id,
        
        @NotNull(message = "부모/자식 정보를 입력해 주세요")
        @Schema(description = "부모")
        String role,

        @NotNull(message = "인증 코드를 입력해 주세요")
        @Schema(description = "6자리 글자")
        String code
) {
}
