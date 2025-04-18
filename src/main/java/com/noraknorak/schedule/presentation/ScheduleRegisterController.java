package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.dto.request.ScheduleRegisterRequest;
import com.noraknorak.schedule.presentation.swagger.ScheduleRegisterSwagger;
import com.noraknorak.schedule.service.ScheduleRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleRegisterController implements ScheduleRegisterSwagger {

    private final ScheduleRegisterService scheduleService;

    @Override
    @PostMapping
    public ResponseEntity<RestResponse<Boolean>> registerSchedule(
            @Valid @RequestBody ScheduleRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        scheduleService.registerSchedule(customUserDetails.user().getId(), request.programId());
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}