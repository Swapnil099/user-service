package com.ubi.userservice.util;

import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
