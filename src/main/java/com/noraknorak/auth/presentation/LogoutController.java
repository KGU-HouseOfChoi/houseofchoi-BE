package com.noraknorak.auth.presentation;

import com.noraknorak.auth.application.RefreshTokenService;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class LogoutController implements LogoutSwagger {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/logout")
    public ResponseEntity<RestResponse<Void>> logout(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ResponseCookie cookie1 = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        ResponseCookie cookie2 = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        refreshTokenService.delete(customUserDetails.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie1.toString())
                .header(HttpHeaders.SET_COOKIE, cookie2.toString())
                .build();
    }
}
