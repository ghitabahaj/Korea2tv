package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.models.entity.Permission;
import com.youcode.korea2tv.repositories.PermissionRepository;
import com.youcode.korea2tv.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    @Override
    public List<Permission> findAllPermission() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }
}
