package com.noraknorak.schedule.util;

import com.noraknorak.schedule.util.DayOfWeekConverter;
import com.noraknorak.program.domain.Program;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleDateUtils {

    public static List<LocalDate> extractProgramDates(Program program) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusMonths(1);

        // Program 객체에서 요일 문자열 가져오기
        List<String> rawDays = List.of(
                program.getFirDay(),
                program.getSecDay(),
                program.getThrDay(),
                program.getFouDay(),
                program.getFivDay()
        );

        // 유효한 DayOfWeek만 추출
        Set<DayOfWeek> targetDays = rawDays.stream()
                .filter(Objects::nonNull)
                .map(DayOfWeekConverter::fromKorean)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        // 한 달 간 날짜 중 해당 요일만 수집
        List<LocalDate> result = new ArrayList<>();
        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (targetDays.contains(date.getDayOfWeek())) {
                result.add(date);
            }
        }

        return result;
    }
}
