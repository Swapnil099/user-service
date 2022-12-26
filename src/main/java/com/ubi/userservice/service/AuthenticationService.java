package com.ubi.userservice.service;

import com.ubi.userservice.dto.jwt.JwtResponse;
import com.ubi.userservice.dto.jwt.LoginCredentialDto;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserPermissionsDto;

public interface AuthenticationService {
    public Response<JwtResponse> generateToken(LoginCredentialDto loginCredentialDTO);

    public Response<UserPermissionsDto> validateTokenAndGetUser(String jwtToken);

}