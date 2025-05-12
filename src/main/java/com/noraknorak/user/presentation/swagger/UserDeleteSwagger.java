package com.noraknorak.user.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "User", description = "유저 관련 API")
public interface UserDeleteSwagger {
    @Operation(
            summary = "유저 삭제 API",
            description = """
                    로그인된 사용자를 삭제합니다.  
                    연관된 성향(Personality), 일정(Schedule), 대화 기록(ChatLog)도 함께 삭제됩니다 (Cascade).  
                    삭제 후 사용자는 더 이상 로그인할 수 없습니다.
                    """,
            operationId = "v1/user/delete"
    )
    @ApiErrorCode(UserErrorCode.class)
    ResponseEntity<RestResponse<Void>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    );

}
