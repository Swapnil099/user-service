package com.ubi.userservice.service;

import com.ubi.userservice.dto.jwt.JwtResponse;
import com.ubi.userservice.dto.jwt.LoginCredentialDto;
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
        String roleName = userService.getRoleByUsername(username);
        UserDto userDto = userService.getUserByUsername(username);
        String token = jwtUtil.generateToken(user);

        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(new JwtResponse(userDto.getId(),token,roleName)));
        return response;
    }

    @Override
    public ResponseEntity<Response<UserPermissionsDto>> validateTokenAndGetUser(String jwtToken) {
        String username = null;
        User user = null;
        Response<UserPermissionsDto> response = new Response<>();
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
            System.out.println(jwtToken);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                if(jwtUtil.isTokenExpired(jwtToken)){
                    response.setStatusCode(HttpStatusCode.TOKEN_EXPIRED.getCode());
                    response.setMessage(HttpStatusCode.TOKEN_EXPIRED.getMessage());
                    response.setResult(new Result<>(null));
                    return ResponseEntity.badRequest().body(response);
                }
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
}