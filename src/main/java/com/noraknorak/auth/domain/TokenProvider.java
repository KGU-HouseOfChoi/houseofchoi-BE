package com.noraknorak.auth.domain;

import com.noraknorak.user.domain.User;

public interface TokenProvider {
    String provideAccessToken(User user);
}
