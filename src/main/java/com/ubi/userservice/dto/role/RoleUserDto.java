package com.ubi.userservice.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RoleUserDto {
    Long id;
    String username;
    String firstName;
    String lastName;
}
