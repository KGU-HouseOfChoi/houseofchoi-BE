package com.noraknorak.user.presentation.controller;

import com.noraknorak.auth.infrastructure.CookieGenerator;
import com.noraknorak.auth.infrastructure.JwtTokenProvider;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;
import com.noraknorak.user.presentation.dto.response.UserSignUpResponse;
import com.noraknorak.user.presentation.swagger.UserRegisterSwagger;
import com.noraknorak.user.service.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class UserRegisterController implements UserRegisterSwagger {

    private final UserRegisterService userRegisterService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieGenerator cookieGenerator;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse<UserSignUpResponse>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        User user = userRegisterService.signUp(userSignUpRequest);

        String accessToken = jwtTokenProvider.provideAccessToken(user);

        UserSignUpResponse response = UserSignUpResponse.from(accessToken, user);

        ResponseCookie cookie = cookieGenerator.generateCookie(accessToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new RestResponse<>(response));
    }

    @Override
    @PostMapping("/code/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest) {
        boolean result = userRegisterService.verifyCode(userVerifyCodeRequest);
        return ResponseEntity.ok(new RestResponse<>(result));
    }

    @Override
    @PostMapping("/relation/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyRelatedUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody UserVerifyRelatedUserRequest userVerifyRelatedUserRequest
    ) {
        // 1. 인증 코드 검증
        User user = userRegisterService.validateUserCode(userVerifyRelatedUserRequest.code());

        // 2. 관계 설정하기
        // 현재 접속 중인 유저가 자식이면 부모의 코드를 활용해서 나를 보호자로 만든다
        userRegisterService.verifyRelatedUser(
                customUserDetails,
                userVerifyRelatedUserRequest.role(),
                user.getId()
        );
        return ResponseEntity.ok(new RestResponse<>(true));
    }

}
