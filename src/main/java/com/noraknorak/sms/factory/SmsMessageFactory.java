package com.noraknorak.sms.factory;

import com.noraknorak.sms.exception.SmsErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageFactory {
    private final String messageTemplate;

    /**
     * Constructs a new SmsMessageFactory with the specified message template.
     *
     * @param messageTemplate the SMS message template for formatting authentication messages,
     *                        injected from the "coolsms.message-template" property
     */
    public SmsMessageFactory(
            @Value("${coolsms.message-template}")
            String messageTemplate
    ) {
        this.messageTemplate = messageTemplate;
    }

    /**
     * Creates an authentication SMS message by embedding the provided code into a predefined template.
     *
     * <p>This method validates the supplied authentication code and constructs the message by
     * substituting the code into the configured message template. If the code is null or empty,
     * it throws an exception.
     *
     * @param code the authentication code to include in the SMS message
     * @return the formatted SMS message containing the authentication code
     * @throws Exception if the provided authentication code is null or empty
     */
    public String createAuthMessage(String code) throws Exception {
        if(code == null || code.isEmpty()){
            throw SmsErrorCode.INVALID_AUTH_CODE.toException();
        }
        return String.format(messageTemplate , code);
    }
}
