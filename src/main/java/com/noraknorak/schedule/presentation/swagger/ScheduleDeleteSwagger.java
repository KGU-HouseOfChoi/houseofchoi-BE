package com.noraknorak.schedule.presentation.swagger;

import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Tag(name = "Schedule", description = "일정 삭제 API")
public interface ScheduleDeleteSwagger {

    @Operation(summary = "일정 삭제", description = "로그인된 사용자와 관계 설정된 유저가 등록한 특정 프로그램 일정을 삭제합니다.")
    @ApiErrorCode(ScheduleErrorCode.class)
    ResponseEntity<RestResponse<Void>> deleteMySchedule(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long programId
    );
}
