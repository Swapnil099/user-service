package com.ubi.userservice.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RoleDto {
    Long id;
    String roleName;
    String roleType;
    Set<String> permissions;
}
