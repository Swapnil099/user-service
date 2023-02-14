package com.ubi.userservice.dto.classDto;

import com.ubi.userservice.dto.schoolDto.SchoolDto;
import com.ubi.userservice.dto.studentDto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentDto //extends Auditable
{
	private ClassDto classDto;
	private SchoolDto schoolDto;
	private TeacherDto teacherDto;
	private Set<StudentDto> studentDto;
}
