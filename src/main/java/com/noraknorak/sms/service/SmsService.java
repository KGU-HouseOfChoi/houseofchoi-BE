package com.noraknorak.sms.service;

import com.noraknorak.core.util.sms.SmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final SmsSender smsSender;
    private static final SecureRandom random = new SecureRandom();

    public void sendSms(String toPhoneNumber) {
        String message = String.format("[노락노락] 인증번호는 %s입니다.", generateCode());
        smsSender.send(toPhoneNumber, message);
    }

    public static String generateCode() {
        int number = random.nextInt(900_000) + 100_000;
        return String.valueOf(number);
    }
}


