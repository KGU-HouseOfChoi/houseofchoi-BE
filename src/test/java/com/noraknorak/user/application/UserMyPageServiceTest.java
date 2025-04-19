package com.noraknorak.user.application;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMyPageServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserMyPageService userMyPageService;

    private static final String TEST_NAME = "홍길동";
    private static final String TEST_BIRTH = "19800101";

    @Nested
    class 마이페이지_정상_조회 {
        @Test
        @DisplayName("마이페이지 - 정상 조회")
        void 정상_조회() {
            // given
            Long userId = 1L;
            Long relatedUserId = 2L;

            User user = mock(User.class);
            User relatedUser = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            when(user.getId()).thenReturn(userId);
            when(user.getName()).thenReturn(TEST_NAME);
            when(user.getRelatedUser()).thenReturn(relatedUserId);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.findById(relatedUserId)).thenReturn(Optional.of(relatedUser));

            when(relatedUser.getName()).thenReturn("김철수");
            when(relatedUser.getBirth()).thenReturn(TEST_BIRTH);

            // when
            UserMyPageResponse result = userMyPageService.getMyPageInfo(customUserDetails);

            // then
            assertEquals(TEST_NAME, result.name());
            assertEquals("김철수", result.relatedUserName());
            assertEquals(TEST_BIRTH, result.relatedUserBirth());
        }

        @Test
        @DisplayName("마이페이지 - 연결된 유저 없는 경우 테스트")
        void 정상_조회_연결된_유저_없음() {
            // given
            Long userId = 1L;

            User user = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            when(user.getId()).thenReturn(userId);
            when(user.getName()).thenReturn(TEST_NAME);
            when(user.getRelatedUser()).thenReturn(null);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            // when
            UserMyPageResponse result = userMyPageService.getMyPageInfo(customUserDetails);

            // then
            assertEquals(TEST_NAME, result.name());
            assertNull(result.relatedUserName());
            assertNull(result.relatedUserBirth());
        }
    }

    @Nested
    class 마이페이지_예외_발생{
        @Test
        @DisplayName("마이페이지 - 유저 없는 경우")
        void 유저_없음_예외(){
            // given
            Long userId = 1L;
            User user = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            when(user.getId()).thenReturn(userId);

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> userMyPageService.getMyPageInfo(customUserDetails))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("유저 정보를 불러오지 못했습니다.");
        }

        @Test
        @DisplayName("마이페이지 - 연결된 유저가 존재하지 않는 경우")
        void 연결된_유저_없음_예외(){
            // given
            Long userId = 1L;
            Long relatedUserId = 2L;

            User user = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            when(user.getId()).thenReturn(userId);
            when(user.getId()).thenReturn(userId);
            when(user.getRelatedUser()).thenReturn(relatedUserId);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.findById(relatedUserId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> userMyPageService.getMyPageInfo(customUserDetails))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("유저 정보를 불러오지 못했습니다.");
        }
    }
}
