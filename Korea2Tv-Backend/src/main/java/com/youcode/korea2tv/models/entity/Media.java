package  com.youcode.korea2tv.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idTmdb;
    @Column(nullable = false)
    private String idImdb;
    private String title;
    private String originalTitle;
    private Integer duration;
    private String posterPath;
    private String backDropPath;
    private String status;
    private LocalDate releaseDate;
    @Column(length = 10000)
    private String overview;
    @Column(unique = true)
    private String shortLink;
    private String originalLanguage;
    private Integer levelView;
    private Boolean adult;
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;
    private String typeMedia;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "media-genres",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "media-countries",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "media-productions",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "production_id")
    )
    private Set<Production> productions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "media-videos",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private Set<Videos> videos;

    @OneToMany(mappedBy = "media")
    private Set<Season> seasons;

    @OneToMany(mappedBy = "media")
    private Set<Watchlist> watchlists;

    @OneToMany(mappedBy = "media")
    private Set<MediaServerPlay> serverPlays;

    @OneToMany(mappedBy = "media")
    @OrderBy(value = "_order")
    private Set<MediaCredit> credits;

    @OneToMany(mappedBy = "media")
    private Set<Slider> sliders;

    @ManyToOne
    private TypeQuality typeQuality;

}
