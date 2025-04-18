package com.noraknorak.schedule.presentation;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.schedule.presentation.dto.request.ScheduleDayRequest;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import com.noraknorak.schedule.presentation.swagger.ScheduleDaySwagger;
import com.noraknorak.schedule.service.ScheduleDayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleDayController implements ScheduleDaySwagger {

    private final ScheduleDayService scheduleDayService;

    @Override
    @PostMapping("/my-schedule-day")
    public ResponseEntity<RestResponse<List<ScheduleDayDto>>> getMyProgramsByDay(
            @Valid @RequestBody ScheduleDayRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.user().getId();
        List<ScheduleDayDto> result = scheduleDayService.getSchedulesByDay(userId, request.day());
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
