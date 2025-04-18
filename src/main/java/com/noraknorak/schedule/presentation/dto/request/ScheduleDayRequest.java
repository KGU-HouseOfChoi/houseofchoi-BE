package com.noraknorak.schedule.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleDayRequest(
        @NotBlank(message = "요일은 필수 입력입니다.")
        String day
) {}
