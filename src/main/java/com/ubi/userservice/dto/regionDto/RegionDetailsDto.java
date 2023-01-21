package com.ubi.userservice.dto.regionDto;

import com.ubi.userservice.dto.educationalInstitutiondto.EducationalInstitutionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDetailsDto {
	private int id;
	private String code;
	private String name;
	private RegionAdminDto regionAdminDto;
	Set<EducationalInstitutionDto> eduInstiDto = new HashSet<>();
}
