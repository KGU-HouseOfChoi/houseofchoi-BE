package com.noraknorak.auth.presentation;

import com.noraknorak.auth.application.LoginService;
import com.noraknorak.auth.dto.request.LoginRequest;
import com.noraknorak.auth.dto.response.TokenResponse;
import com.noraknorak.auth.infrastructure.CookieGenerator;
import com.noraknorak.core.presentation.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class LoginController implements LoginSwagger{
    private final LoginService loginService;
    private final CookieGenerator cookieGenerator;

    @Override
    @PostMapping("/login")
    public ResponseEntity<RestResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = loginService.login(loginRequest);
        String accessToken = tokenResponse.accessToken();
        String refreshToken = tokenResponse.refreshToken();

        ResponseCookie accessCookie = cookieGenerator.generateCookie("AccessToken", accessToken);
        ResponseCookie refreshCookie = cookieGenerator.generateCookie("RefreshToken", refreshToken);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new RestResponse<>(tokenResponse));
    }
}
