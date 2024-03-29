package  com.youcode.korea2tv.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youcode.korea2tv.models.enums.TypeQualityMedia;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeQuality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeQualityMedia name;
    @OneToMany(mappedBy = "typeQuality")
    @JsonManagedReference
    private List<Media> mediaList;
}
