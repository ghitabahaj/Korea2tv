package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.config.JwtService;
import com.youcode.korea2tv.exception.custom.TokenException;
import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.RefreshToken;
import com.youcode.korea2tv.repositories.RefreshTokenRepository;
import com.youcode.korea2tv.repositories.UserRepository;
import com.youcode.korea2tv.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final Long expirationRefreshToken = 60480000L;
    @Override
    public RefreshToken createRefreshToken(Long idUser) {
        AppUser user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .user(user)
                .revoked(false)
                .expiryDate(Instant.now().plusMillis(expirationRefreshToken))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token == null){
            throw new TokenException("Token is null");
        }
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new TokenException("Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public RefreshToken generateNewToken(RefreshToken refreshToken) {
        AppUser user = refreshTokenRepository.findByToken(refreshToken.getToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new TokenException("Refresh token does not exist"));
        String token = jwtService.generateAccessToken(user);
        return RefreshToken.builder()
                .token(token)
                .build();
    }
}
