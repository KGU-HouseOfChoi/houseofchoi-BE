package com.noraknorak.program.application;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.repository.ProgramRepository;
import com.noraknorak.program.presentation.dto.response.ProgramDetailDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @Mock
    ProgramRepository programRepository;

    @InjectMocks
    ProgramService programService;
    
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "프로그램";
    
    @Nested
    @DisplayName("ProgramServiceTest - 다건 조회")
    class 다건_조회{

        @Test
        @DisplayName("전체 프로그램 리스트를 반환한다.")
        void 다건_조회_성공(){
            // given
            List<Program> mockProgramList = List.of(mock(Program.class), mock(Program.class));
            when(programRepository.findAll()).thenReturn(mockProgramList);

            // when
            List<ProgramDetailDto> result = programService.findAllPrograms();

            // then
            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("ProgramServiceTest - ID로 단건 조회")
    class id_단건_조회{

        @Test
        @DisplayName("ID로 프로그램을 찾는다.")
        void 단건_조회_ID(){
            // given
            Program mockProgram = mock(Program.class);
            when(programRepository.findById(TEST_ID)).thenReturn(Optional.of(mockProgram));

            // when
            ProgramDetailDto result = programService.findProgramById(TEST_ID);

            // then
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("없는 ID 조회시 예외 발생")
        void 단건_조회_예외(){
            // given
            when(programRepository.findById(TEST_ID)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> programService.findProgramById(TEST_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("프로그램 정보를 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("ProgramServiceTest - 이름으로 단건 조회")
    class 이름_조회{

        @Test
        @DisplayName("이름으로 프로그램을 찾는다")
        void 조회_이름(){
            // given
            List<Program> mockProgramList = List.of(mock(Program.class), mock(Program.class));
            when(programRepository.findByNameContainingIgnoreCase(TEST_NAME)).thenReturn(mockProgramList);

            // when
            List<ProgramDetailDto> result = programService.findProgramsByName(TEST_NAME);

            // then
            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("이름 검색 결과가 없으면 예외 발생")
        void 조회_이름_예외(){
            // given
            when(programRepository.findByNameContainingIgnoreCase("없는이름")).thenReturn(Collections.EMPTY_LIST);

            // when & then
            assertThatThrownBy(() -> programService.findProgramsByName("없는이름"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("프로그램 정보를 찾을 수 없습니다.");
        }
    }
}
