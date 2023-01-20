package com.ubi.userservice.dto.regionDto;

import com.ubi.userservice.dto.schoolDto.SchoolDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionSchoolDto {
	private RegionDto regionDto;
	private Set<SchoolDto> schoolDto;
}

