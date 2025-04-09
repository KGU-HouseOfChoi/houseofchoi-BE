package com.noraknorak.auth.application;

import com.noraknorak.auth.dto.request.LoginReqeust;
import com.noraknorak.auth.dto.request.TokenRequest;
import com.noraknorak.auth.dto.response.TokenResponse;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public TokenResponse login(LoginReqeust loginReqeust) {
        User user = userRepository.findByPhone(loginReqeust.phone())
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        String accessToken = tokenService.provideAccessToken(new TokenRequest(user.getId()));

        return TokenResponse.of(
                accessToken, user
        );
    }
}
