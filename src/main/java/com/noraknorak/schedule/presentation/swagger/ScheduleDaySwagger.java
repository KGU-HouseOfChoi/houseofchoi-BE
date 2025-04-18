package com.noraknorak.schedule.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.request.ScheduleDayRequest;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Schedule", description = "요일 기반 사용자 일정 조회 API")
public interface ScheduleDaySwagger {

    @Operation(
            summary = "요일 기반 내 프로그램 조회",
            description = "요청한 요일(월~일)에 해당하는 로그인 사용자의 프로그램 정보를 반환합니다.",
            operationId = "schedules/my-schedule-day"
    )
    @ApiErrorCode(ScheduleErrorCode.class)
    ResponseEntity<RestResponse<List<ScheduleDayDto>>> getMyProgramsByDay(
            @Valid @RequestBody ScheduleDayRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
