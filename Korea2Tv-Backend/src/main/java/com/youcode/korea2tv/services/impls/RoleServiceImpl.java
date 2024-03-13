package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.models.entity.Role;
import com.youcode.korea2tv.repositories.RoleRepository;
import com.youcode.korea2tv.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {

    }
    @Override
    public void updateRole(Role role) {

    }
}
