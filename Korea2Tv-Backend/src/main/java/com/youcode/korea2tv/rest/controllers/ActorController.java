package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.dtos.requests.actor.ActorReqDto;
import com.youcode.korea2tv.dtos.response.actor.ActorResDto;
import com.youcode.korea2tv.mapper.ActorMapper;
import com.youcode.korea2tv.mapper.UserMapper;
import com.youcode.korea2tv.models.entity.Actor;
import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.services.ActorService;
import com.example.vistreamv2.utils.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0.0/actor")
public class ActorController {
    private final ActorService actorService;
    private final ActorMapper actorMapper;

    @GetMapping("all")
    public ResponseEntity<Response<List<ActorResDto>>> getAllActor(){
        List<Actor> actorList = actorService.findAllActor();
        return new ResponseEntity<>(Response.<List<ActorResDto>>builder()
                .result(actorList.stream()
                        .map(actorMapper::mapToDto)
                        .toList())
                .message("Success")
                .build(),
                HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Response<Object>> getAllActorPageable(@RequestParam Optional<String> searchTerm,
                                                        @RequestParam Optional<Integer> numPage,
                                                        @RequestParam Optional<Integer> numSize){
        Map<String, Page<Actor>> stringListMap = new HashMap<>();
        Page<Actor> actorPage = actorService.finAllActorPageable(
                searchTerm.orElse(""),
                numPage.orElse(0),
                numSize.orElse(10));
        stringListMap.put("page", actorPage);
        return ResponseEntity.ok(Response.builder()
                .message("Success")
                .result(stringListMap)
                .build());
    }
    @GetMapping("/{nameActor}")
    public ResponseEntity<Response<ActorResDto>> getByName(@Valid @PathVariable("nameActor") String name){
        Actor actor = actorService.findByName(name);
        return new ResponseEntity<>(Response.<ActorResDto>builder()
                .result(actorMapper.mapToDto(actor))
                .message("Success")
                .build(),
                HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Response<ActorResDto>> createActor(@Valid
                                                                 @RequestBody ActorReqDto reqDto){
        Actor actors = actorService.createActor(actorMapper.mapToEntity(reqDto));
        return new ResponseEntity<>(Response.<ActorResDto>builder()
                .result(actorMapper.mapToDto(actors))
                .message("Created Actor Successfully")
                .build(),
                HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response<ActorResDto>> updateActor(@Valid @PathVariable("id") Long id,
                                                                 @RequestBody ActorReqDto reqDto){
        Actor actor = actorService.updateActor(id, actorMapper.mapToEntity(reqDto));
        return new ResponseEntity<>(Response.<ActorResDto>builder()
                .result(actorMapper.mapToDto(actor))
                .message("Updated Actor Successfully")
                .build(),
                HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<ActorResDto>> deleteActor(@Valid @PathVariable("id") Long id){
        actorService.deleteActor(id);
        return new ResponseEntity<>(Response.<ActorResDto>builder()
                .message("Deleted Actor Successfully")
                .build(),
                HttpStatus.NO_CONTENT);
    }
}
