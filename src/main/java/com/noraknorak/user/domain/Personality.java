package com.noraknorak.user.domain;

import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Personality extends BaseLongIdEntity {

    @Column(name = "personality_tags", nullable = false)
    private String tag;

    private String ei;

    private String sn;

    private String tf;

    private String pj;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
