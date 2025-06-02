package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "User", description = "유저 관계 삭제 API")
public interface UserRelationDeleteSwagger {

    @Operation(
            summary = "부모/자식 관계 삭제 및 역할 삭제",
            description = "자신 및 상대방 유저의 관계(relatedUser, role)를 모두 초기화합니다.",
            operationId = "v1/user/relation/delete"
    )
    ResponseEntity<RestResponse<Boolean>> deleteRelation(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
