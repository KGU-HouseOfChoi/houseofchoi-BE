package com.noraknorak.program.domain;

import com.noraknorak.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Tag extends BaseLongIdEntity {
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Program> programs;
}
