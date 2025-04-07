package com.noraknorak.user.service;

import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //유저 등록
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
