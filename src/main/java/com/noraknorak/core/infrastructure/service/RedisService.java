package com.noraknorak.core.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Sets a value in Redis with a specified time-to-live.
     *
     * @param key the key under which the value is stored
     * @param value the value to store in Redis
     * @param timeoutInSeconds the TTL for the stored value, in seconds
     */
    public void setValueWithTTL(String key, Object value, long timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Retrieves the value associated with the specified key from Redis.
     *
     * @param key the key for which to retrieve the associated value
     * @return the value associated with the key, or {@code null} if no value is present
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Deletes the value associated with the specified key from Redis.
     *
     * @param key the key identifying the value to delete from Redis
     */
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
