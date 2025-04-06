package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.exception.GlobalErrorCode;
import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserSwagger {
    @Operation(
            summary = "회원가입 API",
            description = "사용자로부터 회원 정보를 입력받아 회원가입을 진행합니다."
    )
    @ApiErrorCode(GlobalErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest);
}
