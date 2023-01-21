package com.ubi.userservice.dto.regionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

	private int id;

	private String code;

	private String name;

	private Set<Integer> eduInstId;
}
