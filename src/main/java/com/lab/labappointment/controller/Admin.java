package com.lab.labappointment.controller;

import com.lab.labappointment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class Admin {

    private final AdminService adminService;

    @Autowired
    public Admin(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public com.lab.labappointment.entity.Admin AdminRegister(@RequestBody com.lab.labappointment.entity.Admin admin) {
        return adminService.register(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> AdminLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username != null && password != null) {
            Optional<com.lab.labappointment.entity.Admin> authenticatedAdmin = adminService.login(username, password);

            if (authenticatedAdmin.isPresent()) {
                return ResponseEntity.ok(authenticatedAdmin.get());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
