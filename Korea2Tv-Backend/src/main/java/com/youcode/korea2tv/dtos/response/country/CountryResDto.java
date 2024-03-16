package com.youcode.korea2tv.dtos.response.country;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryResDto {
    private String iso;
    private String name;
}
