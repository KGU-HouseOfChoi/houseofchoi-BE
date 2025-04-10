package com.noraknorak.program.domain.repository;

import com.noraknorak.program.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByNameContainingIgnoreCase(String name); // 이거 추가!

}
