package com.noraknorak.sms.factory;

import org.springframework.stereotype.Component;

@Component
public class SmsMessageFactory {
    private static final String MESSAGE_TEMPLATE  = "[노락노락] 인증번호는 %s입니다.";

    public String createAuthMessage(String code){
        return String.format(MESSAGE_TEMPLATE , code);
    }
}
