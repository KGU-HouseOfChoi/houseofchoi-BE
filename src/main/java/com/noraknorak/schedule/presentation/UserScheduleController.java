package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.dto.response.UserScheduleDto;
import com.noraknorak.schedule.presentation.swagger.UserScheduleSwagger;
import com.noraknorak.schedule.service.UserScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class UserScheduleController implements UserScheduleSwagger {

    private final UserScheduleService userScheduleService;

    @Override
    @GetMapping("/my-schedule")
    public ResponseEntity<RestResponse<List<UserScheduleDto>>> getMyDetailedSchedules(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.user().getId();
        List<UserScheduleDto> result = userScheduleService.getDetailedSchedulesForUser(userId);
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
