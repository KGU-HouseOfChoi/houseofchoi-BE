package com.noraknorak.auth.application;

import com.noraknorak.auth.domain.TokenProvider;
import com.noraknorak.auth.dto.request.TokenRequest;
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

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TokenService tokenService;


    @Test
    @DisplayName("토큰 발급 테스트 성공")
    void 토큰_발급_성공(){
        // given
        Optional<User> user = Optional.of(mock(User.class));

        TokenRequest tokenRequest = new TokenRequest(1L);
        when(userRepository.findById(1L)).thenReturn(user);

        // when
        tokenService.provideAccessToken(tokenRequest);

        // then
        verify(tokenProvider, times(1)).provideAccessToken(user.get());
    }

    @Test
    @DisplayName("토큰 발급 실패 - 존재하지 않는 유저")
    void 토큰_발급_실패_유저_x(){
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> tokenService.provideAccessToken(tokenRequest))
                .isInstanceOf(UserErrorCode.USER_NOT_FOUND.toException().getClass())
                .hasMessageContaining("유저 정보를 불러오지 못했습니다.");
    }
}
