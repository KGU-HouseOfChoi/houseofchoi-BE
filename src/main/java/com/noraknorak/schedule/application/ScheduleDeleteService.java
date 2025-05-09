package com.noraknorak.schedule.application;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleDeleteService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void deleteScheduleByUserAndProgram(CustomUserDetails customUserDetails, Long programId) {
        Long userId = customUserDetails.user().getId();

        Schedule schedule = scheduleRepository
                .findByUserIdAndProgramId(userId, programId)
                .orElseThrow(() -> ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException());

        scheduleRepository.delete(schedule);
    }
}
