package com.noraknorak.user.presentation;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyCodeRequest;
import com.noraknorak.user.presentation.dto.request.UserVerifyRelatedUserRequest;
import com.noraknorak.user.presentation.swagger.UserRegisterSwagger;
import com.noraknorak.user.service.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRegisterController implements UserRegisterSwagger {

    private final UserRegisterService userRegisterService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userRegisterService.signUp(userSignUpRequest);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

    @Override
    @PostMapping("/code/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyCode(@Valid @RequestBody UserVerifyCodeRequest userVerifyCodeRequest) {
        boolean result = userRegisterService.verifyCode(userVerifyCodeRequest);
        return ResponseEntity.ok(new RestResponse<>(result));
    }

    @Override
    @PostMapping("/relation/verify")
    public ResponseEntity<RestResponse<Boolean>> verifyRelatedUser(
            @Valid @RequestBody UserVerifyRelatedUserRequest userVerifyRelatedUserRequest
    ) {
        // 1. 인증 코드 검증
        User user = userRegisterService.validateUserCode(userVerifyRelatedUserRequest.code());

        // 2. 관계 설정하기
        // 현재 접속 중인 유저가 자식이면 부모의 코드를 활용해서 나를 보호자로 만든다
        // TODO: JWT 적용시 Auth토큰 검증하여 현재 본인 정보 얻어오기
        userRegisterService.verifyRelatedUser(
                userVerifyRelatedUserRequest.id(),
                userVerifyRelatedUserRequest.role(),
                user.getId()
        );
        return ResponseEntity.ok(new RestResponse<>(true));
    }


}
