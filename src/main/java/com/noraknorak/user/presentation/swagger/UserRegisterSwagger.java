package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "user관련 기능")
public interface UserRegisterSwagger {
    /**
     * Registers a new user account.
     * 
     * <p>Accepts a validated sign-up request containing user details and initiates the registration process.
     * The returned response wraps a Boolean indicating whether the registration was successful.</p>
     * 
     * @param userSignUpRequest the sign-up request payload with the user's registration details
     * @return a ResponseEntity containing a RestResponse with a Boolean result of the registration operation
     */
    @Operation(
            summary = "회원가입 API",
            description = "사용자로부터 회원 정보를 입력받아 회원가입을 진행합니다.",
            operationId = "user/signup"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest);

    /**
     * Verifies the user's authentication code.
     * <p>
     * Processes the provided verification request containing the user code and returns a response entity
     * encapsulating the verification status within a RestResponse. A value of {@code true} indicates a successful
     * verification, whereas {@code false} reflects a failure.
     * </p>
     *
     * @param userVerifyCodeRequest the request object containing the user-provided verification code
     * @return a ResponseEntity containing a RestResponse with a boolean indicating the result of the verification
     */
    @Operation(
            summary = "문자 인증 API",
            description = "사용자로부터 코드를 받아 인증을 수행합니다.",
            operationId = "user/verify"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest);
}
