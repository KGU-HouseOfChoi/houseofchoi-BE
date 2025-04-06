package com.noraknorak.user.presentation;

import com.noraknorak.core.config.exception.GlobalErrorCode;
import com.noraknorak.core.config.swagger.ApiErrorCode;
import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import com.noraknorak.user.presentation.swagger.UserSwagger;
import com.noraknorak.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserSwagger {

    private final UserService userService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<RestResponse<Boolean>> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

}
