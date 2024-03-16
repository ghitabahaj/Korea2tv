package com.youcode.korea2tv.models.entity;

import com.youcode.korea2tv.models.entity.embedded.MediaCreditEmbedded;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "media_credits")
public class MediaCredit {
    @EmbeddedId
    private MediaCreditEmbedded id;
    @ManyToOne
    @MapsId("idMedia")
    @JoinColumn(name = "media_id")
    @JsonBackReference
    private Media media;
    @ManyToOne
    @MapsId("idCredit")
    @JoinColumn(name = "credit_id")
    private Credit credit;
    private String _creditIdTmdb;
    private Integer _order;
    @Column(length = 1000)
    private String _character;
    private String _knownForDepartment;
}
