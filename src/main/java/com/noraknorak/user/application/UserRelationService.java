package com.noraknorak.user.application;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.Personality;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.PersonalityRepository;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationService {

    private final UserRepository userRepository;
    private final PersonalityRepository personalityRepository;

    // 부모/자식 연동 코드 검증
    public User validateUserCode(String code) {
        return userRepository.findByUserCode(code.toLowerCase())
                .orElseThrow(UserErrorCode.NOT_EQUAL_USER_CODE::toException);
    }

    // 부모/자식 관계 설정 + 자식의 personality 태그를 부모로부터 복사
    @Transactional
    public void verifyRelatedUser(CustomUserDetails customUserDetails, String role, Long relatedUserId) {
        Long userId = customUserDetails.getId();

        if (userId.equals(relatedUserId)) {
            throw UserErrorCode.SELF_RELATION_NOT_ALLOWED.toException();
        }

        try {
            User me = userRepository.findById(userId)
                    .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);

            User relatedUser = userRepository.findById(relatedUserId)
                    .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);

            if (Role.SENIOR.getName().equals(role)) {
                userRepository.updateUserRoleAndRelatedUser(userId, Role.SENIOR, relatedUserId);
                userRepository.updateUserRoleAndRelatedUser(relatedUserId, Role.GUARDIAN, userId);

            } else if (Role.GUARDIAN.getName().equals(role)) {
                userRepository.updateUserRoleAndRelatedUser(userId, Role.GUARDIAN, relatedUserId);
                userRepository.updateUserRoleAndRelatedUser(relatedUserId, Role.SENIOR, userId);

                if (relatedUser.getPersonality() != null) {
                    Personality existingPersonality = me.getPersonality();
                    System.out.println(existingPersonality);
                    if (existingPersonality != null) {
                        personalityRepository.delete(existingPersonality); // or use orphanRemoval
                        me.setPersonality(null);
                        personalityRepository.flush();
                    }

                    Personality copied = Personality.builder()
                            .tag(relatedUser.getPersonality().getTag())
                            .ei(relatedUser.getPersonality().getEi())
                            .sn(relatedUser.getPersonality().getSn())
                            .tf(relatedUser.getPersonality().getTf())
                            .pj(relatedUser.getPersonality().getPj())
                            .user(me)
                            .build();

                    me.setPersonality(copied);
                    personalityRepository.save(copied);
                }

            } else {
                throw UserErrorCode.INVALID_ROLE_ERROR.toException();
            }

        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw UserErrorCode.INTERNAL_SERVER_ERROR.toException();
        }
    }
}
