package com.ubi.userservice.util;

import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.dto.role.RoleCreationDto;
import com.ubi.userservice.dto.user.UserDto;
import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.Role;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.repository.UserRepository;
import com.ubi.userservice.service.PermissionService;
import com.ubi.userservice.service.RoleService;
import com.ubi.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataLoaderUtil implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionService permissionService;

    private void loadRolesInDatabase(){
        RoleCreationDto roleSuperAdmin = new RoleCreationDto("Super Admin","ROLE_SUPER_ADMIN",new ArrayList<String>());
        RoleCreationDto roleEducationHQAdmin = new RoleCreationDto("Educational Institute HQ ADMIN","ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN",new ArrayList<String>());
        RoleCreationDto roleRegionalAdmin = new RoleCreationDto("Regional Admin","ROLE_REGIONAL_OFFICE_ADMIN",new ArrayList<String>());
        RoleCreationDto rolePrincipal = new RoleCreationDto("Principal","ROLE_PRINCIPAL",new ArrayList<String>());
        RoleCreationDto roleTeacher = new RoleCreationDto("Teacher","ROLE_TEACHER",new ArrayList<String>());
        roleService.createRole(roleSuperAdmin);
        roleService.createRole(roleEducationHQAdmin);
        roleService.createRole(roleRegionalAdmin);
        roleService.createRole(rolePrincipal);
        roleService.createRole(roleTeacher);
    }

    private void loadUserInDatabase() {
        // adding super admin
        Role roleSuperAdmin = roleService.getRoleByRoleType("ROLE_SUPER_ADMIN");
        Role roleEducationHQAdmin = roleService.getRoleByRoleType("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN");
        Role roleRegionalAdmin = roleService.getRoleByRoleType("ROLE_REGIONAL_OFFICE_ADMIN");
        Role rolePrincipal = roleService.getRoleByRoleType("ROLE_PRINCIPAL");
        Role roleTeacher = roleService.getRoleByRoleType("ROLE_TEACHER");

        User superAdminUser = new User("superadmin",passwordEncoder.encode("superadmin"),true,roleSuperAdmin,null);
        User hqAdminUser = new User("hqadmin",passwordEncoder.encode("hqadmin"),true,roleEducationHQAdmin,null);
        User rgAdminUser = new User("rgadmin",passwordEncoder.encode("rgadmin"),true,roleRegionalAdmin,null);
        User principalUser = new User("principal",passwordEncoder.encode("principal"),true,rolePrincipal,null);
        User teacherUser = new User("teacher",passwordEncoder.encode("teacher"),true,roleTeacher,null);


        userRepository.save(superAdminUser);
        userRepository.save(hqAdminUser);
        userRepository.save(rgAdminUser);
        userRepository.save(principalUser);
        userRepository.save(teacherUser);
    }

    private void assignPermissionToRoles() {
        Role roleSuperAdmin = roleService.getRoleByRoleType("ROLE_SUPER_ADMIN");
        Role roleEducationHQAdmin = roleService.getRoleByRoleType("ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN");
        Role roleRegionalAdmin = roleService.getRoleByRoleType("ROLE_REGIONAL_OFFICE_ADMIN");
        Role rolePrincipal = roleService.getRoleByRoleType("ROLE_PRINCIPAL");
        Role roleTeacher = roleService.getRoleByRoleType("ROLE_TEACHER");

        String[] operations = {"CREATE","READ","UPDATE","DELETE"};
        String[] features = {"TRANSFER-CERTIFICATE","FEES","QUERY","PAYMENT","EDUCATION-INSTITUTE","REGION","SCHOOL","CLASS","STUDENT","USER","ROLE"};

        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                permissionService.addPermission(permissionType);
            }
        }

        // adding permissions for super admin role type
        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                Permission currPermission = permissionService.getPermissionFromType(permissionType);
                permissionService.addRoleToPermission(roleSuperAdmin,currPermission);
            }
        }

        // adding permissions for EducationHQAdmin role type
        for(String operation:operations){
            for(String feature:features){
                if(!feature.equals("EDUCATION-INSTITUTE")){
                    String permissionType = operation+"-"+feature;
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleEducationHQAdmin,currPermission);
                }
            }
        }

        // adding permissions for EducationHQAdmin role type
        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                if(!feature.equals("EDUCATION-INSTITUTE") && !feature.equals("USER") && !feature.equals("ROLE")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleEducationHQAdmin,currPermission);
                }

                if(permissionType.equals("READ-EDUCATION-INSTITUTE")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleEducationHQAdmin,currPermission);
                }

                if(permissionType.equals("UPDATE-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleEducationHQAdmin,currPermission);
                }

                if(permissionType.equals("READ-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleEducationHQAdmin,currPermission);
                }
            }
        }

        // adding permissions for Region Admin role type
        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                if(!feature.equals("EDUCATION-INSTITUTE") && !feature.equals("USER") && !feature.equals("ROLE") && operation.equals("READ")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleRegionalAdmin,currPermission);
                }

                if(permissionType.equals("UPDATE-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleRegionalAdmin,currPermission);
                }

                if(permissionType.equals("READ-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleRegionalAdmin,currPermission);
                }
            }
        }

        // adding permissions for Principal role type
        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                if(!feature.equals("EDUCATION-INSTITUTE") && !feature.equals("USER") && !feature.equals("ROLE") && !operation.equals("REGION")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(rolePrincipal,currPermission);
                }

                if(permissionType.equals("READ-PRINCIPAL")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(rolePrincipal,currPermission);
                }

                if(permissionType.equals("UPDATE-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(rolePrincipal,currPermission);
                }

                if(permissionType.equals("READ-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(rolePrincipal,currPermission);
                }
            }
        }

        // adding permissions for Teacher role type
        for(String operation:operations){
            for(String feature:features){
                String permissionType = operation+"-"+feature;
                if(!feature.equals("EDUCATION-INSTITUTE") && !feature.equals("USER") && !feature.equals("ROLE") && !operation.equals("REGION") && !operation.equals("PRINCIPAL")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleTeacher,currPermission);
                }

                if(permissionType.equals("READ-TEACHER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleTeacher,currPermission);
                }

                if(permissionType.equals("UPDATE-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleTeacher,currPermission);
                }

                if(permissionType.equals("READ-USER")){
                    Permission currPermission = permissionService.getPermissionFromType(permissionType);
                    permissionService.addRoleToPermission(roleTeacher,currPermission);
                }
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserDto userDTO = userService.getUserByUsername("superadmin");
        if(userDTO != null) return;
        loadRolesInDatabase();
        assignPermissionToRoles();
        loadUserInDatabase();
    }

}
