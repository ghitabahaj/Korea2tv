package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAllGenre();
    Genre findByName(String name);
    Genre createGenre(Genre genre);

}
