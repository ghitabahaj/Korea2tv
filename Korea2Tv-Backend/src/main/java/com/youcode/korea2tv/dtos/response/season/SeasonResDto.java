package com.youcode.korea2tv.dtos.response.season;

import com.youcode.korea2tv.dtos.response.episode.EpisodeResDto;
import com.example.vistreamv2.models.entity.Episode;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeasonResDto {
    private Long idTmdb;
    private String title;
    private Integer seasonNumber;
    private String picture;
    private Double voteAverage;
    private LocalDate airDate;
    private Integer episodeCount;
    private String overview;
    private Set<EpisodeResDto> episodes;
}
