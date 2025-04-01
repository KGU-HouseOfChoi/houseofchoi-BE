package com.noraknorak.core.config.swagger;

import com.noraknorak.core.config.exception.error.BaseErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCode {

    Class<? extends BaseErrorCode>[] value();
}
