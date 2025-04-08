package com.noraknorak.program.service;

import com.noraknorak.program.domain.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

}
