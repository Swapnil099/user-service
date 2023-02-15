package com.ubi.userservice.mapper;

import com.ubi.userservice.dto.permissionDto.PermissionDto;
import com.ubi.userservice.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    @Autowired
    RoleMapper roleMapper;

    public PermissionDto entityToDto(Permission permission){
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setType(permission.getType());
        permissionDto.setRoles(permission.getRoles().stream().filter(Objects::nonNull).map(role -> roleMapper.toDto(role)).collect(Collectors.toSet()));
        return permissionDto;
    }
}
