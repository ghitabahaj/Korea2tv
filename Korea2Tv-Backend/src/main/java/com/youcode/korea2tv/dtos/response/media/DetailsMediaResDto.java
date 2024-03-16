package com.youcode.korea2tv.dtos.response.media;

import com.youcode.korea2tv.dtos.response.country.CountryResDto;
import com.youcode.korea2tv.dtos.response.genre.GenreResDto;
import com.youcode.korea2tv.dtos.response.media.credit.MediaCreditResDto;
import com.youcode.korea2tv.dtos.response.media.serverPlay.MediaServerPlayResDto;
import com.youcode.korea2tv.dtos.response.production.ProductionResDto;
import com.youcode.korea2tv.dtos.response.video.VideoResDto;
import com.youcode.korea2tv.models.entity.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsMediaResDto {
    private Long idTmdb;
    private String idImdb;
    private String title;
    private String originalTitle;
    private Integer duration;
    private String posterPath;
    private String backDropPath;
    private String status;
    private LocalDate releaseDate;
    private String overview;
    private String shortLink;
    private String originalLanguage;
    private Boolean adult;
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;
    private String typeMedia;
    private TypeQuality typeQuality;
    private Set<GenreResDto> genres;
    private Set<CountryResDto> countries;
    private Set<ProductionResDto> productions;
    private Set<VideoResDto> videos;
    private Set<MediaCreditResDto> credits;
    private Set<Season> seasons;
    private Set<Watchlist> watchlists;
    private Set<MediaServerPlayResDto> serverPlays;
    private Set<Slider> sliders;
}
