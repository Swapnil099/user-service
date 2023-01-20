package com.ubi.userservice.dto.educationalInstitutiondto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationalInstitutionDto {

	private int id;

	private String educationalInstitutionCode;

	private String educationalInstitutionName;

	private String educationalInstitutionType;

	private Long strength;

	private String state;

	private String exemptionFlag;

	private Long vvnAccount;

	private Long adminId;

	private Set<Integer> regionId;
}
