package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;
import com.noraknorak.user.presentation.dto.response.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import io.swagger.v3.oas.annotations.Parameter;


@Tag(name = "Auth", description = "로그인/회원가입 관련 컨트롤러")
public interface UserRegisterSwagger {
    @Operation(
            summary = "회원가입 API",
            description = "사용자로부터 회원 정보를 입력받아 회원가입을 진행합니다.",
            operationId = "v1/auth/sign-up"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<UserSignUpResponse>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest);

    @Operation(
            summary = "문자 인증 API",
            description = "사용자로부터 코드를 받아 인증을 수행합니다.",
            operationId = "v1/auth/code/verify"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest);

    @Operation(
            summary = "부모/자식 연동 API",
            description = "부모 자식간의 연동을 수행합니다. 각자의 인증코드를 입력하여 인증을 수행합니다.",
            operationId = "v1/auth/relation/verify"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> verifyRelatedUser(
            @Valid @RequestBody UserVerifyRelatedUserRequest userVerifyRelatedUserRequest
    );
}
