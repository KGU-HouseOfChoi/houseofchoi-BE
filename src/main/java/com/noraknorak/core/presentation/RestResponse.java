package com.noraknorak.core.presentation;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestResponse<T> extends BaseResponse {

    private final T data;

    public RestResponse(T data) {
        super(true, LocalDateTime.now());
        this.data = data;
    }

    // 실패 응답
    public static <T> RestResponse<T> fail() {
        return new RestResponse<>(null, false);
    }

    private RestResponse(T data, boolean success) {
        super(success, LocalDateTime.now());
        this.data = data;
    }
}
