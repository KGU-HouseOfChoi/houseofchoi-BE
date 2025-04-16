package com.noraknorak.auth.presentation;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "로그인/회원가입 관련 컨트롤러")
public interface LogoutSwagger {
    @Operation(
            summary = "로그아웃 API",
            description = "사용자가 로그아웃을 진행합니다.",
            operationId = "v1/auth/logout"
    )
    @ApiErrorCode({})
    ResponseEntity<RestResponse<Void>> logout();
}
