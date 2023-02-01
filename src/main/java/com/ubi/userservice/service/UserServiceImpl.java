package com.ubi.userservice.service;


import com.ubi.userservice.dto.pagination.PaginationResponse;
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
import com.ubi.userservice.util.ResetPasswordUtill;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private ResetPasswordUtill resetPasswordUtill;

    @Autowired
    Result result;

    @Autowired
    PermissionUtil permissionUtil;


    @Autowired
    ContactInfoMapper contactInfoMapper;

    @Override
    public Response<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> allUsers = users.stream().map(userMapper::toDto).collect(Collectors.toList());
        Response<List<UserDto>> response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>(allUsers));
        return response;
    }

    @Override
    public Response<PaginationResponse<List<UserDto>>> getAllUsersWithPagination(String fieldName, String fieldQuery, Integer pageNumber, Integer pageSize) throws ParseException {
        Page<User> users = null;
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        String strDateRegEx ="^((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        if(!fieldName.equals("*") && !fieldQuery.equals("*"))
        {
            if(fieldQuery.matches(strDateRegEx)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date localDate = formatter.parse(fieldQuery);
                if(fieldName.equalsIgnoreCase("dateOfBirth")) users = userRepository.getAllUserByDOB(localDate,paging);
            } else {
                if(fieldName.equalsIgnoreCase("firstName")) {
                    users = userRepository.getAllUserByFirstName(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("lastName")) {
                    users = userRepository.getAllUserByLastName(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("fullName")) {
                    users = userRepository.getAllUserByFullName(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("username")) {
                    users = userRepository.getAllUserByUsername(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("middleName")) {
                    users = userRepository.getAllUserByMiddleName(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("gender")) {
                    users = userRepository.getAllUserByGender(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("email")) {
                    users = userRepository.getAllUserByEmail(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("contactNumber")) {
                    users = userRepository.getAllUserByContactNumber(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("nationality")) {
                    users = userRepository.getAllUserByNationality(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("aadhar")) {
                    users = userRepository.getAllUserByAadhar(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("address")) {
                    users = userRepository.getAllUserByAddress(fieldQuery, paging);
                }
                if(fieldName.equalsIgnoreCase("isEnable")) {
                    users = userRepository.getAllUserByIsEnabled(Boolean.parseBoolean(fieldQuery),paging);
                }
                if(fieldName.equalsIgnoreCase("age")) {
                    users = userRepository.getAllUserByAge(Integer.parseInt(fieldQuery),paging);
                }
                if(fieldName.equalsIgnoreCase("BloodGroup")) {
                    users = userRepository.getAllUserByBloodGroup(fieldQuery,paging);
                }
                if(fieldName.equalsIgnoreCase("ContactNumber")) {
                    users = userRepository.getAllUserByBloodGroup(fieldQuery,paging);
                }
                if(fieldName.equalsIgnoreCase("roleType")) {
                    System.out.println("here ");
                    users = userRepository.getAllUserByRole(fieldQuery,paging);
                }
            }
        } else users = userRepository.getAllUser(paging);

        Response<PaginationResponse<List<UserDto>>> response = new Response<>();
        if (users == null || users.isEmpty()) {
            response.setStatusCode(HttpStatusCode.NO_CONTENT.getCode());
            response.setMessage("No User Found");
            response.setResult( new Result(null) );
            return response;
        }

        List<UserDto> userList = users.toList().stream().filter(Objects::nonNull).map(user -> userMapper.toDto(user)).collect(Collectors.toList());

        PaginationResponse<List<UserDto>> paginationResponse = new PaginationResponse<>(userList,users.getTotalPages(),users.getTotalElements());

        Result<PaginationResponse<List<UserDto>>> result = new Result<>();
        result.setData(paginationResponse);

        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage("Users retrived");
        response.setResult(result);

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

    @Override
    public Response<UserDto> getRegionAdminById(String regionAdminId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(regionAdminId));
        Response<UserDto> response = new Response<>();
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.REGION_ADMIN_NOT_EXISTS.getCode(),
                    HttpStatusCode.REGION_ADMIN_NOT_EXISTS,
                    HttpStatusCode.REGION_ADMIN_NOT_EXISTS.getMessage(),
                    result);
        }

        if(!currUser.get().getRole().getRoleType().equals("ROLE_REGIONAL_OFFICE_ADMIN")){
            throw new CustomException(HttpStatusCode.REGION_ADMIN_NOT_EXISTS.getCode(),
                    HttpStatusCode.REGION_ADMIN_NOT_EXISTS,
                    HttpStatusCode.REGION_ADMIN_NOT_EXISTS.getMessage(),
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
    public Response<UserDto> getInsitituteAdminById(String instituteAdminId) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(instituteAdminId));
        Response<UserDto> response = new Response<>();
        if(!currUser.isPresent()) {
            throw new CustomException(HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS.getCode(),
                    HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS,
                    HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS.getMessage(),
                    result);
        }

        if(!currUser.get().getRole().getRoleType().equals("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN")){
            throw new CustomException(HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS.getCode(),
                    HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS,
                    HttpStatusCode.INSTITUTE_ADMIN_NOT_EXISTS.getMessage(),
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
    public Response<String> resetPassword(String userId, String newPassword) {
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));

        if(!currUser.isPresent()){
            throw new CustomException(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode(),
                    HttpStatusCode.BAD_REQUEST_EXCEPTION,
                    "User With Given Id Not Found",
                    new Result<>(null));
        }
        User user = currUser.get();
        String currentUserRole = permissionUtil.getCurrentUsersRoleType();
        String passwordResetUserRole = user.getRole().getRoleType();

        if(currentUserRole.equals("ROLE_PRINCIPAL") && passwordResetUserRole.equals("ROLE_TEACHER")){
            return resetPasswordUtill.resetTeacherPasswordAsPrincipal(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_REGIONAL_OFFICE_ADMIN") && passwordResetUserRole.equals("ROLE_TEACHER")){
            return resetPasswordUtill.resetTeacherPasswordAsRegionAdmin(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN") && passwordResetUserRole.equals("ROLE_TEACHER")){
            return resetPasswordUtill.resetTeacherPasswordAsInstituteAdmin(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_REGIONAL_OFFICE_ADMIN") && passwordResetUserRole.equals("ROLE_PRINCIPAL")){
            return resetPasswordUtill.resetPrincipalPasswordAsRegionAdmin(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN") && passwordResetUserRole.equals("ROLE_PRINCIPAL")){
            return resetPasswordUtill.resetPrincipalPasswordAsInstituteAdmin(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN") && passwordResetUserRole.equals("ROLE_REGIONAL_OFFICE_ADMIN")){
            return resetPasswordUtill.resetRegionAdminPasswordAsInstituteAdmin(userId,newPassword);
        }

        if(currentUserRole.equals("ROLE_SUPER_ADMIN")){
            return resetPasswordUtill.resetPassword(userId,newPassword);
        }

        Response<String> response = new Response<>();
        response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
        response.setMessage(HttpStatusCode.BAD_REQUEST_EXCEPTION.getMessage());
        response.setResult(new Result<>("Unauthorize To Reset Password"));
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
