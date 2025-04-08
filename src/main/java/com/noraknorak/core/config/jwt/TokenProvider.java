package com.noraknorak.core.config.jwt;

import com.noraknorak.user.domain.User;

public interface TokenProvider {
    String provideAccessToken(User user);
}
