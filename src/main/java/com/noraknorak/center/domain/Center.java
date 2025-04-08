package com.noraknorak.center.domain;

import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.noraknorak.program.domain.Program;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Center extends BaseLongIdEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String tel;

    @OneToMany(mappedBy = "center")
    private List<Program> programs;
}