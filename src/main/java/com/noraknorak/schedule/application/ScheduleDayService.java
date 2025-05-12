package com.noraknorak.schedule.application;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleDayService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ImageUrlGenerator imageUrlGenerator;

    @Transactional
    public List<ScheduleDayDto> getSchedulesByDay(Long userId, String dayKorean) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        Set<Long> userIdsToSearch = new HashSet<>();
        userIdsToSearch.add(user.getId());
        if (user.getRelatedUser() != null) {
            userIdsToSearch.add(user.getRelatedUser());
        }

        List<Schedule> schedules = scheduleRepository.findByUserIdIn(new ArrayList<>(userIdsToSearch));
        if (schedules.isEmpty()) {
            throw ScheduleErrorCode.SCHEDULE_NOT_FOUND.toException();
        }

        List<ScheduleDayDto> filtered = filteringScheduleByDays(schedules, dayKorean);
        if (filtered.isEmpty()) {
            throw ScheduleErrorCode.NO_PROGRAM_FOR_DAY.toException();
        }

        return filtered.stream()
                .collect(Collectors.toMap(
                        ScheduleDayDto::programId,
                        Function.identity(),
                        (d1, d2) -> d1
                ))
                .values()
                .stream()
                .toList();
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
