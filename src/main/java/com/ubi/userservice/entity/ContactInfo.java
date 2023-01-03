package com.ubi.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ContactInfo ")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    @Email(message = "Please Enter a valid Email Address")
    private String email;

    @Pattern(regexp  ="^\\d{10}$",message = "Please Enter a Valid Contact Number")
    private String contactNumber;

    private String city;
    private String state;
    private String address;
    private Integer age;
    private String gender;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;
    private String bloodGroup;

    @Pattern(regexp  ="^\\d{12}$",message = "Please Enter a Valid Aadhar Card Number")
    private String aadharCardNumber;
    private String nationality;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contactInfo", fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

}
