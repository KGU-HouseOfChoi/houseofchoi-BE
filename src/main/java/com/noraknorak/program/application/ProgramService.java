package com.noraknorak.program.application;

import com.noraknorak.core.util.file.ImageUrlGenerator;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.repository.ProgramRepository;
import com.noraknorak.program.exception.ProgramErrorCode;
import com.noraknorak.program.presentation.dto.response.ProgramDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ImageUrlGenerator imageUrlGenerator;

    public List<ProgramDetailDto> findAllPrograms() {
        return programRepository.findAll().stream()
                .map(program -> ProgramDetailDto.toDto(program, imageUrlGenerator))
                .collect(Collectors.toList());
    }

    public ProgramDetailDto findProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> ProgramErrorCode.PROGRAM_NOT_FOUND.toException());
        return ProgramDetailDto.toDto(program, imageUrlGenerator);
    }

    public List<ProgramDetailDto> findProgramsByName(String name) {
        List<Program> programs = programRepository.findByNameContainingIgnoreCase(name);
        return programs.stream()
                .map(program -> ProgramDetailDto.toDto(program, imageUrlGenerator))
                .collect(Collectors.toList());
    }

}
