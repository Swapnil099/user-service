package com.ubi.userservice.dto.schoolDto;

import com.ubi.userservice.dto.classDto.ClassDto;
import com.ubi.userservice.dto.educationalInstitutiondto.EducationalInstitutionDto;
import com.ubi.userservice.dto.regionDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SchoolRegionDto {

	private SchoolDto schoolDto;
	private PrincipalDto principalDto;
	private RegionDto regionDto;
	private Set<ClassDto> classDto;
	private EducationalInstitutionDto educationalInstitutionDto;
}
