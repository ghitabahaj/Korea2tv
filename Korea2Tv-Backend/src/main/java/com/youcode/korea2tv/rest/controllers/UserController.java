package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.config.JwtService;
import com.youcode.korea2tv.dtos.requests.token.TokenReqDto;
import com.youcode.korea2tv.dtos.requests.user.AuthenticateReqDto;
import com.youcode.korea2tv.dtos.requests.user.RegisterReqDto;
import com.youcode.korea2tv.dtos.response.token.TokenResDTO;
import com.youcode.korea2tv.mapper.TokenMapper;
import com.youcode.korea2tv.mapper.UserMapper;
import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.RefreshToken;
import com.youcode.korea2tv.services.RefreshTokenService;
import com.youcode.korea2tv.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1.0.0/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final TokenMapper tokenMapper;
    private final UserMapper userMapper;



    @GetMapping
    public ResponseEntity<List<AppUser>> findAllUser(){
        List<AppUser> userList = userService.findAllUser();
        return ResponseEntity.ok(userList);
    }
    @GetMapping("/{username}")
    public ResponseEntity<AppUser> findUserByUsername(@Valid @PathVariable("username") String username){
        AppUser user = userService.findByUsernameUser(username);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<TokenResDTO> register(@Valid @RequestBody RegisterReqDto registerReqDto){
        AppUser user = userService.register(userMapper.mapToEntityRegister(registerReqDto));
        //generate new Token
        String jwtAccessToken = jwtService.generateAccessToken(user);
        RefreshToken jwtRefreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new ResponseEntity<>(tokenMapper.toDto(jwtAccessToken, jwtRefreshToken.getToken()), HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<TokenResDTO> authenticate(@Valid @RequestBody AuthenticateReqDto authenticateReqDto){
        AppUser user = userService.authenticate(userMapper.mapToEntityAuthenticated(authenticateReqDto));
        //generate new Token
        String jwtAccessToken = jwtService.generateAccessToken(user);
        RefreshToken jwtRefreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new ResponseEntity<>(tokenMapper.toDto(jwtAccessToken, jwtRefreshToken.getToken()) , HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResDTO> refreshToken(@RequestBody TokenReqDto request) {
        RefreshToken accessToken = refreshTokenService.generateNewToken(TokenMapper.toEntity(request));
        return ResponseEntity.ok(tokenMapper.toDto(accessToken.getToken(), request.refreshToken()));
    }
//    @GetMapping
//    public ResponseEntity<ToRDto>
}
