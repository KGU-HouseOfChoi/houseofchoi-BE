package com.noraknorak.auth.application;

import com.noraknorak.auth.dto.request.LoginReqeust;
import com.noraknorak.auth.dto.request.TokenRequest;
import com.noraknorak.auth.dto.response.TokenResponse;
import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공(){
        // given
        String phone = "01012345678";
        Long userId = 1L;
        String accessToken = "accessToken";

        LoginReqeust loginReqeust = new LoginReqeust(phone);

        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));
        when(tokenService.provideAccessToken(any(TokenRequest.class))).thenReturn(accessToken);

        // when
        TokenResponse tokenResponse = loginService.login(loginReqeust);

        // then
        assertEquals(accessToken, tokenResponse.accessToken());
        assertEquals(user, tokenResponse.user());

        verify(userRepository, times(1)).findByPhone(phone);
        verify(tokenService).provideAccessToken(any(TokenRequest.class));
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 유저")
    void 로그인_실패_유저_x(){
        // given
        String phone = "01012345678";
        LoginReqeust loginReqeust = new LoginReqeust(phone);

        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(
                ()->loginService.login(loginReqeust)
        ).isInstanceOf(DomainException.class)
                .hasMessage(UserErrorCode.USER_NOT_FOUND.getMessage());

        verify(userRepository, times(1)).findByPhone(phone);
    }
}
