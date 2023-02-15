package com.ubi.userservice.dto.permissionDto;

import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto extends Auditable {
    private Long id;
    private String type;
    Set<RoleDto> roles = new HashSet<>();
}
