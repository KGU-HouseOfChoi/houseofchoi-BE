package com.noraknorak.core.util.sms;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoolSmsSender implements SmsSender {
    private final DefaultMessageService messageService;

    @Value("${coolsms.sender-phone}")
    private String senderPhone;

    public CoolSmsSender(
            @Value("${coolsms.api-key}") String apiKey,
            @Value("${coolsms.api-secret}") String apiSecret,
            @Value("${coolsms.domain}") String domain
    ) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
    }

    @Override
    public void send(String to, String text) {
        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(to);
        message.setText(text);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("CoolSMS 응답: {}", response);
        } catch (Exception e) {
            log.error("CoolSMS 전송 실패", e);
            throw new RuntimeException("SMS 전송 실패", e);
        }
    }
}
