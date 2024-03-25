package  com.youcode.korea2tv.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idTmdb;
    private String title;
    private Integer seasonNumber;
    private String picture;
    private Double voteAverage;
    private LocalDate airDate;
    private Integer episodeCount;
    private String overview;

    @ManyToOne
    @JoinColumn(name = "media_id")
    @JsonBackReference
    private Media media;

    @OneToMany(mappedBy = "season")
    @JsonBackReference
    private Set<MediaServerPlay> serverPlays;

    @OneToMany(mappedBy = "season")
    @JsonManagedReference

    private Set<Episode> episodes;
}