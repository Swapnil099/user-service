package com.ubi.userservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Boolean isActivate;
    private String roleType;
}
