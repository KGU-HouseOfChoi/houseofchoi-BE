package com.noraknorak.auth.domain;

import com.noraknorak.user.domain.User;

public interface TokenProvider {
    String provideAccessToken(User user);
    String provideRefreshToken(User user);
    Long getUserId(String token);
}
