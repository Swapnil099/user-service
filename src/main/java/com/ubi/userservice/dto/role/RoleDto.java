package com.ubi.userservice.dto.role;

import com.ubi.userservice.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RoleDto extends Auditable {
    Long id;
    String roleName;
    String roleType;
    Set<String> permissions;
}
