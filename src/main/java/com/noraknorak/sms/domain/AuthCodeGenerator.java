package com.noraknorak.sms.domain;

import java.security.SecureRandom;

public class AuthCodeGenerator {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random six-digit authentication code.
     *
     * <p>This method produces an integer between 100,000 and 999,999 using a secure random generator,
     * converts it to a string, and returns it.</p>
     *
     * @return a string representing a six-digit authentication code
     */
    public static String generateCode() {
        int number = random.nextInt(900_000) + 100_000; // 100000 ~ 999999
        return String.valueOf(number);
    }
}