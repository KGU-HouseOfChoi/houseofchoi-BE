package com.noraknorak.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserVerifyCodeRequest {
    @Schema(description = "인증 문자를 보내고자하는 전화번호")
    @NotEmpty(message = "휴대폰 번호를 입력해주세요")
    @Pattern(regexp = "^01[016789]\\d{3,4}\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다")
    private String phoneNum;

    @Schema(description = "인증 코드")
    @NotEmpty(message = "인증 코드를 입력해주세요")
    private String code;
}
