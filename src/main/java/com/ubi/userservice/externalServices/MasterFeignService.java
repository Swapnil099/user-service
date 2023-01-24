package com.ubi.userservice.externalServices;


import com.ubi.userservice.dto.classDto.ClassDto;
import com.ubi.userservice.dto.classDto.TeacherDto;
import com.ubi.userservice.dto.educationalInstitutiondto.InstituteDto;
import com.ubi.userservice.dto.regionDto.RegionDetailsDto;
import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.dto.schoolDto.PrincipalDto;
import com.ubi.userservice.dto.schoolDto.SchoolRegionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@FeignClient(name="MASTER-SERVICE")
@Component
public interface MasterFeignService {

    @GetMapping("/class/teacher/{teacherId}")
    public ResponseEntity<Response<ClassDto>> getClassByTeacherId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken, @PathVariable String teacherId);

    @GetMapping("/educationalInstitution/admin/{adminId}")
    public ResponseEntity<Response<InstituteDto>> getInstituteByAdminId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String adminId);

    @GetMapping("/region/admin/{adminId}")
    public ResponseEntity<Response<RegionDetailsDto>> getRegionByAdminId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String adminId);

    @GetMapping("/school/principal/{principalId}")
    public ResponseEntity<Response<SchoolRegionDto>> getSchoolByPrincipalId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String principalId);

    @GetMapping("/school/collegeprincipal/{principalId}")
    public ResponseEntity<Response<SchoolRegionDto>> getCollegeByPrincipalId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String principalId);

    @GetMapping("/region/school/{regionId}")
    public ResponseEntity<Response<Set<SchoolRegionDto>>> getSchoolsByRegionId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String regionId);

    @GetMapping("/region/college/{regionId}")
    public ResponseEntity<Response<Set<SchoolRegionDto>>> getCollegesByRegionId(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken,@PathVariable String regionId);

    @GetMapping("/educationalInstitution/teachers/{instituteId}")
    ResponseEntity<Response<Set<TeacherDto>>> getAllTeachersInsideInstitute(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken, @PathVariable String instituteId);

    @GetMapping("/educationalInstitution/principals/{instituteId}")
    ResponseEntity<Response<Set<SchoolRegionDto>>> getAllPrincipalsInsideInstitute(@RequestHeader(value = "Authorization", required = true) String authorizationHeaderToken, @PathVariable String instituteId);
}