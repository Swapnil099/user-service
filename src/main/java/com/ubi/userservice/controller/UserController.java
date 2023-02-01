package com.ubi.userservice.controller;

import com.ubi.userservice.dto.pagination.PaginationResponse;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.PasswordChangeDto;
import com.ubi.userservice.dto.user.UserCreatedDto;
import com.ubi.userservice.dto.user.UserCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.roleaccessinterface.IsPrincipal;
import com.ubi.userservice.roleaccessinterface.IsSuperAdmin;
import com.ubi.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create New User", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<Response<UserCreatedDto>> createUser(@RequestBody UserCreationDto userCreationDTO){
        Response<UserCreatedDto> userResponse = userService.createNewUser(userCreationDTO);
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Get All Users With Pagination", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Response<PaginationResponse<List<UserDto>>>> getAllUsers(@RequestParam( defaultValue = "*") String fieldName,
                                                               @RequestParam( defaultValue = "*") String fieldQuery,
                                                               @RequestParam(value = "PageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                               @RequestParam(value = "PageSize", defaultValue = "10000000", required = false) Integer pageSize) throws ParseException {
        Response<PaginationResponse<List<UserDto>>> allUserDtoResponse = userService.getAllUsersWithPagination(fieldName,fieldQuery,pageNumber,pageSize);
        return ResponseEntity.ok().body(allUserDtoResponse);
    }

    @Operation(summary = "Get User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{userId}")
    public ResponseEntity<Response<UserDto>> getUserById(@PathVariable String userId) {
        Response<UserDto> response = userService.getUserById(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get Principal Details By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/principal/{principalId}")
    public ResponseEntity<Response<UserDto>> getPrincipalById(@PathVariable String principalId) {
        Response<UserDto> response = userService.getPrincipalById(principalId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get Teacher Details By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Response<UserDto>> getTeacherById(@PathVariable String teacherId) {
        Response<UserDto> response = userService.getTeacherById(teacherId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get Region Admin Details By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/regionadmin/{regionAdminId}")
    public ResponseEntity<Response<UserDto>> getRegionAdminById(@PathVariable String regionAdminId) {
        Response<UserDto> response = userService.getRegionAdminById(regionAdminId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get Educational Admin Details By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/instituteadmin/{instituteAdminId}")
    public ResponseEntity<Response<UserDto>> getInstituteAdminById(@PathVariable String instituteAdminId) {
        Response<UserDto> response = userService.getInsitituteAdminById(instituteAdminId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<UserDto>> deleteUserById(@PathVariable String userId) {
        Response<UserDto> response = userService.deleteUserById(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Change Active Status To True Of User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/activate/{userId}")
    public ResponseEntity<Response<UserDto>> activateUserById(@PathVariable String userId) {
        Response<UserDto> response = userService.changeActiveStatusToTrue(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Change Active Status To False Of User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/deactivate/{userId}")
    public ResponseEntity<Response<UserDto>> deactivateUserById(@PathVariable String userId) {
        Response<UserDto> response = userService.changeActiveStatusToFalse(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Change Password Of User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping ("/password/{userId}")
    public ResponseEntity<Response<String>> changePasswordDto(@PathVariable String userId, @RequestBody PasswordChangeDto passwordChangeDto) {
        Response<String> response = userService.changeSelfPassword(userId, passwordChangeDto.getNewPassword());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping ("/{userId}")
    public ResponseEntity<Response<UserDto>> updateUserById(@PathVariable String userId, @RequestBody UserCreationDto userCreationDto) {
        Response<UserDto> response = userService.updateUserById(userId, userCreationDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Check if user exists", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{roleType}/{userId}")
    public ResponseEntity<Response<Boolean>> checkIfUserExist(@PathVariable String roleType,@PathVariable String userId) {
        Response<Boolean> response = userService.isUserExistsWithGivenRole(roleType, userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Reset Password Of User By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping ("/reset/{userId}")
    public ResponseEntity<Response<String>> resetPassword(@PathVariable String userId, @RequestBody PasswordChangeDto passwordChangeDto) {
        Response<String> response = userService.resetPassword(userId, passwordChangeDto.getNewPassword());
        return ResponseEntity.ok().body(response);
    }
}
