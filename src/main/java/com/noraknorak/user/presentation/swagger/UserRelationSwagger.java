package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.config.swagger.SwaggerRequestBodyExample;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "유저 정보 관련 컨트롤러")
public interface UserRelationSwagger {

    @Operation(
            summary = "부모/자식 연동 API",
            description = """
                    부모 자식간의 연동을 수행합니다. 각자의 인증코드를 입력하여 인증을 수행합니다.                  
                    """,
            operationId = "v1/user/relation/verify"
    )
    @SwaggerRequestBodyExample(UserVerifyRelatedUserRequest.class)
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> verifyRelatedUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody UserVerifyRelatedUserRequest userVerifyRelatedUserRequest
    );
}
