package com.ubi.userservice.mapper;

import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.dto.user.UserCreatedDto;
import com.ubi.userservice.dto.user.UserCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.dto.user.UserPermissionsDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.service.RoleService;
import com.ubi.userservice.util.AutogeneratePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserMapper {

    @Autowired
    RoleService roleService;

    @Autowired
    AutogeneratePassword autogeneratePassword;

    public UserDto toDto(User user){
        String roleType = null;
        if(user.getRole() != null)  roleType = user.getRole().getRoleType();
        return new UserDto(user.getId(),user.getUsername(),user.getIsEnabled(),roleType);
    }

    public User toUser(UserCreationDto userCreationDTO) {
        Role role = roleService.getRoleFromString(userCreationDTO.getRoleType());
        return new User(
                userCreationDTO.getUsername(),
                autogeneratePassword.generate(),
                userCreationDTO.getIsActivate(),
                role
        );
    }

    public UserPermissionsDto userPermissionsDto(User user){
        String roleType = null;
        if(user.getRole() != null)  roleType = user.getRole().getRoleType();
        ArrayList<String> permissions = new ArrayList<>();
        for(Permission permission:user.getRole().getPermissions()){
            permissions.add(permission.getType());
        }

        return new UserPermissionsDto(user.getId(),user.getUsername(),user.getIsEnabled(),roleType,null,permissions);
    }

    public UserCreatedDto toUserCreatedDto(User user){
        UserCreatedDto userCreatedDto = new UserCreatedDto(user.getId(),user.getUsername(),user.getPassword(),user.getIsEnabled(),user.getRole().getRoleName());
        return userCreatedDto;
    }
}
