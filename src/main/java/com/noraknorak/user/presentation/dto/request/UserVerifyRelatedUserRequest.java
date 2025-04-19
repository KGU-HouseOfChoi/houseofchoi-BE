package com.noraknorak.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Map;

public record UserVerifyRelatedUserRequest(

        @NotNull(message = "부모/자식 정보를 입력해 주세요")
        @Pattern(regexp = "^(부모|자식)$", message = "role은 '부모' 또는 '자식'이어야 합니다.")
        @Schema(description = "현재 로그인 한 사용자의 역할(부모 OR 자식)")
        String role,

        @NotNull(message = "인증 코드를 입력해 주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "인증 코드는 6자리 영문자 또는 숫자여야 합니다.")
        @Schema(description = "화면에 보여지는 예시 코드 6자리 문자열")
        String code
) {
        public static Map<String, Object> swaggerExample(){
                return Map.of(
                        "role", "부모",
                        "code", "45a2u9"
                );
        }
}
