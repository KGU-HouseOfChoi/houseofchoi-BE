package com.noraknorak.user.application;

import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationDeleteService {

    private final UserRepository userRepository;

    @Transactional
    public void deleteRelation(CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        User me = userRepository.findById(userId)
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);

        if (me.getRelatedUser() != null) {
            User relatedUser = userRepository.findById(me.getRelatedUser())
                    .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
            userRepository.updateUserRoleAndRelatedUser(relatedUser.getId(), null, null);
        }

        userRepository.updateUserRoleAndRelatedUser(me.getId(), null, null);
    }
}
