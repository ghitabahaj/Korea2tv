package com.youcode.korea2tv.mapper;

import  com.youcode.korea2tv.dtos.requests.token.TokenReqDto;
import  com.youcode.korea2tv.dtos.response.token.TokenResDTO;
import  com.youcode.korea2tv.models.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenMapper {
    private final ModelMapper modelMapper;
    public static RefreshToken toEntity(TokenReqDto tokenReqDTO){
        return RefreshToken.builder()
                .token(tokenReqDTO.refreshToken())
                .build();
    }

    public TokenResDTO toDto(String access, String refresh){
        return modelMapper.map(TokenResDTO.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build(), TokenResDTO.class);
    }


}
