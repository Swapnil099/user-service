package com.ubi.userservice.service;

import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;

public interface PermissionService {
    Permission getPermissionFromType(String permissionType);
    Permission addPermission(String permissionType);

    void addRoleToPermission(Role role,Permission permission);
}
