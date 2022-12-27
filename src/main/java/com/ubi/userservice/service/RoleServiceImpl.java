package com.ubi.userservice.service;

import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.role.RoleCreationDto;
import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.dto.role.RoleUserDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.mapper.RoleMapper;
import com.ubi.userservice.repository.PermissionRepository;
import com.ubi.userservice.repository.RoleRepository;
import com.ubi.userservice.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    Result result;

    @Override
    public Role getRoleFromString(String roleType) {
        Role role = roleRepository.getRoleByRoleType(roleType);
        return role;
    }

    @Override
    public Response<RoleDto> createRole(RoleCreationDto roleCreationDTO) {
        Role role = roleMapper.toRole(roleCreationDTO);
        Role currRole = roleRepository.getRoleByRoleType(roleCreationDTO.getRoleType());
        Response<RoleDto> response = new Response<>();
        if(currRole != null) {
            throw new CustomException(
                    HttpStatusCode.RESOURCE_ALREADY_EXISTS.getCode(),
                    HttpStatusCode.RESOURCE_ALREADY_EXISTS,
                    HttpStatusCode.RESOURCE_ALREADY_EXISTS.getMessage(),
                    result);
        }

        for(String permissionType:roleCreationDTO.getPermissions()){
            Permission permission = permissionService.getPermissionFromType(permissionType);
            if(permission != null && !role.getPermissions().contains(permission)) role.getPermissions().add(permission);
        }

        Role savedRole = roleRepository.save(role);

        if(savedRole == null) {
            throw new CustomException(
                    HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(),
                    HttpStatusCode.INTERNAL_SERVER_ERROR,
                    HttpStatusCode.INTERNAL_SERVER_ERROR.getMessage(),
                    result);
        }
        response.setMessage(HttpStatusCode.RESOURCE_CREATED_SUCCESSFULLY.getMessage());
        response.setStatusCode(HttpStatusCode.RESOURCE_CREATED_SUCCESSFULLY.getCode());
        response.setResult(new Result<RoleDto>(roleMapper.toDto(savedRole)));
        return response;
    }

    @Override
    public Response<List<RoleDto>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        List<RoleDto> rolesDtoList = roles.stream().map(roleMapper::toDto).collect(Collectors.toList());
        for(RoleDto roleDto:rolesDtoList) System.out.println(roleDto.toString());
        Response<List<RoleDto>> response = new Response<>();
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setResult(new Result<List<RoleDto>>(rolesDtoList));
        return response;
    }

    @Override
    public Role getRoleByRoleType(String roleType) {
        return roleRepository.getRoleByRoleType(roleType);
    }

    @Override
    public Response<RoleDto> deleteRole(String roleType) {
        Role role = roleRepository.getRoleByRoleType(roleType);
        Response<RoleDto> response = new Response<>();
        if(role == null) {
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }

        for(User user:role.getUsers()) {
            role.getUsers().remove(user);
            user.setRole(null);
            userRepository.save(user);
        }

        for(Permission permission:role.getPermissions()){
            role.getPermissions().remove(permission);
            permission.getRoles().remove(role);
            permissionRepository.save(permission);
        }
        roleRepository.save(role);
        roleRepository.delete(role);
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<RoleDto>(roleMapper.toDto(role)));
        return response;
    }

    @Override
    public Response<Set<RoleUserDto>> getUsersByRoleName(String roleType) {
        Role role = this.getRoleByRoleType(roleType);
        if(role == null){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        Set<User> users = role.getUsers();
        Set<RoleUserDto> roleUsers = roleMapper.toRoleUsers(users);
        Response<Set<RoleUserDto>> response = new Response<>();
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setResult(new Result<Set<RoleUserDto>>(roleUsers));
        return response;
    }

    @Override
    public Response<RoleDto> updateRoleById(String roleId, RoleCreationDto roleCreationDto) {
        Role roleWithGivenRoleType = roleRepository.getRoleByRoleType(roleCreationDto.getRoleType());
        if(roleWithGivenRoleType != null) {
            throw new CustomException(
                    HttpStatusCode.ROLETYPE_NOT_AVAILAIBLE.getCode(),
                    HttpStatusCode.ROLETYPE_NOT_AVAILAIBLE,
                    HttpStatusCode.ROLETYPE_NOT_AVAILAIBLE.getMessage(),
                    result);
        }
        Role role = roleRepository.getReferenceById(Long.parseLong(roleId));
        if(role == null){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }

        role.setRoleName(roleCreationDto.getRoleName());
        role.setRoleType(roleCreationDto.getRoleType());
        roleRepository.save(role);

        Response response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(roleMapper.toDto(role)));
        return response;
    }
}
