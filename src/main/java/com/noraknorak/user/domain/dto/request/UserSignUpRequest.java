package com.noraknorak.user.domain.dto.request;

import com.noraknorak.user.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignUpRequest {

    @NotBlank(message = "")
    private String name;

    @NotBlank(message = "")
    private String phone;

    @NotBlank(message = "")
    private String birth;

    @NotBlank(message = "")
    private String gender;

    @NotNull(message = "")
    private Role role;

    @NotBlank(message = "")
    private String personality_tag;
}
