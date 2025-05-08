package com.noraknorak.schedule.application;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
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
    private final ImageUrlGenerator imageUrlGenerator;

    @Transactional
    public List<UserScheduleDto> getDetailedSchedulesForUser(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        if(schedules == null || schedules.isEmpty()) {
            throw ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException();
        }

        return scheduleRepository.findByUserId(userId)
                .stream()
                .map(schedule -> {
                    List<LocalDate> dates = ScheduleDateUtils.extractProgramDates(schedule.getProgram());
                    return UserScheduleDto.from(schedule, dates, imageUrlGenerator);
                })
                .toList();
    }
}
