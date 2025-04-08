package com.noraknorak.auth.application;


import com.noraknorak.auth.domain.TokenProvider;
import com.noraknorak.auth.dto.request.TokenRequest;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public String provideAccessToken(TokenRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
        return tokenProvider.provideAccessToken(user);
    }
}
