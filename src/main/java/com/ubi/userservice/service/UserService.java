package com.ubi.userservice.service;


import com.ubi.userservice.dto.pagination.PaginationResponse;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserCreatedDto;
import com.ubi.userservice.dto.user.UserCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.User;

import java.text.ParseException;
import java.util.List;

public interface UserService {

    Response<PaginationResponse<List<UserDto>>> getAllUsersWithPagination(String fieldName, String searchByField, Integer pageNumber, Integer pageSize) throws ParseException;

    Response<UserCreatedDto> createNewUser(UserCreationDto userCreationDTO);

    Response<UserDto> getUserById(String userId);

    Response<UserDto> getPrincipalById(String principalId);

    Response<UserDto> getTeacherById(String teacherId);

    Response<UserDto> getRegionAdminById(String regionAdminId);

    Response<UserDto> getInsitituteAdminById(String instituteAdminId);

     UserDto getUserByUsername(String username);

     User getUserEntityByUsername(String username);

     Response<UserDto> deleteUserById(String userId);

     boolean isUsernamePasswordValid(String username,String password);

     String getRoleByUsername(String username);

     Response<UserDto> changeActiveStatusToTrue(String userId);

     Response<UserDto> changeActiveStatusToFalse(String userId);

     Response<String> changeSelfPassword(String userId, String newPassword);

    Response<String> resetPassword(String userId, String newPassword);
     Response<UserDto> updateUserById(String userId,UserCreationDto userCreationDto);

    Response<Boolean> isUserExistsWithGivenRole(String roleType, String userId);

}
