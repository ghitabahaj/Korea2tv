package  com.youcode.korea2tv.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerPlay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;
    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;
}
