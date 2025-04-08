package com.noraknorak.core.config.jwt;


import com.noraknorak.core.config.jwt.dto.request.TokenRequest;
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
