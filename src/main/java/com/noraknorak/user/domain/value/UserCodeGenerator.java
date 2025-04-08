package com.noraknorak.user.domain.value;

import java.util.UUID;

public class UserCodeGenerator {
    public static String generateUserCode() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 6);
    }
}
