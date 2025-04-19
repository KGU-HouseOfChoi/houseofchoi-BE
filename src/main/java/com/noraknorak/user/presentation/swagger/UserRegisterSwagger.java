package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.config.swagger.SwaggerRequestBodyExample;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.response.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Auth", description = "로그인/회원가입 관련 컨트롤러")
public interface UserRegisterSwagger {
    @Operation(
            summary = "회원가입 API",
            description = """
                    사용자로부터 회원 정보를 받아 로그인 OR 회원가입을 진행합니다.
                    """,
            operationId = "v1/auth/sign-up"
    )
    @SwaggerRequestBodyExample(UserSignUpRequest.class)
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<UserSignUpResponse>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest);

    @Deprecated
    @Operation(
            summary = "문자 인증 API (사용 x)",
            description = "사용자로부터 코드를 받아 인증을 수행합니다. (사용 안함)",
            operationId = "v1/auth/code/verify"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest);
}
