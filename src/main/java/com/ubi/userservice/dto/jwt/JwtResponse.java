package com.ubi.userservice.dto.jwt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    Long id;
    String token;
    String roleType;
}
