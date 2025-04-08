package com.noraknorak.sms.presentaion;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.sms.exception.SmsErrorCode;
import com.noraknorak.sms.presentaion.request.SmsRequest;
import com.noraknorak.user.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "SMS", description = "SMS 인증 관련 컨트롤러")
public interface SmsSwagger {
    @Operation(
            summary = "문자 인증 코드 전송 API",
            description = "문자 인증을 위한 인증 코드를 발송합니다." +
                    "가입시 입력한 휴대폰 번호로 인증 문자를 전송합니다.",
            operationId = "sms/send"
    )
    @ApiErrorCode(SmsErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> send(@Valid @RequestBody SmsRequest request) throws Exception;
}
