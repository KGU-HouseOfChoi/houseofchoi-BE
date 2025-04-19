package com.noraknorak.schedule.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.response.UserScheduleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "Schedule", description = "일정 조회 관련 컨트롤러")
public interface UserScheduleSwagger {

    @Operation(
            summary = "내가 등록한 일정 정보 조회 API",
            description = "로그인한 사용자가 등록한 프로그램 목록과, 각 프로그램의 상세 정보 및 실제 날짜 목록을 조회합니다.",
            operationId = "v1/schedule/my-schedule"
    )
    @ApiErrorCode(ScheduleErrorCode.class)
    ResponseEntity<RestResponse<List<UserScheduleDto>>> getMyDetailedSchedules(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}