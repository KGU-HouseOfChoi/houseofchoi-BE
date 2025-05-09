package com.noraknorak.schedule.domain.repository;

import com.noraknorak.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);
    boolean existsByUserIdAndProgramId(Long userId, Long programId);
    Optional<Schedule> findByUserIdAndProgramId(Long userId, Long programId);

}
