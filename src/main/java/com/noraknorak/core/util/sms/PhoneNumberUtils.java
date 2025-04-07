package com.noraknorak.core.util.sms;

public class PhoneNumberUtils {
    /**
     * Normalizes a phone number string by removing all non-digit characters.
     *
     * <p>This method processes an input string that may contain formatting characters
     * (such as spaces, dashes, or parentheses) and returns a string composed solely of digits.
     *
     * @param phone the phone number string to be normalized
     * @return a string containing only the numeric digits extracted from the input
     */
    public static String normalize(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }
}