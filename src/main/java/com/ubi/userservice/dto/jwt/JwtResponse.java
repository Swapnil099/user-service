package com.ubi.userservice.dto.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    String roleName;
    String accessToken;
    String refreshToken;
}
