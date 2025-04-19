package com.noraknorak.sms.presentaion.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Map;

public record SmsRequest (

        @Schema(description = "인증 문자를 보내고자 하는 전화번호 ( - 제외 )")
        @NotNull(message = "휴대폰 번호를 입력해주세요")
        @Pattern(regexp = "^01[016789]\\d{3,4}\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다")
        String phoneNum
){
    public static Map<String, Object> swaggerExample(){
        return Map.of(
                "phoneNum", "01012345678"
        );
    }
}
