package com.ubi.userservice.service;

import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;

import java.util.ArrayList;
import java.util.List;

public interface PermissionService {
    Permission getPermissionFromType(String permissionType);
    Permission addPermission(String permissionType);

    void addRoleToPermission(Role role,Permission permission);

    Response<List<String>>  getAllPermissions();
}
