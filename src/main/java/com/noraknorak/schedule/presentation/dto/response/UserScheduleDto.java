package com.noraknorak.schedule.presentation.dto.response;

import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.center.domain.Center;

import java.time.LocalDate;
import java.util.List;

public record UserScheduleDto(
        Long programId,
        String name,
        String mainCategory,
        String subCategory,
        Long price,
        String address,
        Double latitude,
        Double longitude,
        List<LocalDate> dates
) {
    public static UserScheduleDto from(Schedule schedule, List<LocalDate> dates) {
        Program program = schedule.getProgram();
        Center center = program.getCenter();

        return new UserScheduleDto(
                program.getId(),
                program.getName(),
                program.getMainCategory(),
                program.getSubCategory(),
                program.getPrice(),
                center.getAddress(),
                center.getLatitude(),
                center.getLongitude(),
                dates
        );
    }
}
