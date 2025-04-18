package com.noraknorak.schedule.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.dto.request.ScheduleRegisterRequest;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Schedule", description = "일정 관련 컨트롤러")
public interface ScheduleRegisterSwagger {

    @Operation(
            summary = "일정 등록 API",
            description = "프론트에서 프로그램 ID만 보내고, 백엔드는 JWT 토큰에서 유저 정보를 추출하여 일정 등록을 처리합니다.",
            operationId = "v1/schedule"
    )
    @ApiErrorCode(ScheduleErrorCode.class)
    ResponseEntity<RestResponse<Boolean>> registerSchedule(
            @Valid @RequestBody ScheduleRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
