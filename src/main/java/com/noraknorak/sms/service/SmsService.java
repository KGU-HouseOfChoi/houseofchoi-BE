package com.noraknorak.sms.service;

import com.noraknorak.core.infrastructure.service.RedisService;
import com.noraknorak.core.util.sms.SmsSender;
import com.noraknorak.sms.domain.AuthCodeGenerator;
import com.noraknorak.sms.exception.SmsErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
    private final SmsSender smsSender;
    private final RedisService redisService;

    private static final long CODE_EXPIRE_SECONDS = 180;

    public void sendSms(String toPhoneNumber) throws Exception{
        // 인증 코드 생성 -> 6자리 정수값
        String code = AuthCodeGenerator.generateCode();

        // redis에 인증 코드 저장
        try{
            redisService.setValueWithTTL("authCode:" + toPhoneNumber, code, CODE_EXPIRE_SECONDS);
        } catch (Exception e) {
            throw SmsErrorCode.REDIS_SAVE_ERROR.toException();
        }

        // 문자 전송
        String message = String.format("[노락노락] 인증번호는 %s입니다.", code);
        smsSender.send(toPhoneNumber, message);
    }

    private boolean verifyCode(String phoneNumber, String inputCode) throws Exception {
        // redis에 저장된 key값 가져오기
        String redisKey = "authCode:" + phoneNumber;

        try{
            Object storedCode = redisService.getValue(redisKey);
            if (storedCode != null && storedCode.equals(inputCode)) {
                redisService.deleteValue(redisKey);
                return true;
            }else{
                throw SmsErrorCode.NOT_EQUAL_CODE.toException();
            }
        } catch (Exception e) {
            throw SmsErrorCode.REDIS_FIND_ERROR.toException();
        }
    }
}


