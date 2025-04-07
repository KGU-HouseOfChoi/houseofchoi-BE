package com.noraknorak.user;

import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.domain.value.ResidentRegistrationNumber;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.service.UserRegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserRegisterServiceTest {
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private AuthCodeManager authCodeManager;

    @Test
    @DisplayName("정상적으로 회원가입할 수 있다.")
    void 정상_회원가입(){
        // given
        String phone = "01012345678";
        UserSignUpRequest request = new UserSignUpRequest("홍길동", phone, "0001013");

        // when
        userRegisterService.signUp(request);

        // then
        boolean exists = userRepository.existsByPhone(phone);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("중복된 전화번호로 회원가입시 예외 발생")
    void 중복_회원가입(){
        // given
        String phone = "01012345678";
        UserSignUpRequest request1 = new UserSignUpRequest("기존 유저", phone, "0001013");
        userRegisterService.signUp(request1);

        // when
        UserSignUpRequest request2 = new UserSignUpRequest("중복 유저", phone, "9901033");

        // then
        assertThatThrownBy(() -> userRegisterService.signUp(request2))
                .hasMessage(UserErrorCode.MULTIPLE_PHONE_ERROR.getMessage());
    }

    @Test
    @DisplayName("인증코드가 일치하면 true 반환 및 인증 코드 삭제")
    void 인증코드_성공_후_삭제(){
        // given
        String phone = "01012345678";
        String code = "000000";

        Mockito.when(authCodeManager.getCode(phone)).thenReturn(code);

        UserVerifyCodeRequest userVerifyCodeRequest = new UserVerifyCodeRequest(phone, code);

        // when
        boolean result = userRegisterService.verifyCode(userVerifyCodeRequest);

        // then
        assertThat(result).isTrue();
        Mockito.verify(authCodeManager).getCode(phone);
    }

    @Test
    @DisplayName("인증 코드가 없으면 예외 발생")
    void 인증코드_없음(){
        // given
        String phone = "01012345678";

        Mockito.when(authCodeManager.getCode(phone)).thenReturn(null);
        UserVerifyCodeRequest userVerifyCodeRequest = new UserVerifyCodeRequest(phone, null);

        // when, then
        assertThatThrownBy(() -> userRegisterService.verifyCode(userVerifyCodeRequest))
                .hasMessage(UserErrorCode.CODE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("잘못된 인증 코드가 들어오면 예외 발생")
    void 인증코드_잘못됨(){
        // given
        String phone = "01012345678";
        String code = "123456";

        Mockito.when(authCodeManager.getCode(phone)).thenReturn("654321");
        UserVerifyCodeRequest userVerifyCodeRequest = new UserVerifyCodeRequest(phone, code);

        // when, then
        assertThatThrownBy(() -> userRegisterService.verifyCode(userVerifyCodeRequest))
                .hasMessage(UserErrorCode.NOT_EQUAL_CODE.getMessage());
    }
}
