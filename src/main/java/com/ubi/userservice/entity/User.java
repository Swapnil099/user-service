package com.ubi.userservice.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ubi.userservice.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name="user_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User extends Auditable {

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

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "contactId", referencedColumnName = "id")
	private ContactInfo contactInfo;

	public User(String username, String password,Boolean activeStatus, Role role,ContactInfo contactInfo) {
		this.username = username;
		this.password = password;
		this.isEnabled = activeStatus;
		this.role = role;
		this.contactInfo = contactInfo;
	}
}