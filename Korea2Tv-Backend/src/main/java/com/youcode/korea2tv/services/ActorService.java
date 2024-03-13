package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.Actor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorService {
    Page<Actor> finAllActorPageable(String searchTerm, Integer numPage, Integer size);
    List<Actor> findAllActor();
    Actor findByName(String name);
    Actor createActor(Actor actor);
    Actor updateActor(Long id, Actor actor);
    void deleteActor(Long id);
}
