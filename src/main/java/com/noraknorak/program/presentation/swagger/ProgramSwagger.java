package com.noraknorak.program.presentation.swagger;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.program.presentation.dto.response.ProgramDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Program", description = "프로그램 관련 API")
public interface ProgramSwagger {

    @Operation(
            summary = "전체 프로그램 조회",
            description = "등록된 모든 프로그램 목록을 조회합니다."
    )
    @GetMapping
    ResponseEntity<RestResponse<List<ProgramDetailDto>>> getAllPrograms();

    @Operation(
            summary = "ID로 프로그램 조회",
            description = "ID를 이용해 특정 프로그램의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    ResponseEntity<RestResponse<ProgramDetailDto>> getProgramById(@PathVariable Long id);

    @Operation(
            summary = "이름으로 프로그램 검색",
            description = "입력한 이름이 포함된 프로그램들을 검색합니다."
    )
    @GetMapping("/search")
    ResponseEntity<RestResponse<List<ProgramDetailDto>>> searchProgramsByName(@RequestParam("name") String name);
}
