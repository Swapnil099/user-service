package com.ubi.userservice.dto.user;

import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreatedDto {
    private Long id;
    private String username;
    private String password;
    private Boolean isActivate;
    private String roleName;
}
