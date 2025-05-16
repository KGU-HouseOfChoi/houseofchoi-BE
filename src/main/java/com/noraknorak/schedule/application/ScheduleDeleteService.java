package com.noraknorak.schedule.application;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleDeleteService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteScheduleById(CustomUserDetails customUserDetails, Long scheduleId) {
        Long userId = customUserDetails.user().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        Set<Long> validUserIds = new HashSet<>();
        validUserIds.add(user.getId());
        if (user.getRelatedUser() != null) {
            validUserIds.add(user.getRelatedUser());
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .filter(s -> validUserIds.contains(s.getUser().getId()))
                .orElseThrow(() -> ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException());

        scheduleRepository.delete(schedule);
    }
}
