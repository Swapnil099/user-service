package com.ubi.userservice.roleaccessinterface;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_EDUCATIONAL_INSTITUTE_HQ_ADMIN','ROLE_REGIONAL_OFFICE_ADMIN','ROLE_PRINCIPAL')")
public @interface IsPrincipal {
}
