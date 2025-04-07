package com.noraknorak.user.presentation;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.swagger.UserRegisterSwagger;
import com.noraknorak.user.service.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRegisterController implements UserRegisterSwagger {

    private final UserRegisterService userRegisterService;

    /**
     * Registers a new user.
     *
     * <p>This endpoint accepts a validated sign-up request, delegates the registration process to the 
     * user registration service, and returns a response indicating the success of the operation.</p>
     *
     * @param userSignUpRequest the user registration details provided in the request body
     * @return a ResponseEntity containing a RestResponse with {@code true} to indicate a successful sign-up
     */
    @Override
    @PostMapping("/signup")
    public ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userRegisterService.signUp(userSignUpRequest);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

    /**
     * Verifies the user's provided code.
     *
     * <p>This endpoint processes a user verification request by delegating the operation to the user registration
     * service. It returns a response indicating the verification outcome.</p>
     *
     * @param userVerifyCodeRequest the request payload containing the verification code
     * @return a ResponseEntity wrapping a RestResponse with a boolean value representing the operation's success
     */
    @Override
    @PostMapping("/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest) {
        userRegisterService.verifyCode(userVerifyCodeRequest);
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}
