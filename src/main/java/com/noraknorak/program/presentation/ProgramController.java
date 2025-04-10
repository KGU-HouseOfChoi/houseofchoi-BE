package com.noraknorak.program.presentation;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.program.presentation.dto.response.ProgramDetailDto;
import com.noraknorak.program.presentation.swagger.ProgramSwagger;
import com.noraknorak.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController implements ProgramSwagger {
    private final ProgramService programService;

    @Override
    @GetMapping
    public ResponseEntity<RestResponse<List<ProgramDetailDto>>> getAllPrograms() {
        List<ProgramDetailDto> programs = programService.findAllPrograms();
        return ResponseEntity.ok(new RestResponse<>(programs));
    }

    @Override
    public ResponseEntity<RestResponse<ProgramDetailDto>> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(new RestResponse<>(programService.findProgramById(id)));
    }


    @Override
    @GetMapping("/search")
    public ResponseEntity<RestResponse<List<ProgramDetailDto>>> searchProgramsByName(@RequestParam("name") String name) {
        List<ProgramDetailDto> result = programService.findProgramsByName(name);
        return ResponseEntity.ok(new RestResponse<>(result));
    }
}
