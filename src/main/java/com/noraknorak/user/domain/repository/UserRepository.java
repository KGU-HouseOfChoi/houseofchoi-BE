package com.noraknorak.user.domain.repository;

import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);

    Optional<User> findByUserCode(String userCode);

    @Modifying
    @Query("UPDATE User u SET u.role = :role, u.relatedUser = :relatedUser WHERE u.id = :id")
    int updateUserByUserCode(
            @Param("id") Long id,
            @Param("role") Role role,
            @Param("relatedUser") Long relatedUser
    );
}
