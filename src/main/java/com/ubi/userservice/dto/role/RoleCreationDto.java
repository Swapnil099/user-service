package com.ubi.userservice.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RoleCreationDto {
    String roleName;
    String roleType;
    ArrayList<String> permissions;
}
