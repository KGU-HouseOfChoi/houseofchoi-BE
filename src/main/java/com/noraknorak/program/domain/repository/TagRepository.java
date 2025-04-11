package com.noraknorak.program.domain.repository;

import com.noraknorak.program.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
