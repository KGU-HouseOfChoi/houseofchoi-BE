package com.noraknorak.sms.application;

import com.noraknorak.core.util.sms.PhoneNumberUtils;
import com.noraknorak.core.util.sms.SmsSender;
import com.noraknorak.sms.domain.AuthCodeGenerator;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.sms.factory.SmsMessageFactory;
import com.noraknorak.sms.service.SmsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SmsServiceTest {

    private SmsSender smsSender;
    private AuthCodeManager authCodeManager;
    private SmsMessageFactory smsMessageFactory;
    private SmsService smsService;

    @BeforeEach
    void setUp() {
        smsSender = mock(SmsSender.class);
        authCodeManager = mock(AuthCodeManager.class);
        smsMessageFactory = mock(SmsMessageFactory.class);
        smsService = new SmsService(smsSender, authCodeManager, smsMessageFactory);
    }

    @Test
    @DisplayName("SmsServiceTest - 정상 문자 인증 전송")
    void 정상_문자_전송() throws Exception {
        // given
        String rawPhoneNumber = "010-1234-5678";
        String normalized = PhoneNumberUtils.normalize(rawPhoneNumber);

        when(smsMessageFactory.createAuthMessage(anyString())).thenReturn("dummy message");

        // when
        smsService.sendSms(rawPhoneNumber);

        // then
        verify(authCodeManager).saveCode(eq(normalized), anyString(), eq(180L));
        verify(smsSender).send(eq(normalized), eq("dummy message"));
    }
}
