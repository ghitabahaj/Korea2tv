package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long idUser);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshToken generateNewToken(RefreshToken token);
}
