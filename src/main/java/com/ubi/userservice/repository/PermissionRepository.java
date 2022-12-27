package com.ubi.userservice.repository;

import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission getPermissionByType(String permissionType);
}
