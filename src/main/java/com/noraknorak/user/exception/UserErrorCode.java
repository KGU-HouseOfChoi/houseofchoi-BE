package com.noraknorak.user.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {
    MULTIPLE_PHONE_ERROR(HttpStatus.CONFLICT, "전화번호가 중복되었습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}