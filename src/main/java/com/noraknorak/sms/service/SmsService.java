package com.noraknorak.sms.service;

import com.noraknorak.core.infrastructure.service.RedisService;
import com.noraknorak.core.util.sms.PhoneNumberUtils;
import com.noraknorak.core.util.sms.SmsSender;
import com.noraknorak.sms.domain.AuthCodeGenerator;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.sms.factory.SmsMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
    private final SmsSender smsSender;
    private final AuthCodeManager authCodeManager;
    private final SmsMessageFactory smsMessageFactory;

    private static final long CODE_EXPIRE_SECONDS = 180;

    /**
     * Sends an SMS message with an authentication code to the specified phone number.
     *
     * <p>
     * The method generates a new authentication code, normalizes the provided phone number,
     * and stores the code with a defined expiration. It then creates an SMS message containing the
     * authentication code and sends it to the normalized number.
     * </p>
     *
     * @param toPhoneNumber the destination phone number for the SMS
     * @throws Exception if an error occurs during the code generation, message creation, or SMS sending process
     */
    public void sendSms(String toPhoneNumber) throws Exception {
        String code = AuthCodeGenerator.generateCode();

        authCodeManager.saveCode(PhoneNumberUtils.normalize(toPhoneNumber), code, CODE_EXPIRE_SECONDS);

        String message = smsMessageFactory.createAuthMessage(code);
        smsSender.send(PhoneNumberUtils.normalize(toPhoneNumber), message);
    }
}


