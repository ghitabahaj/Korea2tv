package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MediaService {
    Page<Media> findAllMediaPageable(String typeMedia, String searchTerm, Pageable pageable);
    Media findMediaByShortLink(String shortLink);
    Media findMediaByIdTmdb(Long idTmdb);

    public List<Media> getTrendingKoreanMovies();

    Boolean checkMediaIsFounded(Media media);
    Media saveMedia(Media media);
    Media updateMedia(Long id, Media media);
    Media deleteMedia(Long id);

}
