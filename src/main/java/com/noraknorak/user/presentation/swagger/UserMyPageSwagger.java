package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name="User", description = "유저 정보 관련 컨트롤러")
public interface UserMyPageSwagger {
    @Operation(
            summary = "마이페이지 조회 API",
            description = "JWT 토큰을 통해 로그인한 사용자의 마이페이지 정보를 조회합니다.",
            operationId = "v1/user/mypage"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<UserMyPageResponse>> getMyPage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(
            summary = "로그인한 유저 이름 조회 API",
            description = "JWT 토큰을 통해 로그인한 사용자의 이름만 조회합니다.",
            operationId = "v1/user/name"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<String>> getUserName(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

}
