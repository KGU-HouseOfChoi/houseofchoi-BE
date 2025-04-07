package com.noraknorak.core.util.sms;

public interface SmsSender {
    /**
 * Sends an SMS message to the specified recipient.
 *
 * @param to the recipient's phone number
 * @param text the text content of the SMS message
 * @throws Exception if an error occurs while sending the SMS
 */
    void send(String to, String text) throws Exception;
}
