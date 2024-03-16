package  com.youcode.korea2tv.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idTmdb;
    @Column(unique = true)
    private Long creditId;
    private String name;
    private String originalName;
    private String character;
    private String picture;
    private Integer gender;
    private Double popularity;
    private String profilePath;
    private LocalDate birthDate;
}
