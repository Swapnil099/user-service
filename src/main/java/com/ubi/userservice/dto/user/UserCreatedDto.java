package com.ubi.userservice.dto.user;

import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCreatedDto {
    private Long id;
    private String username;
    private String password;
    private Boolean isActivate;
    private String roleName;
    private ContactInfoDto contactInfoDto;

}
