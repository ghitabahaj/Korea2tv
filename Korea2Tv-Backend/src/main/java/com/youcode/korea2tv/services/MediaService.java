package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.Media;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MediaService {
    Page<Media> findAllMediaPageable(String searchTerm, Integer numPage, Integer size);
    Boolean checkMediaIsFounded(Media media);
    Media saveMedia(Media media);
    Media updateMedia(Long id, Media media);
    Media deleteMedia(Long id);

}
