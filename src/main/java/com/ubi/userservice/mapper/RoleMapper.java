package com.ubi.userservice.mapper;

import com.ubi.userservice.dto.role.RoleCreationDto;
import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.dto.role.RoleUserDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public RoleDto toDto(Role role){
        Set<Permission> permissions = role.getPermissions();
        System.out.println(role.getPermissions().toString());
        Set<String> permissionsString = permissions.stream().map(permission -> permission.getType()).collect(Collectors.toSet());
        return new RoleDto(role.getId(),role.getRoleName(),role.getRoleType(),permissionsString);
    }

    public Role toRole(RoleDto roleDTO){
        return new Role(roleDTO.getRoleName(),roleDTO.getRoleType());
    }

    public Role toRole(RoleCreationDto roleCreationDTO){
        return new Role(roleCreationDTO.getRoleName(),roleCreationDTO.getRoleType());
    }

    public RoleUserDto toRoleUserDTO(User user){
        return new RoleUserDto(user.getId(),user.getUsername());
    }

    public Set<RoleUserDto> toRoleUsers(Set<User> users){
        return users.stream().map(this::toRoleUserDTO).collect(Collectors.toSet());
    }

}
