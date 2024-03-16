package com.youcode.korea2tv.dtos.response.episode;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeResDto {
    private String title;
    private Integer episodeNumber;
    private String picture;
}
