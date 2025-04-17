package com.noraknorak.user;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.service.UserRelationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRelationServiceTest {

    @InjectMocks
    private UserRelationService userRelationService;

    @Mock
    private UserRepository userRepository;

    private final Long USER_ID = 1L;
    private final Long RELATED_USER_ID = 2L;
    private final String VALID_USER_CODE = "validCode";
    private final String INVALID_USER_CODE = "invalidCode";

    @Nested
    class 부모_자식_연동_검증 {

        @Test
        @DisplayName("유효한 부모/자식 코드 검증")
        void 부모_자식_코드_검증_정상() {
            // given
            User mockUser = mock(User.class);
            given(userRepository.findByUserCode(VALID_USER_CODE)).willReturn(Optional.of(mockUser));

            // when
            User result = userRelationService.validateUserCode(VALID_USER_CODE);

            // then
            assertThat(result).isEqualTo(mockUser);
        }

        @Test
        @DisplayName("잘못된 부모/자식 코드 -> 예외 발생")
        void 부모_자식_코드_검증_잘못된_코드() {
            // given
            given(userRepository.findByUserCode(INVALID_USER_CODE)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> userRelationService.validateUserCode(INVALID_USER_CODE))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.NOT_EQUAL_USER_CODE.getMessage());
        }
    }

    @Nested
    class 부모_자식_관계_설정 {

        @Test
        @DisplayName("부모-자식 관계 설정: SENIOR -> GUARDIAN")
        void 부모_자식_관계_설정_정상() {
            // given
            User mockUser = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);
            given(mockUser.getId()).willReturn(USER_ID);

            // when
            userRelationService.verifyRelatedUser(customUserDetails, Role.SENIOR.getName(), RELATED_USER_ID);

            // then
            verify(userRepository).updateUserRoleAndRelatedUser(USER_ID, Role.GUARDIAN, RELATED_USER_ID);
        }

        @Test
        @DisplayName("부모-자식 관계 설정: GUARDIAN -> SENIOR")
        void 부모_자식_관계_설정_역할반대() {
            // given
            User mockUser = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);
            given(mockUser.getId()).willReturn(USER_ID);

            // when
            userRelationService.verifyRelatedUser(customUserDetails, Role.GUARDIAN.getName(), RELATED_USER_ID);

            // then
            verify(userRepository).updateUserRoleAndRelatedUser(USER_ID, Role.SENIOR, RELATED_USER_ID);
        }

        @Test
        @DisplayName("잘못된 역할 -> 예외 발생")
        void 부모_자식_관계_설정_잘못된_역할() {
            // given
            User mockUser = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);
            given(mockUser.getId()).willReturn(USER_ID);

            // when & then
            assertThatThrownBy(() -> userRelationService.verifyRelatedUser(customUserDetails, "INVALID_ROLE", RELATED_USER_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.INVALID_ROLE_ERROR.getMessage());
        }

        @Test
        @DisplayName("자기 자신과의 관계 설정 x")
        void 자기_자신_관계_설정_x(){
            // given
            User mockUser = mock(User.class);
            CustomUserDetails customUserDetails = new CustomUserDetails(mockUser);
            given(mockUser.getId()).willReturn(USER_ID);

            // when & then
            assertThatThrownBy(() -> userRelationService.verifyRelatedUser(customUserDetails, Role.SENIOR.getName(), USER_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessage(UserErrorCode.SELF_RELATION_NOT_ALLOWED.getMessage());
        }
    }

}
