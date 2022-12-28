package com.ubi.userservice.controller;

import com.ubi.userservice.dto.jwt.JwtResponse;
import com.ubi.userservice.dto.jwt.LoginCredentialDto;
import com.ubi.userservice.dto.jwt.ValidateJwt;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserPermissionsDto;
import com.ubi.userservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthenticateController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<Response<JwtResponse>> getToken(@RequestBody LoginCredentialDto loginCredentialDTO) {
        Response<JwtResponse> jwtTokenResponse = authenticationService.generateToken(loginCredentialDTO);
        return ResponseEntity.ok().body(jwtTokenResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<Response<UserPermissionsDto>> validateTokenAndGetUser(@RequestBody ValidateJwt validateJwt) {
        ResponseEntity<Response<UserPermissionsDto>> userPermissionDto = authenticationService.validateTokenAndGetUser(validateJwt.getJwtToken());
        return userPermissionDto;
    }

}
