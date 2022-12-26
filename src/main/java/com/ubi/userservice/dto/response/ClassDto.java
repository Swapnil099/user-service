package com.ubi.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class ClassDto {
	private Long classId;
	private String classCode;
	private String className;
}
