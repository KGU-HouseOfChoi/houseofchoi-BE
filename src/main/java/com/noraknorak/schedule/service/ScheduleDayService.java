package com.noraknorak.schedule.service;

import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleDayService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public List<ScheduleDayDto> getSchedulesByDay(Long userId, String dayKorean) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        List<ScheduleDayDto> filtered = schedules.stream()
                .filter(schedule -> {
                    Program p = schedule.getProgram();
                    return List.of(
                            p.getFirDay(), p.getSecDay(), p.getThrDay(),
                            p.getFouDay(), p.getFivDay()
                    ).contains(dayKorean);
                })
                .map(ScheduleDayDto::from)
                .toList();

        if (filtered.isEmpty()) {
            throw ScheduleErrorCode.NO_PROGRAM_FOR_DAY.toException();
        }

        return filtered;
    }

}
