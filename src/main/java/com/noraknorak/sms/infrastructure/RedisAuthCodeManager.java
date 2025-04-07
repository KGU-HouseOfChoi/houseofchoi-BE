package com.noraknorak.sms.infrastructure;

import com.noraknorak.core.infrastructure.service.RedisService;
import com.noraknorak.sms.domain.AuthCodeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisAuthCodeManager implements AuthCodeManager {
    private final RedisService redisService;

    @Override
    public void saveCode(String phoneNumber, String code, long ttl) {
        redisService.setValueWithTTL("authCode:" + phoneNumber, code, ttl);
    }

    @Override
    public String getCode(String phoneNumber) {
        return (String) redisService.getValue("authCode:" + phoneNumber);
    }

    @Override
    public void deleteCode(String phoneNumber) {
        redisService.deleteValue("authCode:" + phoneNumber);
    }
}
