package com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> findAllUser();
    AppUser findByUsernameUser(String username);
    AppUser findByUsernameOrEmailUser(String username, String email);
    AppUser register(AppUser user);
    AppUser authenticate(AppUser user);
    void updateUser(AppUser user);
    void deleteUser(Long username);

}
