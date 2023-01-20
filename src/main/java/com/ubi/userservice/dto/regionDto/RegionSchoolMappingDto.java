package com.ubi.userservice.dto.regionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionSchoolMappingDto {
	int regionId;
	int schoolId;
}