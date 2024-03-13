package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.exception.custom.NotFoundUserException;
import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.repositories.UserRepository;
import com.youcode.korea2tv.services.RefreshTokenService;
import com.youcode.korea2tv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public List<AppUser> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public AppUser findByUsernameUser(String username) {
        return userRepository.findByUserNamee(username)
                .orElseThrow(() -> new NotFoundUserException("Not Found Any user by this: " + username));
    }

    @Override
    public AppUser findByUsernameOrEmailUser(String username, String email) {
        return userRepository.findByEmailOrUserNamee(username, email)
                .orElseThrow(() -> new NotFoundUserException("Not Found Any user by this: " + username));
    }

    @Override
    public AppUser register(AppUser req) {
        AppUser user = AppUser.builder()
                .userNamee(req.getUsername())
                .email(req.getEmail())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .roles(req.getRoles())
                .accessionDate(LocalDateTime.now())
                .password(passwordEncoder.encode(req.getPassword()))
                .isEnabled(true)
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public AppUser authenticate(AppUser req) {
        AppUser user = findByUsernameOrEmailUser(req.getUsername(), req.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );
        return user;
    }

    @Override
    public void updateUser(AppUser user) {
    }

    @Override
    public void deleteUser(Long username) {

    }
}
