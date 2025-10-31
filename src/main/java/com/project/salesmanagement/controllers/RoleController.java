package com.project.salesmanagement.controllers;

import com.project.salesmanagement.models.Role;
import com.project.salesmanagement.responses.ResponseObject;
import com.project.salesmanagement.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {
    private final RoleService roleService;
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Get roles successfully")
                        .status(HttpStatus.OK)
                        .data(roles)
                .build());
    }
}
