package com.noraknorak.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieGenerator {
    private final JwtProperties jwtProperties;

    public ResponseCookie generateCookie(String cookieName, String content) {
        long accessCookieMaxAge = jwtProperties.getAccessExpiration() / 1000; // 쿠키 유효시간 1시간

        return ResponseCookie.from(cookieName, content)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(accessCookieMaxAge)
                .build();
    }
}
