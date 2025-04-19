package com.noraknorak.schedule.exception;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.config.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ScheduleErrorCode implements BaseErrorCode {

    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 불러오지 못했습니다."),
    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "프로그램 정보를 불러오지 못했습니다."),
    CENTER_NOT_FOUND(HttpStatus.NOT_FOUND, "연결된 센터 정보를 찾을 수 없습니다."),
    SCHEDULE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 일정입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일정 등록 중 알 수 없는 오류가 발생했습니다."),
    NO_PROGRAM_FOR_DAY(HttpStatus.NOT_FOUND, "요청한 요일에 등록된 프로그램이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
