package com.ubi.userservice.service;

import com.ubi.userservice.dto.jwt.JwtResponse;
import com.ubi.userservice.dto.jwt.LoginCredentialDto;
import com.ubi.userservice.dto.jwt.ValidateRefreshJwt;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.dto.user.UserPermissionsDto;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.mapper.UserMapper;
import com.ubi.userservice.util.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response<JwtResponse> generateToken(LoginCredentialDto loginCredentialDTO) {
        String username = loginCredentialDTO.getUsername();
        String password = loginCredentialDTO.getPassword();
        Response<JwtResponse> response = new Response<>();
        Result<JwtResponse> result = new Result<>();
        if(!userService.isUsernamePasswordValid(username,password)){
            throw new CustomException(HttpStatusCode.INVALID_CREDENTIALS.getCode(),
                    HttpStatusCode.INVALID_CREDENTIALS,
                    HttpStatusCode.INVALID_CREDENTIALS.getMessage(),
                    result);
        }

        User user = userService.getUserEntityByUsername(username);
        UserDto userDto = userService.getUserByUsername(username);
        String token = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        String roleName = user.getRole().getRoleName();
        JwtResponse jwtResponse = JwtResponse.builder().id(userDto.getId()).username(user.getUsername())
                .firstName(user.getContactInfo().getFirstName()).lastName(user.getContactInfo().getLastName())
                .accessToken(token).refreshToken(refreshToken).roleName(roleName).build();

        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(jwtResponse));
        return response;
    }

    @Override
    public ResponseEntity<Response<UserPermissionsDto>> validateTokenAndGetUser(String jwtToken) {
        String username = null;
        User user = null;

        if (jwtToken != null && jwtToken.startsWith("Bearer ")){
            String newToken = jwtToken.substring(7);
            try {
                jwtUtil.isTokenExpired(newToken);
            }
            catch (Exception e){
                throw new CustomException(
                        HttpStatusCode.TOKEN_EXPIRED.getCode(),
                        HttpStatusCode.TOKEN_EXPIRED,
                        HttpStatusCode.TOKEN_EXPIRED.getMessage(),
                        new Result<>());
            }
        }


        Response<UserPermissionsDto> response = new Response<>();
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
            System.out.println(jwtToken);
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (Exception e) {
                response.setStatusCode(HttpStatusCode.UNAUTHORIZED_EXCEPTION.getCode());
                response.setMessage(HttpStatusCode.UNAUTHORIZED_EXCEPTION.getMessage());
                response.setResult(new Result<>(null));
                return ResponseEntity.badRequest().body(response);
            }
        }
        else{
            response.setStatusCode(HttpStatusCode.TOKEN_FORMAT_INVALID.getCode());
            response.setMessage(HttpStatusCode.TOKEN_FORMAT_INVALID.getMessage());
            response.setResult(new Result<>(null));
            return ResponseEntity.badRequest().body(response);
        }

        user = userService.getUserEntityByUsername(username);

        UserPermissionsDto userPermissionsDto = userMapper.userPermissionsDto(user);
        userPermissionsDto.setJwtToken(jwtToken);
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(userPermissionsDto));
        return ResponseEntity.ok().body(response);
    }

    @Override
    public Response<JwtResponse> refreshUserAccessToken(String refreshToken) {
        String username = null;
        Response<JwtResponse> response = new Response<>();
        System.out.println(refreshToken + " refresh token ");
        try {
            jwtUtil.isTokenExpired(refreshToken);
        }
        catch (Exception e){
            throw new CustomException(
                    HttpStatusCode.REFRESH_TOKEN_EXPIRED.getCode(),
                    HttpStatusCode.REFRESH_TOKEN_EXPIRED,
                    HttpStatusCode.REFRESH_TOKEN_EXPIRED.getMessage(),
                    new Result<>());
        }

        try {
            username = jwtUtil.extractUsername(refreshToken);
        } catch (Exception e) {
            throw new CustomException(
                    HttpStatusCode.UNAUTHORIZED_EXCEPTION.getCode(),
                    HttpStatusCode.UNAUTHORIZED_EXCEPTION,
                    HttpStatusCode.UNAUTHORIZED_EXCEPTION.getMessage(),
                    new Result<>());
        }

        String roleName = userService.getRoleByUsername(username);
        UserDto userDto = userService.getUserByUsername(username);
        User user = userService.getUserEntityByUsername(username);
        String accessToken  = jwtUtil.generateToken(user);
        JwtResponse jwtResponse = JwtResponse.builder().id(userDto.getId()).username(user.getUsername())
                        .firstName(user.getContactInfo().getFirstName()).lastName(user.getContactInfo().getLastName())
                        .accessToken(accessToken).refreshToken(refreshToken).roleName(roleName).build();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(jwtResponse));
        System.out.println("reresh token request successfull  -------");
        return response;
    }
}