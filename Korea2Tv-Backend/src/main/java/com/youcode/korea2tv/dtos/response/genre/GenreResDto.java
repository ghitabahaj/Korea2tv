package com.youcode.korea2tv.dtos.response.genre;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreResDto {
    private Long idTmdb;
    private String name;
}
