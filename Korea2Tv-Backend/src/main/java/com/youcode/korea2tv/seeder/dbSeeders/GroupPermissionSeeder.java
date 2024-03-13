package com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.PermissionGroup;
import com.youcode.korea2tv.repositories.PermissionGroupRepository;
import com.youcode.korea2tv.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GroupPermissionSeeder {
    private final PermissionGroupRepository permissionGroupRepository;
    private final PermissionService permissionService;

    private final Set<String> nameGroup = Set.of(
            "stars",
            "simple"
    );

    public void seed(){
        if (permissionGroupRepository.findAll().isEmpty()) {
            nameGroup.forEach(name ->
                    permissionGroupRepository.save(
                            PermissionGroup.builder()
                            .deadline(LocalDateTime.now().minusDays(6))
                            .name(name.toUpperCase())
                            .permissions(new HashSet<>(permissionService.findAllPermission()))
                            .build())
            );
        }
    }
}
