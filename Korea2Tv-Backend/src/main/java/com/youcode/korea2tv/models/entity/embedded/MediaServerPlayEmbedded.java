package com.youcode.korea2tv.models.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaServerPlayEmbedded implements Serializable {
    @Column(name = "media_id")
    private Long idMedia;
    @Column(name = "server_id")
    private Long idServer;
//    @Column(name = "season_id")
//    private Long idSeason;
//    @Column(name = "episode_id")
//    private Long idEpisode;
}