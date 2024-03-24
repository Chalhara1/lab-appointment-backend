package com.lab.labappointment.repositories;

import com.lab.labappointment.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentsRepo extends JpaRepository<Appointments, Integer> {

    Optional<Appointments> findTopByOrderByAppointmentNumberDesc();

    List<Appointments> findByPatientPatientId(int patientId);

    int countAppointmentsByStatus(String status);


}
