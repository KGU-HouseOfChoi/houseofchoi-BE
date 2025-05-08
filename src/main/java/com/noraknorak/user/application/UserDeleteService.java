package com.noraknorak.user.application;

import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeleteService {
    private final UserRepository userRepository;

    @Transactional
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw UserErrorCode.USER_NOT_FOUND.toException();
        }
        userRepository.deleteById(userId);
    }
}
