package com.youcode.korea2tv.repositories;

import  com.youcode.korea2tv.models.entity.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m " +
            "WHERE LOWER(m.director) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(m.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(m.originalTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Optional<Page<Media>> findMediaByContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
    Optional<Media> findMediaByOrOriginalTitleAndReleaseDate(String originalTitle, LocalDate releaseDate);
    Optional<Media> findMediaByIdTmdb(Long idTmdb);
}
