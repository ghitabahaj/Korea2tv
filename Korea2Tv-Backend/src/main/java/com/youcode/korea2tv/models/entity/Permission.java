package  com.youcode.korea2tv.models.entity;

import com.youcode.korea2tv.models.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"subject", "action"})
})
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    @Enumerated(EnumType.STRING)
    private ActionType action;
}
