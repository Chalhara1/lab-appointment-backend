package com.lab.labappointment.service;

import com.lab.labappointment.entity.Admin;
import com.lab.labappointment.repositories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepo adminRepo;


    @Autowired
    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;

    }

    public Admin register(Admin admin) {

        return adminRepo.save(admin);
    }

    public Optional<Admin> login(String username, String password) {
        Optional<Admin> optionalAdmin = adminRepo.findByUsername(username);

        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();

            // Use the password encoder to check if the entered password matches the stored encoded password
            if (password.equals(admin.getPassword())) {
                return Optional.of(admin);
            }
        }

        return Optional.empty();
    }
}
