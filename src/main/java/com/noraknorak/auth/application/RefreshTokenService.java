package com.noraknorak.auth.application;

import com.noraknorak.auth.infrastructure.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties jwtProperties;

    private static final String PREFIX = "RT:";

    public void save(Long userId, String refreshToken) {
        long ttl = jwtProperties.getRefreshExpiration();
        redisTemplate.opsForValue()
                .set(PREFIX + userId, refreshToken, ttl, TimeUnit.DAYS);
    }

    public boolean isValid(Long userId, String token) {
        Object stored = redisTemplate.opsForValue().get(PREFIX + userId);
        return token.equals(stored);
    }

    public void delete(Long userId) {
        redisTemplate.delete(PREFIX + userId);
    }
}
