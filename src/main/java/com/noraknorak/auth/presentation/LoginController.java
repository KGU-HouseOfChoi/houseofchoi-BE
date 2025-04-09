package com.noraknorak.auth.presentation;

import com.noraknorak.auth.application.LoginService;
import com.noraknorak.auth.dto.request.LoginReqeust;
import com.noraknorak.auth.dto.response.TokenResponse;
import com.noraknorak.core.presentation.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController implements LoginSwagger{
    private final LoginService loginService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<RestResponse<TokenResponse>> login(@Valid @RequestBody LoginReqeust loginReqeust) {
        TokenResponse tokenResponse = loginService.login(loginReqeust);

        return ResponseEntity.ok(new RestResponse<>(tokenResponse));
    }
}
