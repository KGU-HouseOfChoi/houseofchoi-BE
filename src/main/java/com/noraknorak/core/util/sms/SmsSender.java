package com.noraknorak.core.util.sms;

public interface SmsSender {
    /**
     * 지정된 수신자에게 sms 메시지를 발송합니다
     * @param to 수신자 전화번호
     * @param text 전송할 문자 내용
     * @throws Exception SMS 전송 중 오류가 발생한 경우
     */
    void send(String to, String text) throws Exception;
}
