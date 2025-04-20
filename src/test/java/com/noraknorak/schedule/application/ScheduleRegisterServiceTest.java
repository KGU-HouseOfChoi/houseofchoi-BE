package com.noraknorak.schedule.application;

import com.noraknorak.center.domain.Center;
import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.repository.ProgramRepository;
import com.noraknorak.schedule.domain.Schedule;
import com.noraknorak.schedule.domain.repository.ScheduleRepository;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleRegisterServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProgramRepository programRepository;

    @InjectMocks
    ScheduleRegisterService scheduleRegisterService;

    private static final Long TEST_USER_ID = 1L;
    private static final Long TEST_PROGRAM_ID = 2L;

    @Nested
    @DisplayName("ScheduleRegisterServiceTest - 정상 저장 테스트")
    class 정상_동작{

        @Test
        @DisplayName("정상 저장 테스트")
        void 정상_저장(){
            // given
            User user = mock(User.class);
            Program program = mock(Program.class);
            Center center = mock(Center.class);

            given(scheduleRepository.existsByUserIdAndProgramId(TEST_USER_ID, TEST_PROGRAM_ID)).willReturn(false);
            given(userRepository.findById(TEST_USER_ID)).willReturn(Optional.of(user));
            given(programRepository.findById(TEST_PROGRAM_ID)).willReturn(Optional.of(program));
            given(program.getCenter()).willReturn(center);

            // when
            scheduleRegisterService.registerSchedule(TEST_USER_ID, TEST_PROGRAM_ID);

            // then
            verify(scheduleRepository, times(1)).save(any(Schedule.class));
        }
    }

    @Nested
    @DisplayName("ScheduleRegisterServiceTest - 저장 실패 테스트")
    class 저장_실패{

        @Test
        @DisplayName("중복된 일정 저장시 예외 발생")
        void 중복_일정(){
            // given
            given(scheduleRepository.existsByUserIdAndProgramId(TEST_USER_ID, TEST_PROGRAM_ID)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> scheduleRegisterService.registerSchedule(TEST_USER_ID, TEST_PROGRAM_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("이미 등록된 일정입니다.");
        }

        @Test
        @DisplayName("존재하지 않는 사용자일 경우 예외 발생")
        void 사용자_없음() {
            // given
            given(scheduleRepository.existsByUserIdAndProgramId(TEST_USER_ID, TEST_PROGRAM_ID)).willReturn(false);
            given(userRepository.findById(TEST_USER_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> scheduleRegisterService.registerSchedule(TEST_USER_ID, TEST_PROGRAM_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("유저 정보를 불러오지 못했습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 프로그램인 경우 예외 발생")
        void 프로그램_없음() {
            // given
            given(scheduleRepository.existsByUserIdAndProgramId(TEST_USER_ID, TEST_PROGRAM_ID)).willReturn(false);
            given(userRepository.findById(TEST_USER_ID)).willReturn(Optional.of(mock(User.class)));
            given(programRepository.findById(TEST_PROGRAM_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> scheduleRegisterService.registerSchedule(TEST_USER_ID, TEST_PROGRAM_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("프로그램 정보를 불러오지 못했습니다.");
        }
    }
}
