package com.youcode.korea2tv.repositories;

import  com.youcode.korea2tv.models.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserNamee(String username);
    Optional<AppUser> findByEmailOrUserNamee(String email, String username);

    Optional<AppUser> findByEmail(String userEmail);
}
