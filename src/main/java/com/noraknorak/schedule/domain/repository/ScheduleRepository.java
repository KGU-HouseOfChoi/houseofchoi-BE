package com.noraknorak.schedule.domain.repository;

import com.noraknorak.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);
    boolean existsByUserIdAndProgramId(Long userId, Long programId);

}
