package com.noraknorak.schedule.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
public class DayOfWeekConverterTest {

    private static final String INVALID_INPUT = "오늘 날짜";

    @Nested
    @DisplayName("DayOfWeekConverterTest - 정상 입력 케이스")
    class 정상_입력 {
        @ParameterizedTest(name = "한글 요일 {0}요일은 {1}로 변환된다")
        @CsvSource({
            "월, MONDAY",
            "화, TUESDAY",
            "수, WEDNESDAY",
            "목, THURSDAY",
            "금, FRIDAY"
        })
        void 정상_입력_반환_테스트(String korean, DayOfWeek expected) {
            Optional<DayOfWeek> result = DayOfWeekConverter.fromKorean(korean);
            assertThat(result).contains(expected);
        }
    }

    @Nested
    @DisplayName("DayOfWeekConverterTest - 비정상 입력 케이스")
    class 비정상_입력{

        @Test
        @DisplayName("잘못된 입력값은 Optinal.empty() 반환된다.")
        void 비정상_이상한_입력(){
            // given & when
            Optional<DayOfWeek> result = DayOfWeekConverter.fromKorean(INVALID_INPUT);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("빈 문자열은 Optinal.empty() 반환 한다")
        void 비정상_빈값(){
            // given & when
            Optional<DayOfWeek> result = DayOfWeekConverter.fromKorean(null);

            // then
            assertThat(result).isEmpty();
        }
    }
}
