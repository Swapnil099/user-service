package com.ubi.userservice.controller;

import com.ubi.userservice.dto.response.Response;
import com.ubi.userservice.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Operation(summary = "Get ALl Permissions Availaible", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Response<List<String>>> getAllPermissions(){
        Response<List<String>> response = permissionService.getAllPermissions();
        return ResponseEntity.ok().body(response);
    }

}
