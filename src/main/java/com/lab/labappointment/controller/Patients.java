package com.lab.labappointment.controller;


import com.lab.labappointment.service.PatientsService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/patients")
public class Patients {

    private final PatientsService patientsService;
    private final JavaMailSender javaMailSender;

    public Patients(PatientsService patientsService, JavaMailSender javaMailSender) {
        this.patientsService = patientsService;
        this.javaMailSender = javaMailSender;
    }


    @PostMapping("/create")
    public ResponseEntity<com.lab.labappointment.entity.Patients> createUser(@RequestBody com.lab.labappointment.entity.Patients patients) {
        com.lab.labappointment.entity.Patients createdPatient = patientsService.createPatients(patients);
        if (createdPatient != null) {
            try {
                sendEmailAfterCreation(createdPatient);
            } catch (MessagingException | jakarta.mail.MessagingException e) {
                e.printStackTrace();
                // Handle exception if email sending fails
                // You can return an error response or log the error
            }
            return ResponseEntity.ok(createdPatient);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendEmailAfterCreation(com.lab.labappointment.entity.Patients patient) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set email details
        helper.setFrom("your-email@example.com");
        helper.setTo(patient.getUsername());
        helper.setSubject("Welcome to MediLab");

        // You can use HTML content for the email body
        String emailContent = "<p>Dear " + patient.getUsername() + ",</p>"
                + "<p>Welcome to MediLab! Your account has been successfully created.</p>"
                + "<p>Thank you for joining us.</p>"
                + "<p>Best regards,</p>"
                + "<p>The MediLab Team</p>";

        helper.setText(emailContent, true); // true indicates HTML content

        // Send email
        javaMailSender.send(message);
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username != null && password != null) {
            Optional<com.lab.labappointment.entity.Patients> authenticatedPatient = patientsService.login(username, password);

            if (authenticatedPatient.isPresent()) {
                return ResponseEntity.ok(authenticatedPatient.get());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    // Get all users
    @GetMapping("/all")
    public List<com.lab.labappointment.entity.Patients> getAllPatients() {
        return PatientsService.getAllPatients();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public com.lab.labappointment.entity.Patients getPatientById(@PathVariable int id) {
        return PatientsService.getPatientById(id).orElse(null);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfPatients() {
        long count = patientsService.countAllPatients();
        return ResponseEntity.ok(count);
    }

}
