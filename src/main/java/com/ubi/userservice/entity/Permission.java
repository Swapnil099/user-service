package com.ubi.userservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name="permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name="permission_id",referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name="role_id",referencedColumnName="id") }
    )
    Set<Role> roles = new HashSet<>();

    public Permission(String permissionType){
        this.type = permissionType;
    }
}