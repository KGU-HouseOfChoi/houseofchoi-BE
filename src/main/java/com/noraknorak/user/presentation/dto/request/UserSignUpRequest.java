package com.noraknorak.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Map;

public record UserSignUpRequest(

    // TODO: 로그인시에 어떻게 하느냐에 따라서 유효성 값이 달라짐.

    @Schema(name = "사용자 이름", description = "홍길동 -> 이런 이름 3자")
    String name,

    @Pattern(regexp = "^(010|011|016|017|018|019)\\d{7,8}$",
             message = "전화번호 형식이 잘못되었습니다.")
    @NotBlank(message = "전화번호를 입력하셔야 합니다.")
    @Schema(name = "전화번호", description = "01012345678 (전화번호 010 포함 '-' 제외")
    String phone,

    @Schema(name = "주민번호", description = "주민번호앞자리 6개 및 뒷자리 1개 총 7자리 '-'은 제외")
    String birth,

    @Pattern(regexp = "\\d{6}", message = "인증번호는 6자리 숫자입니다.")
    @NotBlank(message = "인증 코드를 입력해야 합니다.")
    @Schema(name = "인증 코드", description = "인증번호 정수 6자리 숫자")
    String code
){
    public static Map<String, Object> swaggerExample() {
        return Map.of(
                "name","홍길동",
                "phone", "01012345678",
                "birth","5101012",
                "code","123456"
        );
    }
}
