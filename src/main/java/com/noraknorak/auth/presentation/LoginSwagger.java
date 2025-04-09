package com.noraknorak.auth.presentation;

import com.noraknorak.auth.dto.request.LoginRequest;
import com.noraknorak.auth.dto.response.TokenResponse;
import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name="auth", description = "로그인/회원가입 관련 컨트롤러")
public interface LoginSwagger {
    @Operation(
            summary = "로그인 API",
            description = "전화번호를 입력받아 로그인을 수행한 후, 토큰 값을 저장합니다.",
            operationId = "auth/login"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest);
}
