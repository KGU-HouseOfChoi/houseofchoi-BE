package com.noraknorak.program.presentation.dto.response;

import com.noraknorak.program.domain.Value.MainCategory;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.Value.SubCategory;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalTime;

@Getter
@Builder
public class ProgramDetailDto {
    private Long id;
    private String name;
    private String firDay;
    private String secDay;
    private String thrDay;
    private String fouDay;
    private String fivDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long price;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private int headcount;
    private String tags;
    private Long centerId;
    private String centerName;

    public static ProgramDetailDto toDto(Program program) {
        var center = program.getCenter();

        return ProgramDetailDto.builder()
                .id(program.getId())
                .name(program.getName())
                .firDay(program.getFirDay())
                .secDay(program.getSecDay())
                .thrDay(program.getThrDay())
                .fouDay(program.getFouDay())
                .fivDay(program.getFivDay())
                .startTime(program.getStartTime())
                .endTime(program.getEndTime())
                .price(program.getPrice())
                .mainCategory(program.getMainCategory())
                .subCategory(program.getSubCategory())
                .headcount(program.getHeadcount())
                .tags(program.getTags())
                .centerId(center != null ? center.getId() : null)
                .centerName(center != null ? center.getName() : null)
                .build();
    }
}
