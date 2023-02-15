package com.ubi.userservice.service;

import com.ubi.userservice.dto.permissionDto.PermissionDto;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.mapper.PermissionMapper;
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

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Permission getPermissionFromType(String permissionType) {
        return permissionRepository.getPermissionByType(permissionType);
    }

    @Override
    public PermissionDto addPermission(String permissionType) {
        Permission tempPermission = permissionRepository.getPermissionByType(permissionType);
        if(tempPermission!=null) return permissionMapper.entityToDto(tempPermission);
        Permission newPermission = new Permission(permissionType);
        Permission createdPermission = permissionRepository.save(newPermission);
        PermissionDto permissionDto = permissionMapper.entityToDto(createdPermission);
        return permissionDto;
    }

    @Override
    public PermissionDto addRoleToPermission(Role role,Permission permission) {
        if(permission.getRoles().contains(role)) return null;
        permission.getRoles().add(role);
        role.getPermissions().add(permission);
        roleRepository.save(role);
        Permission permission1 = permissionRepository.save(permission);
        PermissionDto permissionDto = permissionMapper.entityToDto(permission1);
        return permissionDto;
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
