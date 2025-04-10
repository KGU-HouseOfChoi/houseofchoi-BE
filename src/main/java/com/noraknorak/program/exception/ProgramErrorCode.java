package com.noraknorak.program.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ProgramErrorCode implements BaseErrorCode {

    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "프로그램 정보를 찾을 수 없습니다."),
    DUPLICATE_PROGRAM_NAME(HttpStatus.CONFLICT, "이미 존재하는 프로그램 이름입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "올바르지 않은 카테고리 정보입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "프로그램 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
