package com.ubi.userservice.service;


import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.user.UserCreatedDto;
import com.ubi.userservice.dto.user.UserCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.ContactInfo;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.mapper.ContactInfoMapper;
import com.ubi.userservice.mapper.UserMapper;
import com.ubi.userservice.repository.ContactInfoRepository;
import com.ubi.userservice.repository.UserRepository;
import com.ubi.userservice.util.PermissionUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Result result;

    @Autowired
    PermissionUtil permissionUtil;

    @Autowired
    ContactInfoMapper contactInfoMapper;

    @Override
    public Response<List<UserDto>> getAllUsers() {
        permissionUtil.hasPermission("CREATE-USER");

        List<User> users = userRepository.findAll();
        List<UserDto> allUsers = users.stream().map(userMapper::toDto).collect(Collectors.toList());
        Response<List<UserDto>> response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(allUsers));
        return response;
    }

    @Override
    public Response<UserCreatedDto> createNewUser(UserCreationDto userCreationDTO) {
        Response<UserCreatedDto> response = new Response<>();
        if(this.getUserByUsername(userCreationDTO.getUsername()) != null){
            throw new CustomException(
                    HttpStatusCode.USERNAME_NOT_AVAILAIBLE.getCode(),
                    HttpStatusCode.USERNAME_NOT_AVAILAIBLE,
                    HttpStatusCode.USERNAME_NOT_AVAILAIBLE.getMessage(),
                    new Result<>());
        }

        User user = userMapper.toUser(userCreationDTO);
        ContactInfo contactInfo = contactInfoService.createContactInfo(userCreationDTO.getContactInfoDto());
        user.setContactInfo(contactInfo);

        User userWithoutEncodedPassword = new User(user.getUsername(),user.getPassword(),user.getIsEnabled(),user.getRole(),user.getContactInfo());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userWithoutEncodedPassword.setId(user.getId());
        UserCreatedDto userCreatedDto = userMapper.toUserCreatedDto(userWithoutEncodedPassword);

        response.setStatusCode(HttpStatusCode.RESOURCE_CREATED_SUCCESSFULLY.getCode());
        response.setMessage(HttpStatusCode.RESOURCE_CREATED_SUCCESSFULLY.getMessage());
        response.setResult(new Result<>(userCreatedDto));
        return response;
    }

    @Override
    public Response<UserDto> getUserById(String userId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));
        Response<UserDto> response = new Response<>();
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        User user = currUser.get();
        UserDto userDto = userMapper.toDto(user);
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(userDto));
        return response;
    }

    @Override
    public Response<UserDto> getPrincipalById(String principalId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(principalId));
        Response<UserDto> response = new Response<>();
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.PRINCIPAL_NOT_EXISTS.getCode(),
                    HttpStatusCode.PRINCIPAL_NOT_EXISTS,
                    HttpStatusCode.PRINCIPAL_NOT_EXISTS.getMessage(),
                    result);
        }

        if(!currUser.get().getRole().getRoleType().equals("ROLE_PRINCIPAL")){
            throw new CustomException(HttpStatusCode.PRINCIPAL_NOT_EXISTS.getCode(),
                    HttpStatusCode.PRINCIPAL_NOT_EXISTS,
                    HttpStatusCode.PRINCIPAL_NOT_EXISTS.getMessage(),
                    result);
        }
        User user = currUser.get();
        UserDto userDto = userMapper.toDto(user);
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(userDto));
        return response;
    }

    @Override
    public Response<UserDto> getTeacherById(String teacherId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(teacherId));
        Response<UserDto> response = new Response<>();
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.TEACHER_NOT_EXISTS.getCode(),
                    HttpStatusCode.TEACHER_NOT_EXISTS,
                    HttpStatusCode.TEACHER_NOT_EXISTS.getMessage(),
                    result);
        }

        if(!currUser.get().getRole().getRoleType().equals("ROLE_TEACHER")){
            throw new CustomException(HttpStatusCode.TEACHER_NOT_EXISTS.getCode(),
                    HttpStatusCode.TEACHER_NOT_EXISTS,
                    HttpStatusCode.TEACHER_NOT_EXISTS.getMessage(),
                    result);
        }
        User user = currUser.get();
        UserDto userDto = userMapper.toDto(user);
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(userDto));
        return response;
    }

    public UserDto getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) return null;
        return userMapper.toDto(user);
    }

    public User getUserEntityByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) return null;
        return user;
    }

    @Override
    public Response<UserDto> deleteUserById(String userId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        userRepository.deleteById(Long.parseLong(userId));
        Response<UserDto> response = new Response<>();
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setResult(new Result<>(userMapper.toDto(currUser.get())));
        return response;
    }

    @Override
    public boolean isUsernamePasswordValid(String username,String password){
        User user = userRepository.findByUsername(username);
        return (user != null && passwordEncoder.matches(password,user.getPassword()));
    }

    @Override
    public String getRoleByUsername(String username) {
        UserDto user = this.getUserByUsername(username);
        return user.getRoleType();
    }

    @Override
    public Response<UserDto> changeActiveStatusToTrue(String userId) {
        if(this.getUserById(userId).getResult().getData() == null){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        User user = userRepository.getReferenceById(Long.parseLong(userId));
        user.setIsEnabled(true);
        User updatedUser = userRepository.save(user);

        return new Response<>(new Result<>(userMapper.toDto(updatedUser)));
    }

    @Override
    public Response<UserDto> changeActiveStatusToFalse(String userId) {
        if(this.getUserById(userId).getResult().getData() == null){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        User user = userRepository.getReferenceById(Long.parseLong(userId));
        user.setIsEnabled(false);
        User updatedUser = userRepository.save(user);
        return new Response<>(new Result<>(userMapper.toDto(updatedUser)));
    }

    @Override
    public Response<String> changeSelfPassword(String userId,String newPassword) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!currUser.isPresent()){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        User user = currUser.get();
//        if(!user.getId().equals(currUser.get().getId())){
//            throw new CustomException(HttpStatusCode.UNAUTHORIZED_EXCEPTION.getCode(),
//                    HttpStatusCode.UNAUTHORIZED_EXCEPTION,
//                    HttpStatusCode.UNAUTHORIZED_EXCEPTION.getMessage(),
//                    result);
//        }
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        userRepository.save(user);

        Response response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>("PASSWORD CHANGED SUCCESSFULLY"));
        return response;
    }

    @Override
    public Response<UserDto> updateUserById(String userId, UserCreationDto userCreationDto) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));
        if(!currUser.isPresent()){
            throw new CustomException(HttpStatusCode.RESOURCE_NOT_FOUND.getCode(),
                    HttpStatusCode.RESOURCE_NOT_FOUND,
                    HttpStatusCode.RESOURCE_NOT_FOUND.getMessage(),
                    result);
        }
        User user = currUser.get();
        User userByNewUsername = userRepository.findByUsername(userCreationDto.getUsername());
        if(userByNewUsername != null && !user.getUsername().equals(userCreationDto.getUsername())){
            throw new CustomException(HttpStatusCode.USERNAME_NOT_AVAILAIBLE.getCode(),
                    HttpStatusCode.USERNAME_NOT_AVAILAIBLE,
                    HttpStatusCode.USERNAME_NOT_AVAILAIBLE.getMessage(),
                    result);
        }
        user.setUsername(userCreationDto.getUsername());
        user.setIsEnabled(userCreationDto.getIsActivate());
        Role role = roleService.getRoleByRoleType(userCreationDto.getRoleType());
        if(role == null){
            throw new CustomException(HttpStatusCode.ROLE_NOT_EXISTS.getCode(),
                    HttpStatusCode.ROLE_NOT_EXISTS,
                    HttpStatusCode.ROLE_NOT_EXISTS.getMessage(),
                    result);
        }
        user.setRole(role);

        ContactInfo contactInfo = contactInfoService.updateContactInfo(userCreationDto.getContactInfoDto(),user.getContactInfo().getId());
        user.setContactInfo(contactInfo);

        userRepository.save(user);

        Response response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(userMapper.toDto(user)));
        return response;
    }

    @Override
    public Response<Boolean> isUserExistsWithGivenRole(String roleType, String userId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));
        Boolean isExists = false;
        if(!currUser.isPresent()){
            Response response = new Response<>();
            response.setStatusCode(HttpStatusCode.USER_NOT_EXISTS.getCode());
            response.setMessage(HttpStatusCode.USER_NOT_EXISTS.getMessage());
            response.setResult(new Result<>(isExists));
            return response;
        }
        User user = currUser.get();
        if(user.getRole() == null || !user.getRole().getRoleType().equals(roleType)){
            Response response = new Response<>();
            response.setStatusCode(HttpStatusCode.USER_NOT_EXISTS.getCode());
            response.setMessage(HttpStatusCode.USER_NOT_EXISTS.getMessage());
            response.setResult(new Result<>(isExists));
            return response;
        }

        isExists = true;
        Response response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(isExists));
        return response;
    }

}
