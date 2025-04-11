package com.noraknorak.program.domain;

import com.noraknorak.center.domain.Center;
import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.noraknorak.program.domain.Value.MainCategory;
import com.noraknorak.program.domain.Value.SubCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Program extends BaseLongIdEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String firDay;

    @Column(nullable = true)
    private String secDay;

    @Column(nullable = true)
    private String thrDay;

    @Column(nullable = true)
    private String fouDay;

    @Column(nullable = true)
    private String fivDay;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @Column(nullable = false)
    private int headcount;

    @Column(nullable = false)
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;
}