package com.noraknorak.core.config.exception.error;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode<T extends Exception> {

    String name();

    String getMessage();

    HttpStatus getHttpStatus();

    T toException();
}
