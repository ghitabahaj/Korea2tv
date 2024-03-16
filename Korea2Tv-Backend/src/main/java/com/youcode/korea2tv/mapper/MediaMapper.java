package com.youcode.korea2tv.mapper;

import com.youcode.korea2tv.dtos.response.media.DetailsMediaResDto;
import com.youcode.korea2tv.dtos.response.media.credit.MediaCreditResDto;
import com.youcode.korea2tv.models.entity.Media;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MediaMapper {
    private final ModelMapper modelMapper;

    public DetailsMediaResDto mapToDto(Media media){
        DetailsMediaResDto dto = modelMapper.map(media, DetailsMediaResDto.class);
        // Configure custom mapping for nested objects

        dto.setCredits(media.getCredits().stream().map(credit -> modelMapper.map(credit, MediaCreditResDto.class))
                .collect(Collectors.toSet()));
        return dto;
    }
}
