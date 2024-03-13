package  com.youcode.korea2tv.services;

import  com.youcode.korea2tv.models.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    List<Permission> findAllPermission();
    Permission createPermission(Permission permission);
}
