package com.noraknorak.sms.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    private final DefaultMessageService messageService;

    @Value("${coolsms.senderPhone}")
    private String senderPhone;

    public SmsService(
            @Value("${coolsms.api-key}") String apiKey,
            @Value("${coolsms.api-secret}") String apiSecret
    ) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendSms(String toPhoneNumber) {
        Message message = new Message();
        message.setFrom(senderPhone); // 등록된 발신번호
        message.setTo(toPhoneNumber);
        message.setText("[노락노락] 인증번호는 123456입니다."); // 실제 인증번호 넣으면 됨

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("CoolSMS 응답: {}", response);
        } catch (Exception e) {
            log.error("CoolSMS 전송 실패", e);
        }
    }
}


