package com.ubi.userservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    private String username;
    private Boolean isActivate;
    private String roleType;
}
