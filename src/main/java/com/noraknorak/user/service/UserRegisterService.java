package com.noraknorak.user.service;

import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.value.ResidentRegistrationNumber;
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

    //유저 등록
    @Transactional
    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw UserErrorCode.MULTIPLE_PHONE_ERROR.toException();
        }

        ResidentRegistrationNumber residentRegistrationNumber
                = new ResidentRegistrationNumber(request.getBirth());

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .birth(residentRegistrationNumber.extractBirthDate())
                .gender(residentRegistrationNumber.extractGender())
                .build();

        userRepository.save(user);
    }

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
}
