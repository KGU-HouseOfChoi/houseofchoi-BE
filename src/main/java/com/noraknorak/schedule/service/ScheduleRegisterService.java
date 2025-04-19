package com.noraknorak.schedule.service;

import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.repository.ProgramRepository;
import com.noraknorak.program.exception.ProgramErrorCode;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.exception.ScheduleErrorCode;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleRegisterService {

    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void registerSchedule(Long userId, Long programId) {
        boolean alreadyRegistered = scheduleRepository.existsByUserIdAndProgramId(userId, programId);
        if (alreadyRegistered) {
            throw ScheduleErrorCode.SCHEDULE_ALREADY_EXISTS.toException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> ProgramErrorCode.PROGRAM_NOT_FOUND.toException());

        Schedule schedule = Schedule.builder()
                .user(user)
                .program(program)
                .center(program.getCenter())
                .build();

        scheduleRepository.save(schedule);
    }
}
