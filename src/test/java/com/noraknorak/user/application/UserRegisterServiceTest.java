package com.noraknorak.user.application;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.sms.domain.AuthCodeManager;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.service.UserRegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {
    @InjectMocks
    private UserRegisterService userRegisterService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthCodeManager authCodeManager;

    private final String TEST_NAME = "홍길동";
    private final String TEST_PHONE = "01012345678";
    private final String TEST_BIRTH = "9001011";
    private final String TEST_CODE = "123456";

    private final String TEST_NEW_NAME = "홍길동";
    private final String TEST_NEW_PHONE = "01087654321";

    private final String CORRECT_CODE = "123456";
    private final String WRONG_CODE = "654321";

    @Nested
    class 기존_유저_테스트 {
        @Test
        @DisplayName("기존 유저 - 정상 로그인 성공")
        void 기존_유저_정상_로그인(){
            // given
            UserSignUpRequest request = new UserSignUpRequest(TEST_NAME, TEST_PHONE, TEST_BIRTH, TEST_CODE);

            given(authCodeManager.getCode(request.phone())).willReturn(CORRECT_CODE);
            given(userRepository.existsByPhone(request.phone())).willReturn(true);

            User mockUser = mock(User.class);
            given(userRepository.findByPhone(request.phone())).willReturn(Optional.of(mockUser));

            // when
            User result = userRegisterService.signUp(request);

            // then
            assertThat(result).isEqualTo(mockUser);
        }

        @Test
        @DisplayName("인증 코드 없음 -> 예외 발생")
        void 인증코드_없음_예외() {
            // given
            UserSignUpRequest request = new UserSignUpRequest(TEST_NAME, TEST_PHONE, TEST_BIRTH, TEST_CODE);

            given(authCodeManager.getCode(TEST_PHONE)).willReturn(null);

            // when & then
            assertThatThrownBy(() -> userRegisterService.signUp(request))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.CODE_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("기존 유저 예외 -> 인증 코드 불일치")
        void 기존_유저_인증_코드_불일치(){
            // given
            UserSignUpRequest request = new UserSignUpRequest(TEST_NAME, TEST_PHONE, TEST_BIRTH, TEST_CODE);

            given(authCodeManager.getCode(TEST_PHONE)).willReturn(WRONG_CODE);

            // when & then
            assertThatThrownBy(() -> userRegisterService.signUp(request))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.NOT_EQUAL_CODE.getMessage());
        }
    }

    @Nested
    class 신규_유저_테스트{
        @Test
        @DisplayName("신규 유저 - 정상 저장 및 로그인 가능 테스트")
        void 신규_유저_정상_회원가입(){
            // given
            UserSignUpRequest request = new UserSignUpRequest(TEST_NEW_NAME, TEST_NEW_PHONE, TEST_BIRTH, TEST_CODE);

            given(authCodeManager.getCode(request.phone())).willReturn(CORRECT_CODE);
            given(userRepository.existsByPhone(request.phone())).willReturn(false);

            // 유저 저장시 그대로 반환
            given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

            // when
            User result = userRegisterService.signUp(request);

            // then
            assertThat(result.getPhone()).isEqualTo(TEST_NEW_PHONE);
            assertThat(result.getName()).isEqualTo(TEST_NEW_NAME);
        }
    }
}
