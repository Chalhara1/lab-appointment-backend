package com.lab.labappointment.controller;

import com.lab.labappointment.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AssignDoctor {

    @Autowired
    private PatientsService patientService;

    @PostMapping("/patients/{patientId}/assign-doctor")
    public ResponseEntity<String> assignDoctorToPatient(@PathVariable int patientId, @RequestParam Long doctorId) {
        try {
            System.out.println(patientId+","+doctorId);
            patientService.savePatientAndAssignDoctor(patientId, doctorId);
            return ResponseEntity.ok("Doctor assigned to the patient successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to assign doctor to the patient.");
        }
    }
}
