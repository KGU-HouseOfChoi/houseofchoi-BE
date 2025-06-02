package com.noraknorak.user.presentation.controller;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.application.UserRelationDeleteService;
import com.noraknorak.user.presentation.swagger.UserRelationDeleteSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/relation")
@RequiredArgsConstructor
public class UserRelationDeleteController implements UserRelationDeleteSwagger {

    private final UserRelationDeleteService userRelationDeleteService;

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<RestResponse<Boolean>> deleteRelation(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        userRelationDeleteService.deleteRelation(customUserDetails);
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}
