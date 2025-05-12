package com.noraknorak.schedule.application;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.response.UserScheduleDto;
import com.noraknorak.schedule.util.ScheduleDateUtils;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ImageUrlGenerator imageUrlGenerator;
    private final UserRepository userRepository;

    @Transactional
    public List<UserScheduleDto> getDetailedSchedulesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        List<Long> userIdsToSearch = new ArrayList<>();
        userIdsToSearch.add(userId);

        if (user.getRelatedUser() != null) {
            userIdsToSearch.add(user.getRelatedUser());
        }

        List<Schedule> schedules = scheduleRepository.findByUserIdIn(userIdsToSearch);

        if (schedules.isEmpty()) {
            throw ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException();
        }

        // 중복 제거 기준: programId
        Set<Long> seenProgramIds = new HashSet<>();
        return schedules.stream()
                .filter(schedule -> seenProgramIds.add(schedule.getProgram().getId()))
                .map(schedule -> {
                    List<LocalDate> dates = ScheduleDateUtils.extractProgramDates(schedule.getProgram());
                    return UserScheduleDto.from(schedule, dates, imageUrlGenerator);
                })
                .toList();
    }

}
