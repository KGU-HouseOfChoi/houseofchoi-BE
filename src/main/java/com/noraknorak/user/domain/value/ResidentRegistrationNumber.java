package com.noraknorak.user.domain.value;

import com.noraknorak.user.exception.UserErrorCode;
import lombok.Getter;

@Getter
public record ResidentRegistrationNumber(
        String raw
) {
    public ResidentRegistrationNumber {
        if (raw == null || raw.length() < 7) {
            throw UserErrorCode.INVALID_RESIDENT_NUMBER.toException();
        }
    }

    public String extractBirthDate() {
        return raw.substring(0, raw.length() - 1);
    }

    public String extractGender() {
        int lastDigit = Character.getNumericValue(raw.charAt(raw.length() - 1));
        return lastDigit % 2 == 0 ? "여자" : "남자";
    }
}
