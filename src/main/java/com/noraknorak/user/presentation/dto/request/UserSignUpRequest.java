package com.noraknorak.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignUpRequest {
    @NotBlank(message = "")
    private String name;

    @NotBlank(message = "")
    private String phone;

    @NotBlank(message = "")
    private String birth;
}
