package com.ubi.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.dto.role.RoleDto;
import com.ubi.userservice.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor @AllArgsConstructor @Builder
public class UserDto extends Auditable {
    private Long id;
    private String username;
    private Boolean isActivate;
    private String roleType;
    private RoleDto roleDto;
    private ContactInfoDto contactInfoDto;
}
