package com.noraknorak.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    SENIOR("부모"),
    GUARDIAN("자식");

    private final String name;
}
