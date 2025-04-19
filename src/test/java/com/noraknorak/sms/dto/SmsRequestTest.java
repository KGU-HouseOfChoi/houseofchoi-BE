package com.noraknorak.sms.dto;

import com.noraknorak.sms.presentaion.request.SmsRequest;
import jakarta.validation.*;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsRequestTest {

    private Validator validator;

    private static final String TEST_PHONE = "01012345678";

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("SmsRequest - 정상 입력")
    class 정상_입력{

        @Test
        @DisplayName("정상적으로 입력된 경우")
        void 정상_입력(){
            // given
            SmsRequest request = new SmsRequest(TEST_PHONE);

            // when
            Set<ConstraintViolation<SmsRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("SmsRequest - 잘못된 입력")
    class 잘못된_입력{

        @Test
        @DisplayName("null 값 입력의 경우")
        void 전화번호_null(){
            // given
            SmsRequest request = new SmsRequest(null);

            // when
            Set<ConstraintViolation<SmsRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("휴대폰 번호를 입력해주세요");
        }

        @Test
        @DisplayName("정상 자리수 아닌 경우")
        void 전화번호_자리수_오류(){
            // given
            SmsRequest request = new SmsRequest("010123456789");

            // when
            Set<ConstraintViolation<SmsRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("올바른 휴대폰 번호 형식이 아닙니다");
        }

        @Test
        @DisplayName("형식에 맞지 않는 경우")
        void 전화번호_형식_오류(){
            // given
            SmsRequest request = new SmsRequest("010-1234-5678");

            // when
            Set<ConstraintViolation<SmsRequest>> violations = validator.validate(request);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("올바른 휴대폰 번호 형식이 아닙니다");
        }
    }
}
