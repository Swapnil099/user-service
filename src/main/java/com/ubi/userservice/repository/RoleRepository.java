package com.ubi.userservice.repository;

import com.ubi.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(
            value = "SELECT * FROM role WHERE role_name = ?1 AND is_deleted = false",
            nativeQuery = true)
    Role getRoleByRoleName(String roleName);

    @Query(
            value = "SELECT * FROM role WHERE role_type = ?1 AND is_deleted = false",
            nativeQuery = true)
    Role getRoleByRoleType(String roleType);
}
