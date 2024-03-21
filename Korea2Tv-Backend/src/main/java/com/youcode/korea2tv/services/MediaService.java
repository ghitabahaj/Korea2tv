package com.youcode.korea2tv.services;

import com.youcode.korea2tv.models.entity.Country;
import com.youcode.korea2tv.models.entity.Genre;
import  com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.models.entity.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MediaService {
    Page<Media> findAllMediaPageable(String typeMedia, String searchTerm, Pageable pageable);
    Media findMediaByShortLink(String shortLink);
    Media findMediaByIdTmdb(Long idTmdb);

    public List<Media> getTrendingKoreanMovies();

    List<Media> getRelatedMoviesByGenre(Long genreId);

    Boolean checkMediaIsFounded(Media media);

    Set<Media> recommended(Set<Country> countries,
                           Set<Genre> genres,
                           Set<Production> productions);

    Media saveMedia(Media media);
    Media updateMedia(Long id, Media media);
    Media deleteMedia(Long id);

    List<Media> searchMediaByName(String name);

    List<Media> getMediaByActor(String actorId);
}
