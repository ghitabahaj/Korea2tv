package com.youcode.korea2tv.dtos.requests.user;

import com.youcode.korea2tv.models.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class RegisterReqDto {
        @NotNull(message = "firstName cannot be null")
        String firstName;
        @NotNull(message = "lastName cannot be null")
        String lastName;
        @NotNull(message = "userName cannot be null")
        String userName;
        @NotNull(message = "email cannot be null")
        @Email(message = "Email")
        String email;
        @NotNull(message = "password cannot be null")
        String password;
}
