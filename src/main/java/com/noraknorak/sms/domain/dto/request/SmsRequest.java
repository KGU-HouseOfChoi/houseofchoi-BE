package com.noraknorak.sms.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class SmsRequest {
    @NotEmpty(message = "휴대폰 번호를 입력해주세요")
    private String phoneNum;
}
