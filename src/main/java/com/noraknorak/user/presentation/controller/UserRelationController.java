package com.noraknorak.user.presentation.controller;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import com.noraknorak.user.presentation.swagger.UserRelationSwagger;
import com.noraknorak.user.application.UserRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserRelationController implements UserRelationSwagger {

    private final UserRelationService userRelationService;

    @Override
    @PostMapping("/relation/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyRelatedUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody UserVerifyRelatedUserRequest userVerifyRelatedUserRequest
    ) {
        // 1. 인증 코드 검증
        User user = userRelationService.validateUserCode(userVerifyRelatedUserRequest.code());

        // 2. 관계 설정하기
        // 현재 접속 중인 유저가 자식이면 부모의 코드를 활용해서 나를 보호자로 만든다
        userRelationService.verifyRelatedUser(
                customUserDetails,
                userVerifyRelatedUserRequest.role(),
                user.getId()
        );
        return ResponseEntity.ok(new RestResponse<>(true));
    }

}
