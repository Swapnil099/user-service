package com.ubi.userservice.service;


import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserCreatedDto;
import com.ubi.userservice.dto.user.UserCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.User;

import java.util.List;

public interface UserService {

    Response<List<UserDto>> getAllUsers();

    Response<UserCreatedDto> createNewUser(UserCreationDto userCreationDTO);

    Response<UserDto> getUserById(String userId);

     UserDto getUserByUsername(String username);

     User getUserEntityByUsername(String username);

     Response<UserDto> deleteUserById(String userId);

     boolean isUsernamePasswordValid(String username,String password);

     String getRoleByUsername(String username);

     Response<UserDto> changeActiveStatusToTrue(String userId);

     Response<UserDto> changeActiveStatusToFalse(String userId);

     Response<String> changeSelfPassword(String userId, String newPassword);

     Response<UserDto> updateUserById(String userId,UserCreationDto userCreationDto);
}
