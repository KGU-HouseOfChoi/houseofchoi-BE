package com.noraknorak.core.infrastructure.jpa.repository;

import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseLongIdEntityRepository extends JpaRepository<BaseLongIdEntity, Long> {
}
