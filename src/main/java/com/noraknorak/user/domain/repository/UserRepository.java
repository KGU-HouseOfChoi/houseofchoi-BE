package com.noraknorak.user.domain.repository;

import com.noraknorak.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);
}
