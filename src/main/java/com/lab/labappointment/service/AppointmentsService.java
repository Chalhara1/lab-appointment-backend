package com.lab.labappointment.service;

import com.lab.labappointment.entity.Appointments;
import com.lab.labappointment.repositories.AppointmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentsService {

    private final AppointmentsRepo appointmentsRepo;



    @Autowired
    public AppointmentsService(AppointmentsRepo appointmentsRepo) {
        this.appointmentsRepo = appointmentsRepo;
    }

    public List<Appointments> getAllAppointments() {
        return appointmentsRepo.findAll();
    }

    public Optional<Appointments> getAppointmentById(int id) {
        return appointmentsRepo.findById(id);
    }

    public Appointments createAppointment(Appointments appointment) {
        String generatedAppointmentNumber = generateAppointmentNumber();

        appointment.setAppointmentNumber(generatedAppointmentNumber);

        return appointmentsRepo.save(appointment);
    }

    public Appointments updateAppointment(int id, Appointments updatedAppointment) {
        if (appointmentsRepo.existsById(id)) {
            updatedAppointment.setAppointmentId(id);
            return appointmentsRepo.save(updatedAppointment);
        }
        return null; // Handle not found scenario
    }

    private String generateAppointmentNumber() {
        // Logic to generate a unique appointment number (you can customize this)
        // For example, concatenate a prefix with a sequential number
        int nextAppointmentNumber = getNextAppointmentNumber();
        String prefix = "APPT";
        return prefix + String.format("%04d", nextAppointmentNumber);
    }

    private int getNextAppointmentNumber() {
        Optional<Appointments> latestAppointment = appointmentsRepo.findTopByOrderByAppointmentNumberDesc();

        return latestAppointment.map(appointment -> {
            String appointmentNumberStr = String.valueOf(appointment.getAppointmentNumber());
            return appointmentNumberStr.length() > 4 ?
                    Integer.parseInt(appointmentNumberStr.substring(4)) + 1 :
                    1;
        }).orElse(1);
    }

    public List<Appointments> getAppointmentsByPatientId(int patientId) {
        return appointmentsRepo.findByPatientPatientId(patientId);
    }

    public void deleteAppointment(int id) {
        appointmentsRepo.deleteById(id);
    }


    public Map<String, Integer> getCountOfAppointmentsByStatus() {
        Map<String, Integer> countByStatus = new HashMap<>();
        countByStatus.put("Confirmed", appointmentsRepo.countAppointmentsByStatus("{\"status\":\"Confirmed\"}"));
        countByStatus.put("Cancelled", appointmentsRepo.countAppointmentsByStatus("{\"status\":\"Cancelled\"}"));
        return countByStatus;
    }



}
