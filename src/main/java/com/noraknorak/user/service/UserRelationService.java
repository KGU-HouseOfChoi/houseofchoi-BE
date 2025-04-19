package com.noraknorak.user.service;

import com.noraknorak.core.config.exception.DomainException;
import com.noraknorak.core.infrastructure.security.CustomUserDetails;
import com.noraknorak.user.domain.Role;
import com.noraknorak.user.domain.User;
import com.noraknorak.user.domain.repository.UserRepository;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationService {

    private final UserRepository userRepository;

    // 부모/자식 연동 코드 검증
    public User validateUserCode(String code) {
        return userRepository.findByUserCode(code.toLowerCase())
                .orElseThrow(UserErrorCode.NOT_EQUAL_USER_CODE::toException);
    }

    // 부모/자식 관계 설정
    @Transactional
    public void verifyRelatedUser(CustomUserDetails customUserDetails, String role, Long relatedUserId){
        Long userId = customUserDetails.getId();

        if (userId.equals(relatedUserId)) {
            throw UserErrorCode.SELF_RELATION_NOT_ALLOWED.toException();
        }

        try{
            if(Role.SENIOR.getName().equals(role)){
                userRepository.updateUserRoleAndRelatedUser(userId, Role.GUARDIAN, relatedUserId);
            }else if(Role.GUARDIAN.getName().equals(role)){
                userRepository.updateUserRoleAndRelatedUser(userId, Role.SENIOR, relatedUserId);
            }else{
                throw UserErrorCode.INVALID_ROLE_ERROR.toException();
            }
        } catch (DomainException e) {
            throw e;
        }
        catch (Exception e){
            throw UserErrorCode.INTERNAL_SERVER_ERROR.toException();
        }
    }
}
