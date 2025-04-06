package com.noraknorak.user.presentation;

import com.noraknorak.core.config.exception.GlobalErrorCode;
import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.domain.dto.request.UserSignUpRequest;
import com.noraknorak.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API", description = "사용자로부터 회원 정보를 입력받아 회원가입을 진행합니다.")
    @ApiErrorCode(GlobalErrorCode.class)
    @PostMapping("/signup")
    public ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

}
