package com.noraknorak.user;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.service.UserRegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {
    @InjectMocks
    private UserRegisterService userRegisterService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthCodeManager authCodeManager;

    @Test
    @DisplayName("정상적으로 회원가입할 수 있다.")
    void 정상_회원가입(){
        // given
        String phone = "01012345678";
        UserSignUpRequest request = new UserSignUpRequest("홍길동", phone, "0001013");
        given(userRepository.existsByPhone(phone)).willReturn(false);
        // when
        userRegisterService.signUp(request);

        // then
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("중복된 전화번호로 회원가입시 예외 발생")
    void 중복_회원가입(){
        // given
        String phone = "01012345678";
        UserSignUpRequest request = new UserSignUpRequest("유저", phone, "0001013");

        // when
        given(userRepository.existsByPhone(phone)).willReturn(true);

        // then
        assertThatThrownBy(() -> userRegisterService.signUp(request))
                .hasMessage(UserErrorCode.MULTIPLE_PHONE_ERROR.getMessage());
    }

    @Test
    @DisplayName("인증코드가 일치하면 true 반환 및 인증 코드 삭제")
    void 인증코드_성공_후_삭제(){
        // given
        String phone = "01012345678";
        String code = "000000";

        when(authCodeManager.getCode(phone)).thenReturn(code);

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

        when(authCodeManager.getCode(phone)).thenReturn(null);
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

        when(authCodeManager.getCode(phone)).thenReturn("654321");
        UserVerifyCodeRequest userVerifyCodeRequest = new UserVerifyCodeRequest(phone, code);

        // when, then
        assertThatThrownBy(() -> userRegisterService.verifyCode(userVerifyCodeRequest))
                .hasMessage(UserErrorCode.NOT_EQUAL_CODE.getMessage());
    }

    @Nested
    class 유저인증코드{
        @Test
        @DisplayName("유효한 유저 코드를 입력하면 유저를 반환한다")
        void 유효한_유저코드_입력시_유저반환() {
            // given
            String validCode = "ABC123";
            User mockUser = Mockito.mock(User.class); // 필요시 필드 채워줘
            given(userRepository.findByUserCode(validCode)).willReturn(Optional.of(mockUser));

            // when
            User result = userRegisterService.validateUserCode(validCode);

            // then
            assertThat(result).isEqualTo(mockUser);
        }

        @Test
        @DisplayName("잘못된 유저 코드를 입력하면 오류가 발생한다.")
        void 잘못된_유저_코드(){
            // given
            String invalidCode = "ERROR!";
            given(userRepository.findByUserCode(invalidCode)).willReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> userRegisterService.validateUserCode(invalidCode))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.NOT_EQUAL_USER_CODE.getMessage());
        }
    }
    
    @Nested
    @DisplayName("역할 설정관련 테스트")
    class 역할설정{
        @Test
        @DisplayName("역할이 부모이면 보호자로 설정되고 true 반환")
        void 역할_부모_true(){
            // given
            Long id = 1L;
            Long relatedId = 2L;
            String role = "부모";

            // when
            boolean result = userRegisterService.verifyRelatedUser(id, role, relatedId);

            // then
            verify(userRepository).updateUserByUserCode(id, Role.GUARDIAN, relatedId);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("역할이 자식이면 부모로 설정되고 true 반환")
        void 역할_자식_true(){
            // given
            Long id = 1L;
            Long relatedId = 2L;
            String role = "자식";

            // when
            boolean result = userRegisterService.verifyRelatedUser(id, role, relatedId);

            // then
            verify(userRepository).updateUserByUserCode(id, Role.SENIOR, relatedId);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("잘못된 역할로 설정되면 오류 반환")
        void 잘못된_역할(){
            // given
            Long userId = 1L;
            Long relatedUserId = 2L;
            String role = "친구";

            // when, then
            assertThatThrownBy(() -> userRegisterService.verifyRelatedUser(userId, role, relatedUserId))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.INVALID_ROLE_ERROR.getMessage());
        }

        @Test
        @DisplayName("역할 설정 중 알 수 없는 외부 오류 발생")
        void 오류_발생(){
            // given
            Long userId = 1L;
            Long relatedUserId = 2L;
            String role = "부모";

            doThrow(new RuntimeException("DB Error"))
                    .when(userRepository).updateUserByUserCode(userId, Role.GUARDIAN, relatedUserId);

            // when, then
            assertThatThrownBy(() -> userRegisterService.verifyRelatedUser(userId, role, relatedUserId))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
