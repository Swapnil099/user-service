package com.ubi.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.dto.role.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Boolean isActivate;
    private Long updatedByUserId;
    private String roleType;
    private RoleDto roleDto;
    private ContactInfoDto contactInfoDto;
}
