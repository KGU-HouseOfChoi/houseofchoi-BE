package com.noraknorak.user.service;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.domain.value.ResidentRegistrationNumber;
import com.noraknorak.user.domain.value.UserCodeGenerator;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterService {

    private final UserRepository userRepository;
    private final AuthCodeManager authCodeManager;

    //유저 등록
    @Transactional
    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByPhone(request.phone())) {
            throw UserErrorCode.MULTIPLE_PHONE_ERROR.toException();
        }

        ResidentRegistrationNumber residentRegistrationNumber
                = new ResidentRegistrationNumber(request.birth());

        User user = User.builder()
                .name(request.name())
                .phone(request.phone())
                .birth(residentRegistrationNumber.extractBirthDate())
                .gender(residentRegistrationNumber.extractGender())
                .userCode(UserCodeGenerator.generateUserCode())
                .build();

        userRepository.save(user);
    }

    // 문자인증 코드 검증
    public boolean verifyCode(UserVerifyCodeRequest userVerifyCodeRequest){
        String storedCode = authCodeManager.getCode(userVerifyCodeRequest.phoneNum());

        if(storedCode == null){
            throw UserErrorCode.CODE_NOT_FOUND.toException();
        }

        if (storedCode.equals(userVerifyCodeRequest.code())) {
            authCodeManager.deleteCode(userVerifyCodeRequest.phoneNum());
            return true;
        }
        throw UserErrorCode.NOT_EQUAL_CODE.toException();
    }

    // 부모/자식 연동 코드 검증
    public User validateUserCode(String code) {
        return userRepository.findByUserCode(code)
                .orElseThrow(() -> UserErrorCode.NOT_EQUAL_USER_CODE.toException());
    }

    // 부모/자식 관계 설정
    @Transactional
    public void verifyRelatedUser(Long userId, String role, Long relatedUserId){
        try{
            if(Role.SENIOR.getName().equals(role)){
                userRepository.updateUserRoleAndRelatedUser(userId, Role.GUARDIAN, relatedUserId);
            }else if(Role.GUARDIAN.getName().equals(role)){
                userRepository.updateUserRoleAndRelatedUser(userId, Role.SENIOR, relatedUserId);
            }else{
                throw UserErrorCode.INVALID_ROLE_ERROR.toException();
            }
        } catch (DomainException e) {
            throw e;
        }
        catch (Exception e){
            throw UserErrorCode.INTERNAL_SERVER_ERROR.toException();
        }
    }
}
