package com.noraknorak.core.util.sms;

public class PhoneNumberUtils {
    public static String normalize(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }
}