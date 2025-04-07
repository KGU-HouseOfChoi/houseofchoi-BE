package com.noraknorak.user.domain;

import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseLongIdEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String personalityTag;

}
