package com.noraknorak.program.presentation;

import com.noraknorak.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;
}
