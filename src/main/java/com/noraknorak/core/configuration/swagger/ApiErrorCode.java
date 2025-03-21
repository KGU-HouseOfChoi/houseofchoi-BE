package com.noraknorak.core.configuration.swagger;

import com.noraknorak.core.configuration.exception.error.BaseErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCode {

    Class<? extends BaseErrorCode>[] value();
}
