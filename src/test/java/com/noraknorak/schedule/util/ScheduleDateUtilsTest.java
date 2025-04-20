package com.noraknorak.schedule.util;

import com.noraknorak.program.domain.Program;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ScheduleDateUtilsTest {

    @Nested
    @DisplayName("ScheduleDateUtilsTest - 정상 입력 테스트")
    class 정상_입력{

        @Test
        @DisplayName("월/수요일만 입력된 경우 한달간의 월/수요일 날짜를 반환한다.")
        void 월_수요일_반환(){
            // given
            Program program = mock(Program.class);
            when(program.getFirDay()).thenReturn("월");
            when(program.getSecDay()).thenReturn("수");
            when(program.getThrDay()).thenReturn(null);
            when(program.getFouDay()).thenReturn(null);
            when(program.getFivDay()).thenReturn(null);

            // when
            List<LocalDate> result = ScheduleDateUtils.extractProgramDates(program);

            // then
            LocalDate today = LocalDate.now();
            LocalDate oneMonthLater = today.plusMonths(1);

            List<LocalDate> expected = today.datesUntil(oneMonthLater.plusDays(1))
                    .filter(d -> d.getDayOfWeek() == DayOfWeek.MONDAY || d.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                    .toList();

            assertThat(result).containsExactlyElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("ScheduleDateUtilsTest - 비정상 입력 테스트")
    class 비정상_입력{

        @Test
        @DisplayName("요일이 모두 null인 경우 빈 리스트 반환")
        void 요일_모두_null(){
            // given
            Program program = mock(Program.class);
            when(program.getFirDay()).thenReturn(null);
            when(program.getSecDay()).thenReturn(null);
            when(program.getThrDay()).thenReturn(null);
            when(program.getFouDay()).thenReturn(null);
            when(program.getFivDay()).thenReturn(null);

            // when
            List<LocalDate> result = ScheduleDateUtils.extractProgramDates(program);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("잘못된 요일(토/일)은 무시 결과 포함 x")
        void 잘못된_요일(){
            // given
            Program program = mock(Program.class);
            when(program.getFirDay()).thenReturn("토");
            when(program.getSecDay()).thenReturn("일");
            when(program.getThrDay()).thenReturn("월");
            when(program.getFouDay()).thenReturn(null);
            when(program.getFivDay()).thenReturn(null);

            // when
            List<LocalDate> result = ScheduleDateUtils.extractProgramDates(program);

            // then
            LocalDate today = LocalDate.now();
            LocalDate oneMonthLater = today.plusMonths(1);

            List<LocalDate> expected = today.datesUntil(oneMonthLater.plusDays(1))
                    .filter(d -> d.getDayOfWeek() == DayOfWeek.MONDAY)
                    .toList();

            assertThat(result).containsExactlyElementsOf(expected);
        }
    }
}
