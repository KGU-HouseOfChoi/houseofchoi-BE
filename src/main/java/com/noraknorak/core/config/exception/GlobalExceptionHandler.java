package com.noraknorak.core.config.exception;

import com.noraknorak.core.presentation.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 유효성 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.createValidationErrorResponse()
                        .statusCode(400)
                        .exception(ex)
                        .build());
    }

    // 도메인 예외 (예: UserErrorCode에서 터지는 경우)
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        log.warn("Domain error: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.createDomainErrorResponse()
                        .statusCode(ex.getHttpStatus().value())
                        .exception(ex)
                        .build());
    }

    // 그 외 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.createErrorResponse()
                        .statusCode(500)
                        .exception(ex)
                        .build());
    }
}
