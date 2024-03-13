package com.youcode.korea2tv.seeder.dbSeeders;


import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.Permission;
import com.youcode.korea2tv.models.entity.Role;
import com.youcode.korea2tv.models.enums.ActionType;
import com.youcode.korea2tv.models.enums.RoleType;
import com.youcode.korea2tv.repositories.PermissionRepository;
import com.youcode.korea2tv.repositories.RoleRepository;
import com.youcode.korea2tv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserSeeder {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void seed(){
        if(userRepository.findAll().isEmpty()){
            AppUser user = AppUser.builder()
                    .firstName("saad")
                    .lastName("momo")
                    .userNamee("saadmomo")
                    .email("saad@super.com")
                    .accessionDate(LocalDateTime.now())
                    .password(passwordEncoder.encode("12345678999"))
                    .isEnabled(true)
                    .build();
            roleRepository.findRoleByName(RoleType.SUPER_ADMIN)
                    .ifPresent(role -> user.setRoles(Set.of(role)));
            userRepository.save(user);
        }
    }
}
