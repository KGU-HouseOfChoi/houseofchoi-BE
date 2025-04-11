package com.noraknorak.program.presentation.dto.response;

import com.noraknorak.program.domain.Tag;
import com.noraknorak.program.domain.Value.MainCategory;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.Value.SubCategory;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private String mainCategory;
    private String subCategory;
    private String headcount;
    private List<String> tags;
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
                .tags(
                        program.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
                )
                .centerId(center != null ? center.getId() : null)
                .centerName(center != null ? center.getName() : null)
                .build();
    }
}
