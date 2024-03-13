package  com.youcode.korea2tv.dtos.requests.actor;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Builder
public class ActorReqDto {
        @NotNull(message = "name must not be null")
        String fullName;
//        @NotNull(message = "picture must not be null")
        String picture;
        @NotNull(message = "date birthday must not be null")
        LocalDate birthDate;
}
