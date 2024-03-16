package com.youcode.korea2tv.mapper;

import com.youcode.korea2tv.dtos.response.movie.MovieResDto;
import com.youcode.korea2tv.models.entity.Media;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMapper {
    private final ModelMapper modelMapper;

    public MovieResDto mapToDto(Media media){
        return modelMapper.map(media, MovieResDto.class);
    }
}
