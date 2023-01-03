package com.ubi.userservice.dto.user;

import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    private String username;
    private Boolean isActivate;
    private String roleType;
    private ContactInfoDto contactInfoDto;
}
