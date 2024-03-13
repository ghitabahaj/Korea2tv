package com.youcode.korea2tv.dtos.response.token;

import com.youcode.korea2tv.models.entity.RefreshToken;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResDTO {
    String accessToken;
    String refreshToken;
}
