package com.noraknorak.user.application;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import com.noraknorak.user.presentation.dto.response.UserMyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

    private final UserRepository userRepository;

    //마이페이지 정보 조회
    public UserMyPageResponse getMyPageInfo(CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.user().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

        String relatedUserName = null;
        String relatedUserBirth = null;

        if (user.getRelatedUser() != null) {
            User related = userRepository.findById(user.getRelatedUser())
                    .orElseThrow(() -> UserErrorCode.USER_NOT_FOUND.toException());

            relatedUserName = related.getName();
            relatedUserBirth = related.getBirth();
        }

        return UserMyPageResponse.builder()
                .name(user.getName())
                .relatedUserName(relatedUserName)
                .relatedUserBirth(relatedUserBirth)
                .build();
    }
}
