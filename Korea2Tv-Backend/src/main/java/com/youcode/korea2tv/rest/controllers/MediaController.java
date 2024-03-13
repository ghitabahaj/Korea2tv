package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.services.MediaService;
import com.example.vistreamv2.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1.0.0/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping
    public ResponseEntity<Response<Object>> getAllMedia(@RequestParam Optional<String> searchTerm,
                                                @RequestParam Optional<Integer> numPage,
                                                @RequestParam Optional<Integer> numSize){
        Map<String, Page<Media>> stringListMap = new HashMap<>();
        Page<Media> mediaPage = mediaService.findAllMediaPageable(
                searchTerm.orElse(""),
                numPage.orElse(0),
                numSize.orElse(10));
        stringListMap.put("page", mediaPage);
        return ResponseEntity.ok(Response.builder()
                .message("Success")
                .result(stringListMap)
                .build());
    }
}
