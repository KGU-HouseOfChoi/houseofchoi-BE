package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.swagger.ScheduleRegisterSwagger;
import com.noraknorak.schedule.application.ScheduleRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schedule")
@RequiredArgsConstructor
public class ScheduleRegisterController implements ScheduleRegisterSwagger {

    private final ScheduleRegisterService scheduleRegisterService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<RestResponse<Boolean>> registerSchedule(
            @RequestParam("programId") Long programId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        scheduleRegisterService.registerSchedule(customUserDetails.user().getId(), programId);
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}