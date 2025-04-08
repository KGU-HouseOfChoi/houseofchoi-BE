package com.noraknorak.user.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 불러오지 못했습니다."),
    NOT_EQUAL_CODE(HttpStatus.BAD_REQUEST, "인증번호를 잘못 입력했습니다."),
    MULTIPLE_PHONE_ERROR(HttpStatus.CONFLICT, "이미 가입된 전화번호입니다."),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 코드를 찾을 수 없습니다."),
    INVALID_RESIDENT_NUMBER(HttpStatus.BAD_REQUEST, "주민번호 양식이 잘못되었습니다."),
    NOT_EQUAL_USER_CODE(HttpStatus.NOT_FOUND, "부모/자식 정보를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류 발생 -> 서버를 확인해주세요"),
    INVALID_ROLE_ERROR(HttpStatus.BAD_REQUEST, "알 수 없는 역할 정보입니다. 정보를 정확히 입력해주세요");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}