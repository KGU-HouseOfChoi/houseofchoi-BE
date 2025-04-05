package com.noraknorak.user.service;

import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.domain.dto.request.UserSignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 가입된 전화번호입니다.");
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .role(Role.GUARDIAN)
                .build();

        userRepository.save(user);
    }

}
