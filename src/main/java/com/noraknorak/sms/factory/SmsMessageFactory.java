package com.noraknorak.sms.factory;

import com.noraknorak.sms.exception.SmsErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageFactory {
    private final String messageTemplate;

    public SmsMessageFactory(
            @Value("${coolsms.message-template}")
            String messageTemplate
    ) {
        this.messageTemplate = messageTemplate;
    }

    public String createAuthMessage(String code) throws Exception {
        if(code == null || code.isEmpty()){
            throw SmsErrorCode.INVALID_AUTH_CODE.toException();
        }
        return String.format(messageTemplate , code);
    }
}
