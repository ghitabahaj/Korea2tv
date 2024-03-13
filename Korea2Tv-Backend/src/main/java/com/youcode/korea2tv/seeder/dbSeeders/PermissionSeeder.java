package com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.Permission;
import com.youcode.korea2tv.models.enums.ActionType;
import com.youcode.korea2tv.models.enums.SubjectType;
import com.youcode.korea2tv.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionSeeder {
    private final PermissionRepository permissionRepository;

    private final Set<String> subjects = Set.of(
            "users",
            "media",
            "sliders"
    );
    Set<Permission> permissions = new HashSet<>();

    public void seed(){
        if (permissionRepository.findAll().isEmpty()) {
            for (SubjectType subjectType : SubjectType.values()) {
                for (ActionType actionType : ActionType.values()) {
                    Permission permission = Permission.builder()
                            .action(actionType)
                            .subject(subjectType.name())
                            .build();
                    permissions.add(permission);
                }
            }
            permissionRepository.saveAll(permissions);
        }
    }

    public Set<Permission> permissionsCalled(){
        return permissions;
    }

}
