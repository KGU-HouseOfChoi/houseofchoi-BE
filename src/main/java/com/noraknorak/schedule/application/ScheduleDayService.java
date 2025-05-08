package com.noraknorak.schedule.application;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleDayService {

    private final ScheduleRepository scheduleRepository;
    private final ImageUrlGenerator imageUrlGenerator;

    @Transactional
    public List<ScheduleDayDto> getSchedulesByDay(Long userId, String dayKorean) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        if(schedules == null || schedules.isEmpty()) {
            throw ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException();
        }

        List<ScheduleDayDto> filtered = filteringScheduleByDays(
                schedules,
                dayKorean
        );

        if (filtered.isEmpty()) {
            throw ScheduleErrorCode.NO_PROGRAM_FOR_DAY.toException();
        }

        return filtered;
    }

    private List<ScheduleDayDto> filteringScheduleByDays(List<Schedule> schedules, String dayKorean) {
        return schedules.stream()
                .filter(schedule -> {
                    Program p = schedule.getProgram();
                    return Arrays.asList(
                            p.getFirDay(), p.getSecDay(), p.getThrDay(), p.getFouDay(), p.getFivDay()
                    ).contains(dayKorean);
                })
                .map(schedule -> ScheduleDayDto.from(schedule, imageUrlGenerator))
                .toList();
    }
}
