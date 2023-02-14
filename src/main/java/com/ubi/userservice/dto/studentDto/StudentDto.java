package com.ubi.userservice.dto.studentDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentDto {
	@Id
	private Long studentId;
	private String studentFirstName;
	private String studentLastName;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

//	private boolean studentStatus;
	private String category;
	private String minority;
	private String fatherName;
	private String fatherOccupation;
	private String motherName;
	private String motherOccupation;
	private String gender;
	private String bloodGroup;
	private Long aadhaarNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	private Date joiningDate;

//	private String status;
	private Boolean verifiedByTeacher;
	private Boolean verifiedByPrincipal;
	private Boolean isActivate;
	private String currentStatus;
	private Long rollNo;
	private Boolean isPhysicallyHandicapped;
	private Long classId;
}


