package com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m " +
            "WHERE (:typeMedia IS NULL OR LOWER(m.typeMedia) = LOWER(:typeMedia)) " +
            "AND (LOWER(m.originalTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(m.originalLanguage) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(m.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Optional<Page<Media>> findMediaByTypeMediaContaining(
            @Param("searchTerm") String searchTerm,
            @Param("typeMedia") String typeMedia,
            Pageable pageable);
    Optional<Media> findMediaByOriginalTitleAndReleaseDate(String originalTitle, LocalDate releaseDate);

    Optional<Set<Media>> findMediaByCountriesInAndGenresInOrProductionsIn(Set<Country> countries,
                                                                          Set<Genre> genres,
                                                                          Set<Production> productions);


    Optional<Media> findMediaByShortLink(String shortLink);
    Optional<Media> findMediaByIdTmdb(Long idTmdb);

    @Query("SELECT m FROM Media m WHERE m.releaseDate >= :cutoffDate ORDER BY m.popularity DESC")
    List<Media> findTrendingMovies(@Param("cutoffDate") LocalDate cutoffDate);

    List<Media> findByGenresId(Long genreId);
    List<Media> findByOriginalTitle(String name);

    List<Media> findByTitleContainingIgnoreCase(String searchTerm);



}
