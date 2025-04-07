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

    /**
     * Constructs a new CoolSmsSender with the specified configuration properties.
     *
     * This constructor initializes the underlying message service using the provided CoolSmsProperties,
     * which must include the API key, API secret, and domain for the CoolSMS service.
     *
     * @param coolSmsProperties configuration properties for the CoolSMS service
     */
    public CoolSmsSender(CoolSmsProperties coolSmsProperties) {
        this.coolSmsProperties = coolSmsProperties;
        this.messageService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain()
        );
    }

    /**
     * Sends an SMS message using the CoolSMS service.
     * <p>
     * Constructs an SMS message with the sender's phone number from configuration and the provided recipient and text.
     * It attempts to send the message via the messaging service, logging the response on success.
     * If an error occurs during sending, it logs the failure and throws a custom exception.
     * </p>
     *
     * @param to the recipient's phone number
     * @param text the SMS message content
     * @throws Exception if the SMS sending process fails
     */
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
            log.error("SMS 전송 실패: {}", e.getMessage(), e);
            throw SmsErrorCode.FAILED_SMS_SEND.toException();
        }
    }
}
