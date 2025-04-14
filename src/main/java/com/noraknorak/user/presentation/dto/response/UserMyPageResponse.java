package com.noraknorak.user.presentation.dto.response;

import lombok.Builder;

@Builder
public record UserMyPageResponse(
        String name,
        String relatedUserName,
        String relatedUserBirth
) {}