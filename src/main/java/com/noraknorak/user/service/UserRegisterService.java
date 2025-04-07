package com.noraknorak.user.service;

import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.sms.exception.SmsErrorCode;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final AuthCodeManager authCodeManager;

    /**
     * Registers a new user.
     *
     * <p>Validates that the provided phone number is not already registered. If a user with the same phone number exists,
     * an exception is thrown. Otherwise, it creates a new user using the sign-up details—deriving the birthday and gender
     * from the provided birth string—and persists the user in the repository.</p>
     *
     * @param request the sign-up request containing user data such as name, phone number, and birth information
     * @throws RuntimeException if the phone number is already registered
     */
    @Transactional
    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw UserErrorCode.MULTIPLE_PHONE_ERROR.toException();
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .birth(getBirthday(request.getBirth()))
                .gender(getGenderByBirth(request.getBirth()))
                .build();

        userRepository.save(user);
    }

    /**
     * Verifies the authentication code from the provided request.
     * 
     * <p>This method retrieves the stored code associated with the phone number in the request
     * and compares it with the provided code. If the stored code is not found, it throws a CODE_NOT_FOUND
     * exception. If the stored code matches the provided code, the method deletes the stored code and
     * returns {@code true}; otherwise, it throws a NOT_EQUAL_CODE exception.</p>
     * 
     * @param userVerifyCodeRequest the request containing the phone number and the code to verify
     * @return {@code true} if the authentication code matches the stored code
     */
    public boolean verifyCode(UserVerifyCodeRequest userVerifyCodeRequest){
        String storedCode = authCodeManager.getCode(userVerifyCodeRequest.getPhoneNum());

        if(storedCode == null){
            throw UserErrorCode.CODE_NOT_FOUND.toException();
        }

        if (storedCode.equals(userVerifyCodeRequest.getCode())) {
            authCodeManager.deleteCode(userVerifyCodeRequest.getPhoneNum());
            return true;
        }
        throw UserErrorCode.NOT_EQUAL_CODE.toException();
    }

    /**
     * Determines the gender based on the last digit of the provided birth string.
     *
     * <p>The method extracts the numeric value of the last character of the string and returns "여자" (female) if
     * the value is even, or "남자" (male) if the value is odd.</p>
     *
     * @param birth the birth identifier string, where the last character indicates gender
     * @return "여자" if the extracted digit is even; otherwise, "남자"
     */
    private String getGenderByBirth(String birth) {
        int lastNum = Character.getNumericValue(birth.charAt(birth.length() - 1));

        // 주민번호 뒷자리 2,4인 경우 여성
        if(lastNum % 2 == 0 ) {
            return "여자";
        }
        else {
            return "남자";
        }
    }

    private String getBirthday(String birth) {
        return birth.substring(0, birth.length() - 1);
    }
}
