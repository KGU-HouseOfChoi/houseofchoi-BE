package com.noraknorak.user.service;

import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.request.UserSignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //유저 등록
    @Transactional
    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw UserErrorCode.MULTIPLE_PHONE_ERROR.toException();
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .birth(request.getBirth())
                .gender(request.getGender())
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

}
