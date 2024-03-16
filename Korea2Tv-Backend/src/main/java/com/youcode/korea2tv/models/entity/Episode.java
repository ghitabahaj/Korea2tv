package  com.youcode.korea2tv.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer episodeNumber;
    private String picture;

    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    @JsonBackReference
    private Season season;

    @OneToMany(mappedBy = "episode")
    @JsonBackReference
    private Set<MediaServerPlay> serverPlays;
}
