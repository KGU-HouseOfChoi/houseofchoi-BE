package com.noraknorak.sms.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SmsErrorCode implements BaseErrorCode {
    FAILED_SMS_SEND(HttpStatus.INTERNAL_SERVER_ERROR, "SMS 전송에 실패하였습니다."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다.");

    private final HttpStatus httpStatus;

    private final String message;

    /**
     * Converts the error code into its corresponding exception.
     *
     * <p>Creates and returns a new {@link DomainException} using the HTTP status and error message defined by this error code.</p>
     *
     * @return a DomainException representing this error condition
     */
    @Override
    public Exception toException() {
        return new DomainException(httpStatus, message);
    }
}
