package com.ubi.userservice.service;

import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.repository.PermissionRepository;
import com.ubi.userservice.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public Response<List<String>> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        if(permissions.isEmpty()){
            throw new CustomException(HttpStatusCode.NO_CONTENT.getCode(),
                    HttpStatusCode.NO_CONTENT,
                    "No Permission Found",
                    new Result<>(null));
        }

        List<String> permissionsString = permissions.stream().filter(Objects::nonNull).map(permission -> permission.getType()).collect(Collectors.toList());
        Response<List<String>> response = new Response<>();
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setResult(new Result<>(permissionsString));
        return response;
    }
}
