package com.noraknorak.user.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {
    NOT_EQUAL_CODE(HttpStatus.BAD_REQUEST, "인증번호를 잘못 입력했습니다."),
    MULTIPLE_PHONE_ERROR(HttpStatus.CONFLICT, "이미 가입된 전화번호입니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}