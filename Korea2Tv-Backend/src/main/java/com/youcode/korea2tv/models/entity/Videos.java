package com.youcode.korea2tv.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Videos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String idTmdb;
    private String name;
    private String _key;
    private String _site;
    private Integer _size;
    private String _type;
    private String _official;
    private LocalDateTime _publishedAt;
}
