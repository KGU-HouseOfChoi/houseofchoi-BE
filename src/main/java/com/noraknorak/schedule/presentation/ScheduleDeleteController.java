package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.application.ScheduleDeleteService;
import com.noraknorak.schedule.presentation.swagger.ScheduleDeleteSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/v1/schedule")
@RequiredArgsConstructor
public class ScheduleDeleteController implements ScheduleDeleteSwagger {

    private final ScheduleDeleteService scheduleDeleteService;

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<RestResponse<Void>> deleteMySchedule(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long scheduleId
    ) {
        scheduleDeleteService.deleteScheduleById(customUserDetails, scheduleId);
        return ResponseEntity.ok(new RestResponse<>(null));
    }
}
