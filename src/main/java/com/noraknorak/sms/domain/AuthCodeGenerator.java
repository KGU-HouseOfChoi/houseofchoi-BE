package com.noraknorak.sms.domain;

import java.security.SecureRandom;

public class AuthCodeGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {
        int number = random.nextInt(900_000) + 100_000; // 100000 ~ 999999
        return String.valueOf(number);
    }
}