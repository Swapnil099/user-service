package com.ubi.userservice.dto.schoolDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

	private int schoolId;

	private int code;

	private String name;

	private String email;

	private long contact;

	private String address;

	private String type;

	private int strength;

	private String shift;

	private Boolean isCollege;
	
	private boolean exemptionFlag;

	private int vvnAccount;

	private int vvnFund;
	
	private Long principalId;

	private int regionId;

	private Set<Long> classId;

	private int educationalInstitutionId;

}
