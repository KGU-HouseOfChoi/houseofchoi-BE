package com.noraknorak.core.presentation.swagger;

import com.noraknorak.core.presentation.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Test", description = "각종 테스트용 컨트롤러")
public interface RedisSwagger {
    @Operation(
            summary = "Redis Test API - Set",
            description = "현재 레디스가 연결되어 있고 값을 저장할 수 있는지 확인합니다.",
            operationId = "/redis/set"
    )
    public ResponseEntity<RestResponse<Boolean>> setValue();

    @Operation(
            summary = "Redis Test API - Get",
            description = "현재 레디스가 연결되어 있고 값을 가져올 수 있는지 확인합니다.",
            operationId = "/redis/get"
    )
    public ResponseEntity<RestResponse<String>> getValue();
}
