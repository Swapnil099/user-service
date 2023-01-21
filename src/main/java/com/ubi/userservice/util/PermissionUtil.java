package com.ubi.userservice.util;

import com.ubi.userservice.dto.user.UserPermissionsDto;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.model.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionUtil {

    public boolean hasPermission(String permissionName){
        for(GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if(authority.getAuthority().equals(permissionName)) return true;
        }
        throw new CustomException(
                HttpStatusCode.PERMISSION_DENIED.getCode(),
                HttpStatusCode.PERMISSION_DENIED,
                HttpStatusCode.PERMISSION_DENIED.getMessage(),
                null);
    }

    public String getCurrentUsersToken(){
        UserPermissionsDto userPermissionsDto = (UserPermissionsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userPermissionsDto != null) return userPermissionsDto.getJwtToken();
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(ArrayList<String> permissions) {
        Set<Authority> roles = new HashSet<>();
        for(String permission:permissions){
            roles.add(new Authority(permission));
        }
        return roles;
    }
}
