package com.noraknorak.user.domain.repository;

import com.noraknorak.user.domain.Personality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityRepository extends JpaRepository<Personality, Long> {
}