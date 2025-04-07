package com.noraknorak.sms.domain;

public interface AuthCodeManager {
    /**
 * Saves an authentication code in Redis using the specified phone number as the key.
 *
 * @param phoneNumber the phone number used as the key for the authentication code.
 * @param code the authentication code to be stored.
 * @param ttl the time-to-live in seconds, after which the code expires.
 */
    void saveCode(String phoneNumber, String code, long ttl);

    /**
 * Retrieves the authentication code associated with the specified phone number.
 *
 * @param phoneNumber the phone number for which the authentication code is retrieved
 * @return the authentication code associated with the phone number
 */
    String getCode(String phoneNumber);

    /**
 * Deletes the authentication code associated with the specified phone number.
 *
 * @param phoneNumber the phone number whose authentication code is to be removed from storage
 */
    void deleteCode(String phoneNumber);
}
