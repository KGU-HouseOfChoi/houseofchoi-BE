package com.noraknorak.core.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test", description = "각종 테스트용 컨트롤러")
public interface DBTestSwagger {
    @Operation(
            summary = "DB Test API",
            description = "현재 데이터 베이스가 연결 되어 있는지 확인합니다.",
            operationId = "/test/db"
    )
    public String testDatabaseConnection();
}
