package com.ubi.userservice.repository;

import com.ubi.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRoleName(String roleName);

    Role getRoleByRoleType(String roleType);
}
