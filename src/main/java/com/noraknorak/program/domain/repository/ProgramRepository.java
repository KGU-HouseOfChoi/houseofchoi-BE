package com.noraknorak.program.domain.repository;

import com.noraknorak.program.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByNameContainingIgnoreCase(String name); // 이거 추가!

    @Query("SELECT p FROM Program p LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Program> findWithTagsById(@Param("id") Long id);

}
