package com.ubi.userservice.dto.classDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
	
	private Long classId;
	private String classCode;
	private String className;
	private int schoolId;
	private Long teacherId;
}
