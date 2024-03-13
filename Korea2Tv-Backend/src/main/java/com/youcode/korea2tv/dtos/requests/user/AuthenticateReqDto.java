package  com.youcode.korea2tv.dtos.requests.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticateReqDto {
        @NotNull(message = "Email cannot be null")
        @Email(message = "Email")
        String email;
        @NotNull(message = "Password cannot be null")
//        @Min(value = 8, message = "Sorry")
        String password;
}




