package com.noraknorak.user.presentation;

import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserVerifyRelatedUserRequestTest {

    private Validator validator;

    private static final String TEST_ROLE = "부모";
    private static final String TEST_CODE = "q1w2e3";

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("정상 입력이 들어온 경우")
    void 정상_입력(){
        // given
        UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest(TEST_ROLE, TEST_CODE);

        // When
        Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @Nested
    class 역할_오류{

        @Test
        @DisplayName("역할 오류 - 부모 자식 외의 다른 입력값이 들어온 경우")
        void 역할_오류_다른_입력(){
            // given
            UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest("형제", TEST_CODE);

            // when
            Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("role은 '부모' 또는 '자식'이어야 합니다.");
        }

        @Test
        @DisplayName("역할 오류 - 역할 정보가 비어 있는 경우")
        void 역할_오류_빈값(){
            // given
            UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest(null, TEST_CODE);

            // when
            Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("부모/자식 정보를 입력해 주세요");
        }
    }
    
    @Nested
    class 유저코드_오류{
        
        @Test
        @DisplayName("입력 코드 오류 - 코드의 글자 수가 맞지 않는 경우")
        void 코드_형식_오류_글자수(){
            // given
            UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest(TEST_ROLE, "1234567");
            
            // when
            Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);
            
            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("인증 코드는 6자리 영문자 또는 숫자여야 합니다.");
        }

        @Test
        @DisplayName("입력 코드 오류 - 코드의 글자 수가 맞지 않는 경우")
        void 코드_형식_오류_특수문자(){
            // given
            UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest(TEST_ROLE, "12345!");

            // when
            Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("인증 코드는 6자리 영문자 또는 숫자여야 합니다.");
        }

        @Test
        @DisplayName("입력 코드 오류 - 빈값이 들어온 경우")
        void 코드_빈값(){
            // given
            UserVerifyRelatedUserRequest request = new UserVerifyRelatedUserRequest(TEST_ROLE, null);

            // when
            Set<ConstraintViolation<UserVerifyRelatedUserRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("인증 코드를 입력해 주세요");
        }
    }
}
