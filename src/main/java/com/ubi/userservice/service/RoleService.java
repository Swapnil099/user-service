package com.ubi.userservice.service;

import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.role.RoleCreationDto;
import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.dto.role.RoleUserDto;
import com.ubi.userservice.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role getRoleFromString(String roleTye);
    Response<RoleDto> createRole(RoleCreationDto roleCreationDTO);
    Response<List<RoleDto>> getAllRoles();

    Role getRoleByRoleType(String roleType);

    Response<RoleDto> deleteRole(String roleType);

    Response<Set<RoleUserDto>> getUsersByRoleName(String roleType);

    Response<RoleDto> updateRoleById(String roleId,RoleCreationDto roleCreationDto);


}
