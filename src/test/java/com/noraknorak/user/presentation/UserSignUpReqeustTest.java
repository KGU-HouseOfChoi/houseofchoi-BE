package com.noraknorak.user.presentation;

import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserSignUpReqeustTest {

    private Validator validator;

    private static final String TEST_NAME = "홍길동";
    private static final String TEST_PHONE = "01012345678";
    private static final String TEST_BIRTH = "0202193";
    private static final String TEST_CODE = "123456";

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("UserSignUpReqeustTest - 정상 요청")
    class 정상_요청{

        @Test
        @DisplayName("모든 값이 정상 요청인 경우")
        void 모두_정상_값_전달(){
            // given
            UserSignUpRequest request = new UserSignUpRequest(
                    TEST_NAME,
                    TEST_PHONE,
                    TEST_BIRTH,
                    TEST_CODE
            );

            // when
            Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

            // then
            Assertions.assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("전화번호와 코드만 전달된 경우")
        void 전화번호_코드만_전달도_정상_작동(){
            // given
            UserSignUpRequest request = new UserSignUpRequest(
                    null,
                    TEST_PHONE,
                    null,
                    TEST_CODE
            );

            // when
            Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

            // then
            Assertions.assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("UserSignUpReqeustTest - 비정상 요청")
    class 비정상_요청{

        @Nested
        @DisplayName("전화번호 관련 비정상 요청")
        class 전화번호_비정상_요청{

            @Test
            @DisplayName("전화번호가 null인 경우")
            void 전화번호_빈칸(){
                // given
                UserSignUpRequest request = new UserSignUpRequest(
                        TEST_NAME,
                        null,
                        TEST_BIRTH,
                        TEST_CODE
                );

                // when
                Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

                // then
                Assertions.assertThat(violations).hasSize(1);
                Assertions.assertThat(violations.iterator().next().getMessage())
                        .isEqualTo("전화번호를 입력하셔야 합니다.");
            }

            @Test
            @DisplayName("전화번호 형식이 잘못된 경우")
            void 전화번호_빈문자열(){
                // given
                UserSignUpRequest request = new UserSignUpRequest(
                        TEST_NAME,
                        "011123456789",
                        TEST_BIRTH,
                        TEST_CODE
                );

                // when
                Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

                // then
                Assertions.assertThat(violations).hasSize(1);
                Assertions.assertThat(violations.iterator().next().getMessage())
                        .isEqualTo("전화번호 형식이 잘못되었습니다.");
            }
        }

        @Nested
        @DisplayName("인증 코드 관련 비정상 요청")
        class 인증코드_비정상_요청{

            @Test
            @DisplayName("인증코드가 빈 문자열인 경우")
            void 인증코드_빈문자열(){
                // given
                UserSignUpRequest request = new UserSignUpRequest(
                        TEST_NAME,
                        TEST_PHONE,
                        TEST_BIRTH,
                        null
                );

                // when
                Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

                // then
                Assertions.assertThat(violations).hasSize(1);
                Assertions.assertThat(violations.iterator().next().getMessage())
                        .isEqualTo("인증 코드를 입력해야 합니다.");
            }

            @Test
            @DisplayName("인증코드 형식이 잘못된 경우 경우")
            void 인증코드_잘못된_형식(){
                // given
                UserSignUpRequest request = new UserSignUpRequest(
                        TEST_NAME,
                        TEST_PHONE,
                        TEST_BIRTH,
                        "abcdef"
                );

                // when
                Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

                // then
                Assertions.assertThat(violations).hasSize(1);
                Assertions.assertThat(violations.iterator().next().getMessage())
                        .isEqualTo("인증번호는 6자리 숫자입니다.");
            }
        }
    }
}
