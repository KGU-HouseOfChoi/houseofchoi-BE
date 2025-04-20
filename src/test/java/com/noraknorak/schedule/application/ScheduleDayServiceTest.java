package com.noraknorak.schedule.application;

import com.noraknorak.center.domain.Center;
import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.presentation.dto.response.ScheduleDayDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleDayServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    ScheduleDayService scheduleDayService;

    private static final Long TEST_USER_ID = 1L;

    @Nested
    @DisplayName("ScheduleDayServiceTest - 정상 반환 테스트")
    class 정상_반환{

        @Test
        @DisplayName("해당 요일에 맞는 스케줄 반환")
        void 정상_스케줄_반환(){
            // given
            Program program = mock(Program.class);
            given(program.getFirDay()).willReturn("월");
            given(program.getSecDay()).willReturn("화");
            given(program.getThrDay()).willReturn(null);
            given(program.getFouDay()).willReturn(null);
            given(program.getFivDay()).willReturn(null);

            Center center = mock(Center.class);

            Schedule schedule = mock(Schedule.class);
            given(schedule.getProgram()).willReturn(program);
            given(program.getCenter()).willReturn(center);

            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(List.of(schedule));

            // when
            List<ScheduleDayDto> result = scheduleDayService.getSchedulesByDay(TEST_USER_ID, "화");

            // then
            assertThat(result).hasSize(1);
        }
    }

    @Nested
    @DisplayName("ScheduleDayServiceTest - 예외 테스트")
    class 예외_테스트{

        @Test
        @DisplayName("스케줄이 없는 경우")
        void 스케줄_x(){
            // given
            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(null);

            // when & then
            assertThatThrownBy(() -> scheduleDayService.getSchedulesByDay(TEST_USER_ID, "화"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("해당 일정을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("해당 요일에 프로그램이 없는 경우")
        void 해당_요일_프로그램_x(){
            // given
            Program program = mock(Program.class);
            given(program.getFirDay()).willReturn("월");
            given(program.getSecDay()).willReturn("화");
            given(program.getThrDay()).willReturn("수");
            given(program.getFouDay()).willReturn("목");
            given(program.getFivDay()).willReturn("금");

            Schedule schedule = mock(Schedule.class);
            given(schedule.getProgram()).willReturn(program);

            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(List.of(schedule));

            // when & then
            assertThatThrownBy(() -> scheduleDayService.getSchedulesByDay(TEST_USER_ID, "토"))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("요청한 요일에 등록된 프로그램이 없습니다.");
        }
    }
}
