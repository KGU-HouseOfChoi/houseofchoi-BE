package com.noraknorak.sms.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SmsErrorCode implements BaseErrorCode {
    NOT_EQUAL_CODE(HttpStatus.BAD_REQUEST, "인증번호를 잘못 입력했습니다."),
    REDIS_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"레디스 서버에 값을 저장하는데 실패했습니다."),
    REDIS_FIND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "레디스 서버에서 값을 찾아오지 못했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public Exception toException() {
        return new DomainException(httpStatus, message);
    }
}
