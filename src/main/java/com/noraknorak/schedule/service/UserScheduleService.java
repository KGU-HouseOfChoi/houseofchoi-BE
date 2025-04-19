package com.noraknorak.schedule.service;

import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.presentation.dto.response.UserScheduleDto;
import com.noraknorak.schedule.util.ScheduleDateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public List<UserScheduleDto> getDetailedSchedulesForUser(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream()
                .map(schedule -> {
                    List<LocalDate> dates = ScheduleDateUtils.extractProgramDates(schedule.getProgram());
                    return UserScheduleDto.from(schedule, dates);
                })
                .toList();
    }
}
