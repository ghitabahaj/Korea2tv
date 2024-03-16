package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.models.entity.Genre;
import com.youcode.korea2tv.repositories.GenreRepository;
import com.youcode.korea2tv.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    @Override
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findGenreByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Not found this genre: "+name));
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
