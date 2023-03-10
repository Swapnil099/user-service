package com.ubi.userservice.service;

import com.ubi.userservice.dto.jwt.JwtResponse;
import com.ubi.userservice.dto.jwt.LoginCredentialDto;
import com.ubi.userservice.dto.jwt.ValidateRefreshJwt;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserPermissionsDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public Response<JwtResponse> generateToken(LoginCredentialDto loginCredentialDTO);

    public ResponseEntity<Response<UserPermissionsDto>>  validateTokenAndGetUser(String jwtToken);

    Response<JwtResponse> refreshUserAccessToken(String refreshToken);
}