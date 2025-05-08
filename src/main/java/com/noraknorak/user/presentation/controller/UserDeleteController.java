package com.noraknorak.user.presentation.controller;

import com.noraknorak.auth.infrastructure.JwtTokenProvider;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.application.UserDeleteService;
import com.noraknorak.user.presentation.swagger.UserDeleteSwagger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserDeleteController implements UserDeleteSwagger {

    private final UserDeleteService userDeleteService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<RestResponse<Void>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        userDeleteService.deleteUserById(userDetails.getId());

        String token = (String) request.getAttribute("accessToken");
        if (token != null) {
            long remainingTime = jwtTokenProvider.getRemainingTime(token);
            redisTemplate.opsForValue().set("blacklist:" + token, "true", remainingTime, TimeUnit.MILLISECONDS);
        }

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new RestResponse<>(null));
    }
}
