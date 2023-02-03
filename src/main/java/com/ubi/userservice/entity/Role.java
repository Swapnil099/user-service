package com.ubi.userservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ubi.userservice.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name="role")
public class Role extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String roleName;

	@Column(nullable = false)
	private String roleType;

	@OneToMany(mappedBy = "role",cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Set<User> users;

	@ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	private Set<Permission> permissions = new HashSet<>();

    public Role(String roleName, String roleType) {
    	this.roleName = roleName;
		this.roleType = roleType;
	}
}
