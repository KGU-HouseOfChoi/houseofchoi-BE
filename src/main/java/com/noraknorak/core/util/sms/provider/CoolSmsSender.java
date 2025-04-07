package com.noraknorak.core.util.sms.provider;

import com.noraknorak.core.util.sms.SmsSender;
import com.noraknorak.core.util.sms.properties.CoolSmsProperties;
import com.noraknorak.sms.exception.SmsErrorCode;
import lombok.RequiredArgsConstructor;
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
    private final CoolSmsProperties coolSmsProperties;

    public CoolSmsSender(CoolSmsProperties coolSmsProperties) {
        this.coolSmsProperties = coolSmsProperties;
        this.messageService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain()
        );
    }

    @Override
    public void send(String to, String text) throws Exception {
        Message message = new Message();
        message.setFrom(coolSmsProperties.getSenderPhone());
        message.setTo(to);
        message.setText(text);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("CoolSMS 응답: {}", response);
        } catch (Exception e) {
            throw SmsErrorCode.FAIL_SMS_SEND.toException();
        }
    }
}
