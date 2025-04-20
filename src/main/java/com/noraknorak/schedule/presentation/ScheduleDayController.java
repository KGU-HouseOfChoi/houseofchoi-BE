package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import com.noraknorak.schedule.presentation.swagger.ScheduleDaySwagger;
import com.noraknorak.schedule.application.ScheduleDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule")
@RequiredArgsConstructor
public class ScheduleDayController implements ScheduleDaySwagger {

    private final ScheduleDayService scheduleDayService;

    @Override
    @GetMapping("/my-schedule-day/{day}")
    public ResponseEntity<RestResponse<List<ScheduleDayDto>>> getMyProgramsByDay(
            @PathVariable("day") String day,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.user().getId();
        List<ScheduleDayDto> result = scheduleDayService.getSchedulesByDay(userId, day);
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
