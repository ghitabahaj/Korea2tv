package com.youcode.korea2tv.models.entity;

import com.youcode.korea2tv.models.entity.embedded.MediaServerPlayEmbedded;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "media_servers")
public class MediaServerPlay {
    @EmbeddedId
    private MediaServerPlayEmbedded id;
    @ManyToOne
    @MapsId("idMedia")
    @JoinColumn(name = "media_id")
    @JsonBackReference
    private Media media;
    @ManyToOne
    @MapsId("idServer")
    @JoinColumn(name = "server_id")
    @JsonBackReference
    private ServerPlay serverPlay;
    @ManyToOne
//    @MapsId("idSeason")
    @JoinColumn(name = "season_id")
    @JsonBackReference
    private Season season;
    @ManyToOne
//    @MapsId("idEpisode")
    @JoinColumn(name = "episode_id")
    @JsonBackReference
    private Episode episode;
    @Column(length = 5000)
    private String mediaPath;
}
