package com.noraknorak.schedule.presentation.dto.response;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.center.domain.Center;

public record ScheduleDayDto(
        Long programId,
        String name,
        String mainCategory,
        String subCategory,
        Long price,
        String address,
        Double latitude,
        Double longitude,
        String imageUrl
) {
    public static ScheduleDayDto from(Schedule schedule, ImageUrlGenerator imageUrlGenerator) {
        Program program = schedule.getProgram();
        Center center = program.getCenter();

        return new ScheduleDayDto(
                program.getId(),
                program.getName(),
                program.getMainCategory(),
                program.getSubCategory(),
                program.getPrice(),
                center.getAddress(),
                center.getLatitude(),
                center.getLongitude(),
                imageUrlGenerator.getImageUrlById(program.getId())
        );
    }
}