package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.dtos.response.media.DetailsMediaResDto;
import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.services.MediaService;

import com.youcode.korea2tv.dtos.response.movie.MovieResDto;
import com.youcode.korea2tv.mapper.MediaMapper;
import com.youcode.korea2tv.mapper.MovieMapper;
import com.example.vistreamv2.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1.0.0/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    private final MovieMapper movieMapper;
    private final MediaMapper mediaMapper;

    @GetMapping
    public ResponseEntity<Response<Object>> getAllMedia(@RequestParam Optional<String> searchTerm,
                                                        @RequestParam Optional<String> typeMedia,
                                                        @RequestParam Optional<Integer> numPage,
                                                        @RequestParam Optional<Integer> numSize){
        Map<String, Page<MovieResDto>> stringListMap = new HashMap<>();
        // initialize pageable default
        Pageable pageable = PageRequest.of(
                numPage.orElse(0),
                numSize.orElse(10)
        );
        // initialize request default
        Page<Media> mediaPage = mediaService.findAllMediaPageable(
                typeMedia.orElse(""),
                searchTerm.orElse(""),
                pageable
        );
        // Mapped data
        List<MovieResDto> movieResDtoList = mediaPage.stream()
                .map(movieMapper::mapToDto)
                .toList();

        // Insert data in PageImpl class
        Page<MovieResDto> movieResDtoPage = new PageImpl<>(movieResDtoList, pageable, mediaPage.getTotalElements());
        // Set data pageble
        stringListMap.put("page", movieResDtoPage);
        return ResponseEntity.ok(Response.builder()
                .message("Success")
                .result(stringListMap)
                .build());
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<Response<Object>> findMediaByShortLink(@PathVariable String shortLink){
        Media media = mediaService.findMediaByShortLink(shortLink);
        return ResponseEntity.ok(Response.builder()
                .message("Success")
                .result(mediaMapper.mapToDto(media))
                .build());
    }

    @GetMapping("/trending-korean-movies")
    public ResponseEntity<Response<Object>> getTrendingKoreanMovies() {
        List<Media> trendingKoreanMovies = mediaService.getTrendingKoreanMovies();
        List<MovieResDto> trendingKoreanMovieDtos = trendingKoreanMovies.stream()
                .map(movieMapper::mapToDto) // Map Media objects to MovieResDto objects
                .toList();
        Map<String, List<MovieResDto>> result = new HashMap<>();
        result.put("trendingKoreanMovies", trendingKoreanMovieDtos);
        return ResponseEntity.ok(Response.builder()
                .message("Success")
                .result(result)
                .build());
    }


}
