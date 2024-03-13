package com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.Permission;
import  com.youcode.korea2tv.models.entity.Role;
import  com.youcode.korea2tv.models.enums.ActionType;
import  com.youcode.korea2tv.models.enums.RoleType;
import  com.youcode.korea2tv.repositories.RoleRepository;
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
public class RoleSeeder {
    private final RoleRepository roleRepository;
    private final PermissionSeeder permissionSeeder;

    public void seed(){
        Set<Permission> permissionSet = permissionSeeder.permissionsCalled();
        if (roleRepository.findAll().isEmpty()){
            for (RoleType roleType : RoleType.values()) {
                Role role = Role.builder().build();
                role.setName(roleType);
                role.setPermissions(permissionSet.stream().filter(p -> p.getAction().equals(ActionType.ALL)).collect(Collectors.toSet()));
                if(roleType.equals(RoleType.GUEST)){
                    role.setPermissions(permissionSet.stream().filter(p -> p.getAction().equals(ActionType.READ)).collect(Collectors.toSet()));
                }
                roleRepository.save(role);
            }
        }

    }
}
