package com.lab.labappointment.controller;

import com.lab.labappointment.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/appointments")
public class Appointments {

    private final AppointmentsService appointmentsService;

    @Autowired
    public Appointments(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    @GetMapping("/all")
    public List<com.lab.labappointment.entity.Appointments> getAllAppointments() {
        return appointmentsService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.lab.labappointment.entity.Appointments> getAppointmentById(@PathVariable int id) {
        return appointmentsService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<com.lab.labappointment.entity.Appointments> createAppointment(@RequestBody com.lab.labappointment.entity.Appointments appointment) {
        com.lab.labappointment.entity.Appointments createdAppointment = appointmentsService.createAppointment(appointment);
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.lab.labappointment.entity.Appointments> updateAppointmentStatus(
            @PathVariable int id,
            @RequestBody String newStatus
    ) {
        com.lab.labappointment.entity.Appointments appointment = appointmentsService.getAppointmentById(id).orElse(null);

        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        // Only update the status
        appointment.setStatus(newStatus);

        com.lab.labappointment.entity.Appointments result = appointmentsService.updateAppointment(id, appointment);

        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentsService.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/byPatient/{patientId}")
    public ResponseEntity<List<com.lab.labappointment.entity.Appointments>> getAppointmentsByPatientId(@PathVariable int patientId) {
        List<com.lab.labappointment.entity.Appointments> appointments = appointmentsService.getAppointmentsByPatientId(patientId);

        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/countByStatus")
    public ResponseEntity<Map<String, Integer>> getCountOfAppointmentsByStatus() {
        Map<String, Integer> countByStatus = appointmentsService.getCountOfAppointmentsByStatus();
        return ResponseEntity.ok(countByStatus);
    }


}
