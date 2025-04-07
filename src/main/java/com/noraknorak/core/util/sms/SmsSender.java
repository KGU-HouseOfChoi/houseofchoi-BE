package com.noraknorak.core.util.sms;

public interface SmsSender {
    void send(String to, String text) throws Exception;
}
