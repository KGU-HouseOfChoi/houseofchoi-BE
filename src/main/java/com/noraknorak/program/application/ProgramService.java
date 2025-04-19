package com.noraknorak.program.application;

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

    public List<ProgramDetailDto> findAllPrograms() {
        return programRepository.findAll().stream()
                .map(ProgramDetailDto::toDto)
                .collect(Collectors.toList());
    }

    public ProgramDetailDto findProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> ProgramErrorCode.PROGRAM_NOT_FOUND.toException());
        return ProgramDetailDto.toDto(program);
    }

    public List<ProgramDetailDto> findProgramsByName(String name) {
        List<Program> programs = programRepository.findByNameContainingIgnoreCase(name);
        if (programs.isEmpty()) {
            throw ProgramErrorCode.PROGRAM_NOT_FOUND.toException();
        }
        return programs.stream()
                .map(ProgramDetailDto::toDto)
                .collect(Collectors.toList());
    }

}
