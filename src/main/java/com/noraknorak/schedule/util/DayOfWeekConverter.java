package com.noraknorak.schedule.util;

import java.time.DayOfWeek;
import java.util.Optional;

public class DayOfWeekConverter {

    public static Optional<DayOfWeek> fromKorean(String koreanDay) {
        if (koreanDay == null || koreanDay.isBlank()) {
            return Optional.empty();
        }

        return switch (koreanDay) {
            case "월" -> Optional.of(DayOfWeek.MONDAY);
            case "화" -> Optional.of(DayOfWeek.TUESDAY);
            case "수" -> Optional.of(DayOfWeek.WEDNESDAY);
            case "목" -> Optional.of(DayOfWeek.THURSDAY);
            case "금" -> Optional.of(DayOfWeek.FRIDAY);
            default -> Optional.empty();
        };
    }
}
