package com.youcode.korea2tv.dtos.response.video;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoResDto {
    private String idTmdb;
    private String name;
    private String _key;
    private String _site;
    private Integer _size;
    private String _type;
    private String _official;
    private LocalDateTime _publishedAt;
}
