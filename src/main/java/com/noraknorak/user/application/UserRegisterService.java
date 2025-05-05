package com.noraknorak.user.application;

import com.noraknorak.auth.application.RefreshTokenService;
import com.noraknorak.auth.application.TokenService;
import com.noraknorak.auth.dto.request.TokenRequest;
import com.noraknorak.auth.exception.AuthenticationErrorCode;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.domain.value.ResidentRegistrationNumber;
import com.noraknorak.user.domain.value.UserCodeGenerator;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.response.UserNewTokenResponse;
import com.noraknorak.user.presentation.dto.response.UserSignUpResult;
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
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    //유저 등록
    @Transactional
    public UserSignUpResult signUp(UserSignUpRequest request) {
        String phoneNum = request.phone();

        // 1. 인증 코드 검증
        String storedCode = authCodeManager.getCode(phoneNum);

        if (storedCode == null) {
            throw UserErrorCode.CODE_NOT_FOUND.toException();
        }

        if (!storedCode.equals(request.code())) {
            throw UserErrorCode.NOT_EQUAL_CODE.toException();
        }

        // 2. 기존 유저인 경우
        User user;
        boolean isNewUser;

        if (userRepository.existsByPhone(phoneNum)) {
            user = userRepository.findByPhone(phoneNum)
                    .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
            isNewUser = false;
        } else {
            ResidentRegistrationNumber rrn = new ResidentRegistrationNumber(request.birth());

            user = User.builder()
                    .name(request.name())
                    .phone(request.phone())
                    .birth(rrn.extractBirthDate())
                    .gender(rrn.extractGender())
                    .userCode(UserCodeGenerator.generateUserCode())
                    .build();

            userRepository.save(user);
            isNewUser = true;
        }

        return new UserSignUpResult(user, isNewUser);
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

    @Transactional
    public UserNewTokenResponse issueNewToken(String refreshToken){
        if (refreshToken == null || refreshToken.isBlank()) {
            throw AuthenticationErrorCode.EMPTY_TOKEN.toException();
        }

        Long id = tokenService.getUserId(refreshToken);

        User user = userRepository.findById(id)
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);

        String newAccessToken = tokenService.provideAccessToken(new TokenRequest(user.getId()));
        String newRefreshToken = tokenService.provideRefreshToken(new TokenRequest(user.getId()));

        refreshTokenService.delete(user.getId());
        refreshTokenService.save(user.getId(), newRefreshToken);

        return UserNewTokenResponse.of(newAccessToken, newRefreshToken);
    }
}
