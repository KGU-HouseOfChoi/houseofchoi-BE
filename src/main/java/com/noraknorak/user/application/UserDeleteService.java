package com.noraknorak.user.application;

import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeleteService {
    private final UserRepository userRepository;

    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        List<User> relatedUsers = userRepository.findAllByRelatedUserId(userId);
        for (User related : relatedUsers) {
            related.clearRelation();
        }

        userRepository.deleteById(userId);
    }
}
