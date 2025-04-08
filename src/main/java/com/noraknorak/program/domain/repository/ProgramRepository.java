package com.noraknorak.program.domain.repository;

import com.noraknorak.program.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
