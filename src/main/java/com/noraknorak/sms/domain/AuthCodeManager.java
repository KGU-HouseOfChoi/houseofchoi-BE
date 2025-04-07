package com.noraknorak.sms.domain;

public interface AuthCodeManager {
    void saveCode(String phoneNumber, String code, long ttl);
    String getCode(String phoneNumber);
    void deleteCode(String phoneNumber);
}
