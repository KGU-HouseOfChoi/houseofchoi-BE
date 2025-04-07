package com.noraknorak.sms.infrastructure;

import com.noraknorak.core.infrastructure.service.RedisService;
import com.noraknorak.sms.domain.AuthCodeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisAuthCodeManager implements AuthCodeManager {
    private final RedisService redisService;

    /**
     * Saves an authentication code for the specified phone number in Redis with a designated time-to-live.
     *
     * <p>The code is stored under a key formatted as "authCode:" concatenated with the phone number.</p>
     *
     * @param phoneNumber the phone number associated with the authentication code
     * @param code the authentication code to be stored
     * @param ttl the duration for which the code is valid (time-to-live)
     */
    @Override
    public void saveCode(String phoneNumber, String code, long ttl) {
        redisService.setValueWithTTL("authCode:" + phoneNumber, code, ttl);
    }

    /**
     * Retrieves the authentication code for the specified phone number from Redis.
     *
     * <p>The Redis key is created by prefixing the provided phone number with "authCode:".
     *
     * @param phoneNumber the phone number associated with the authentication code
     * @return the authentication code as a {@code String}, or {@code null} if no code exists
     */
    @Override
    public String getCode(String phoneNumber) {
        return (String) redisService.getValue("authCode:" + phoneNumber);
    }

    /**
     * Deletes the authentication code associated with the given phone number from Redis.
     *
     * <p>The authentication code is stored under a key formatted as "authCode:{phoneNumber}".</p>
     *
     * @param phoneNumber the phone number for which the authentication code should be deleted
     */
    @Override
    public void deleteCode(String phoneNumber) {
        redisService.deleteValue("authCode:" + phoneNumber);
    }
}
