package com.ubi.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column
	private Boolean isEnabled;

	@ManyToOne
	@JoinColumn(name="roleId",referencedColumnName = "id",nullable=true)
	private Role role;

	public User(String username, String password,Boolean activeStatus, Role role) {
		this.username = username;
		this.password = password;
		this.isEnabled = activeStatus;
		this.role = role;
	}
}