package com.noraknorak.user.presentation.controller;

import com.noraknorak.auth.application.RefreshTokenService;
import com.noraknorak.auth.infrastructure.CookieGenerator;
import com.noraknorak.auth.infrastructure.JwtTokenProvider;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.response.UserSignUpResponse;
import com.noraknorak.user.presentation.dto.response.UserSignUpResult;
import com.noraknorak.user.presentation.swagger.UserRegisterSwagger;
import com.noraknorak.user.application.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class UserRegisterController implements UserRegisterSwagger {

    private final UserRegisterService userRegisterService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieGenerator cookieGenerator;
    private final RefreshTokenService refreshTokenService;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse<UserSignUpResponse>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        UserSignUpResult result = userRegisterService.signUp(userSignUpRequest);

        User user = result.user();
        boolean isNewUser = result.isNewUser();

        String accessToken = jwtTokenProvider.provideAccessToken(user);
        String refreshToken = jwtTokenProvider.provideRefreshToken(user);

        refreshTokenService.save(user.getId(), refreshToken);

        UserSignUpResponse response = UserSignUpResponse.from(accessToken, refreshToken, user, isNewUser);

        ResponseCookie accessCookie = cookieGenerator.generateCookie("accessToken", accessToken);
        ResponseCookie refreshCookie = cookieGenerator.generateCookie("refreshToken", refreshToken);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new RestResponse<>(response));
    }

    @Override
    @PostMapping("/code/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest) {
        boolean result = userRegisterService.verifyCode(userVerifyCodeRequest);
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
