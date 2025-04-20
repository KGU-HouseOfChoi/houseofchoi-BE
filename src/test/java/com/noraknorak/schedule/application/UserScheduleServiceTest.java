package com.noraknorak.schedule.application;

import com.noraknorak.center.domain.Center;
import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.program.domain.Program;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.schedule.presentation.dto.response.UserScheduleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserScheduleServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    UserScheduleService userScheduleService;

    private static final Long TEST_USER_ID = 1L;

    @Nested
    @DisplayName("UserScheduleServiceTest - 정상 반환")
    class 정상_반환_id{

        @Test
        @DisplayName("UserId를 받아서 유저의 스케쥴 리스트를 받는다")
        void 정상_반환_유저(){
            // given
            Program mockProgram = mock(Program.class);
            Schedule mockSchedule = mock(Schedule.class);
            Center mockCenter = mock(Center.class);

            given(mockSchedule.getProgram()).willReturn(mockProgram);
            given(mockProgram.getCenter()).willReturn(mockCenter);
            given(mockSchedule.getProgram().getFirDay()).willReturn("월");
            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(List.of(mockSchedule));

            // when
            List<UserScheduleDto> result = userScheduleService.getDetailedSchedulesForUser(TEST_USER_ID);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).dates()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("UserScheduleServiceTest - 비정상 반환")
    class 비정상_반환{

        @Test
        @DisplayName("스케줄이 존재하지 않는 경우 오류 반환")
        void 스케줄_존재_x(){
            // given
            Schedule mockSchedule = mock(Schedule.class);

            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(null);

            // when & then
            assertThatThrownBy(() -> userScheduleService.getDetailedSchedulesForUser(TEST_USER_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("해당 일정을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("스케줄이 빈 리스트인 경우 오류 반환")
        void 스케줄_빈리스트() {
            // given
            given(scheduleRepository.findByUserId(TEST_USER_ID)).willReturn(Collections.emptyList());

            // when & then
            assertThatThrownBy(() -> userScheduleService.getDetailedSchedulesForUser(TEST_USER_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("해당 일정을 찾을 수 없습니다.");
        }
    }
}
