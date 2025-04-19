package com.noraknorak.user.presentation.controller;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;
import com.noraknorak.user.presentation.swagger.UserMyPageSwagger;
import com.noraknorak.user.application.UserMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserMyPageController implements UserMyPageSwagger {

    private final UserMyPageService userMyPageService;

    @Override
    @GetMapping("/mypage")
    public ResponseEntity<RestResponse<UserMyPageResponse>> getMyPage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserMyPageResponse response = userMyPageService.getMyPageInfo(customUserDetails);

        return ResponseEntity.ok(new RestResponse<>(response));
    }
}
