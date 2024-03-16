package com.youcode.korea2tv.dtos.response.production;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionResDto {
    private Long idTmdb;
    private String name;
    private String logoPath;
    private String originCountry;
}
