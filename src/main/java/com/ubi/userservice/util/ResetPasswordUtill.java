 package com.ubi.userservice.util;

import com.ubi.userservice.dto.classDto.ClassDto;
import com.ubi.userservice.dto.classDto.TeacherDto;
import com.ubi.userservice.dto.educationalInstitutiondto.InstituteDto;
import com.ubi.userservice.dto.regionDto.RegionDetailsDto;
import com.ubi.userservice.dto.regionDto.RegionGet;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.schoolDto.PrincipalDto;
import com.ubi.userservice.dto.schoolDto.SchoolRegionDto;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.externalServices.MasterFeignService;
import com.ubi.userservice.repository.UserRepository;
import lombok.Data;
import org.hibernate.property.access.internal.PropertyAccessStrategyMixedImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

 @Service
@Data
public class ResetPasswordUtill {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private MasterFeignService masterFeignService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PermissionUtil permissionUtil;

    public Response<String> resetTeacherPasswordAsPrincipal(String userId,String newPassword){
        String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
        Long currUserId= permissionUtil.getCurrentUsersid();
        Response<String> response = new Response<>();
        ResponseEntity<Response<SchoolRegionDto>> schoolResponse = masterFeignService.getSchoolByPrincipalId(currJwtToken, currUserId.toString());
        if (schoolResponse.getBody().getResult().getData() != null){
            SchoolRegionDto schoolRegionDto = schoolResponse.getBody().getResult().getData();
            if(schoolRegionDto != null){
                Set<ClassDto> classes = schoolRegionDto.getClassDto();
                for(ClassDto classDto:classes){
                    if(classDto.getTeacherId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
                }
            }
        }

        ResponseEntity<Response<SchoolRegionDto>> collegeResponse = masterFeignService.getCollegeByPrincipalId(currJwtToken, currUserId.toString());
        if(collegeResponse.getBody().getResult().getData() == null){
            response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
            response.setMessage("No School/College is mapped with current user principal");
            response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher"));
            return response;
        }

        SchoolRegionDto schoolRegionDto = collegeResponse.getBody().getResult().getData();

        if(schoolRegionDto != null){
            Set<ClassDto> classes = schoolRegionDto.getClassDto();
            for(ClassDto classDto:classes){
                if(classDto.getTeacherId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
            }
        }

        response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
        response.setMessage("Current User Principal Does Not Have Authority To Reset Password");
        response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher"));
        return response;
    }

     public Response<String> resetTeacherPasswordAsRegionAdmin(String userId,String newPassword){
         String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
         Long currUserId= permissionUtil.getCurrentUsersid();
         Response<String> response = new Response<>();

         ResponseEntity<Response<RegionDetailsDto>> regionResponse = masterFeignService.getRegionByAdminId(currJwtToken, currUserId.toString());
         if (regionResponse.getBody().getResult().getData() == null)

         if (regionResponse.getBody().getResult().getData() == null){
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No Region is mapped with current user regional admin");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
             return response;
         }

         RegionDetailsDto regionDetailsDto = regionResponse.getBody().getResult().getData();
         Integer regionId = regionDetailsDto.getId();
         ResponseEntity<Response<Set<SchoolRegionDto>>> schoolResponse = masterFeignService.getSchoolsByRegionId(currJwtToken, regionId.toString());

         if (schoolResponse.getBody().getResult().getData() != null){
             Set<SchoolRegionDto> schoolRegionDto = schoolResponse.getBody().getResult().getData();

             for(SchoolRegionDto schoolRegionDto1:schoolRegionDto){
                 Set<ClassDto> classes = schoolRegionDto1.getClassDto();
                 for(ClassDto classDto:classes){
                     if(classDto.getTeacherId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
                 }
             }
         }


         ResponseEntity<Response<Set<SchoolRegionDto>>> collegeResponse = masterFeignService.getCollegesByRegionId(currJwtToken, regionId.toString());
         if (regionResponse.getBody().getResult().getData() == null){
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No School/college is mapped with current region admin users region");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
             return response;
         }

         Set<SchoolRegionDto> schoolRegionDto3 = collegeResponse.getBody().getResult().getData();
         for(SchoolRegionDto schoolRegionDto1:schoolRegionDto3){
             Set<ClassDto> classes = schoolRegionDto1.getClassDto();
             for(ClassDto classDto:classes){
                 if(classDto.getTeacherId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
             }
         }

         response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
         response.setMessage("Current User Region Admin Does Not Have Authority To Reset Password");
         response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
         return response;
     }

     public Response<String> resetTeacherPasswordAsInstituteAdmin(String userId,String newPassword) {
         String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
         Long currUserId = permissionUtil.getCurrentUsersid();
         Response<String> response = new Response<>();

         ResponseEntity<Response<InstituteDto>> instituteResponse = masterFeignService.getInstituteByAdminId(currJwtToken, currUserId.toString());
         if (instituteResponse.getBody().getResult().getData() == null) {
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No institute is mapped with current institute admin user");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
             return response;
         }

         InstituteDto instituteDto = instituteResponse.getBody().getResult().getData();
         Integer instituteId = instituteDto.getId();
         ResponseEntity<Response<Set<TeacherDto>>> teacherResponse = masterFeignService.getAllTeachersInsideInstitute(currJwtToken, instituteId.toString());
         Set<TeacherDto> teachers = teacherResponse.getBody().getResult().getData();
         for(TeacherDto teacherDto:teachers){
             if(teacherDto.getUserId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
         }

         response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
         response.setMessage("Current User Institute Admin Does Not Have Authority To Reset Password");
         response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
         return response;
     }

     public Response<String> resetPrincipalPasswordAsRegionAdmin(String userId,String newPassword){
         String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
         Long currUserId= permissionUtil.getCurrentUsersid();
         Response<String> response = new Response<>();


         ResponseEntity<Response<RegionDetailsDto>> regionResponse = masterFeignService.getRegionByAdminId(currJwtToken, currUserId.toString());
             if (regionResponse.getBody().getResult().getData() == null){
                 response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
                 response.setMessage("No Region is mapped with current user regional admin");
                 response.setResult(new Result<>("Unauthorized To Reset Password Of This Principal (out of scope)"));
                 return response;
             }

         RegionDetailsDto regionDetailsDto = regionResponse.getBody().getResult().getData();
         Integer regionId = regionDetailsDto.getId();
         ResponseEntity<Response<Set<SchoolRegionDto>>> schoolResponse = masterFeignService.getSchoolsByRegionId(currJwtToken, regionId.toString());


         if (regionResponse.getBody().getResult().getData() != null){
             Set<SchoolRegionDto> schoolRegionDto = schoolResponse.getBody().getResult().getData();
             if(schoolRegionDto != null && !schoolRegionDto.isEmpty()){
                 for(SchoolRegionDto schoolRegionDto1:schoolRegionDto){
                     if(schoolRegionDto1.getSchoolDto().getPrincipalId().toString().equals(userId)){
                         return this.resetPassword(userId,newPassword);
                     }
                 }
             }
         }

         ResponseEntity<Response<Set<SchoolRegionDto>>> collegeResponse = masterFeignService.getCollegesByRegionId(currJwtToken, regionId.toString());
         if (regionResponse.getBody().getResult().getData() == null){
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No Schools/Colleges are mapped with current user region");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Principal (out of scope)"));
             return response;
         }

         Set<SchoolRegionDto> schoolRegionDto3 = collegeResponse.getBody().getResult().getData();

         if(schoolRegionDto3 != null && !schoolRegionDto3.isEmpty()){
             for(SchoolRegionDto schoolRegionDtoNew:schoolRegionDto3){
                 if(schoolRegionDtoNew.getSchoolDto().getPrincipalId().toString().equals(userId)){
                     return this.resetPassword(userId,newPassword);
                 }
             }
         }

         response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
         response.setMessage("Current User Region Admin Does Not Have Authority To Reset Password");
         response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
         return response;
     }

     public Response<String> resetPrincipalPasswordAsInstituteAdmin(String userId,String newPassword) {
         String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
         Long currUserId = permissionUtil.getCurrentUsersid();
         Response<String> response = new Response<>();

         ResponseEntity<Response<InstituteDto>> instituteResponse = masterFeignService.getInstituteByAdminId(currJwtToken, currUserId.toString());
         if (instituteResponse.getBody().getResult().getData() == null) {
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No institute is mapped with current institute admin user");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
             return response;
         }

         InstituteDto instituteDto = instituteResponse.getBody().getResult().getData();
         Integer instituteId = instituteDto.getId();
         ResponseEntity<Response<Set<SchoolRegionDto>>> teacherResponse = masterFeignService.getAllPrincipalsInsideInstitute(currJwtToken, instituteId.toString());
         Set<SchoolRegionDto> SchoolRegionDtos = teacherResponse.getBody().getResult().getData();

         for(SchoolRegionDto schoolRegionDto:SchoolRegionDtos){
             if(schoolRegionDto.getPrincipalDto() != null){
                if(schoolRegionDto.getPrincipalDto().getUserId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
             }
         }

         response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
         response.setMessage("Current User Institute Admin Does Not Have Authority To Reset Password");
         response.setResult(new Result<>("Unauthorized To Reset Password Of This Principal (out of scope)"));
         return response;
     }

     public Response<String> resetRegionAdminPasswordAsInstituteAdmin(String userId,String newPassword) {
         String currJwtToken = "Bearer " + permissionUtil.getCurrentUsersToken();
         Long currUserId = permissionUtil.getCurrentUsersid();
         Response<String> response = new Response<>();

         ResponseEntity<Response<InstituteDto>> instituteResponse = masterFeignService.getInstituteByAdminId(currJwtToken, currUserId.toString());
         if (instituteResponse.getBody().getResult().getData() == null) {
             response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
             response.setMessage("No institute is mapped with current institute admin user");
             response.setResult(new Result<>("Unauthorized To Reset Password Of This Teacher (out of scope)"));
             return response;
         }

         InstituteDto instituteDto = instituteResponse.getBody().getResult().getData();
         Set<RegionGet> regionDto = instituteDto.getRegionDto();

         for(RegionGet regionGet:regionDto){
             if(regionGet.getAdminId().toString().equals(userId)) return this.resetPassword(userId,newPassword);
         }

         response.setStatusCode(HttpStatusCode.BAD_REQUEST_EXCEPTION.getCode());
         response.setMessage("Current User Institute Admin Does Not Have Authority To Reset Password");
         response.setResult(new Result<>("Unauthorized To Reset Password Of This Regional Admin (out of scope)"));
         return response;
     }


    public Response<String> resetPassword(String userId,String newPassword){
        Optional<User> currUser = userRepository.findById(Long.parseLong(userId));
        User user = currUser.get();
        String encodedNewpassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewpassword);
        userRepository.save(user);
        Response response = new Response<>();
        response.setStatusCode(HttpStatusCode.SUCCESSFUL.getCode());
        response.setMessage(HttpStatusCode.SUCCESSFUL.getMessage());
        response.setResult(new Result<>("Password Reset Successfully"));
        return response;
    }
}
