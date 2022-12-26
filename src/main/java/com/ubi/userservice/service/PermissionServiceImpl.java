package com.ubi.userservice.service;

import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.repository.PermissionRepository;
import com.ubi.userservice.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Permission getPermissionFromType(String permissionType) {
        return permissionRepository.getPermissionByType(permissionType);
    }

    @Override
    public Permission addPermission(String permissionType) {
        Permission tempPermission = permissionRepository.getPermissionByType(permissionType);
        if(tempPermission!=null) return tempPermission;
        Permission newPermission = new Permission(permissionType);
        Permission createdPermission = permissionRepository.save(newPermission);
        return createdPermission;
    }

    @Override
    public void addRoleToPermission(Role role,Permission permission) {
        if(permission.getRoles().contains(role)) return;
        permission.getRoles().add(role);
        role.getPermissions().add(permission);
        roleRepository.save(role);
        permissionRepository.save(permission);
    }
}
