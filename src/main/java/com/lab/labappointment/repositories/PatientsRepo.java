package com.lab.labappointment.repositories;

import com.lab.labappointment.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientsRepo extends JpaRepository<Patients, Integer> {
    Optional<Patients> findByUsername(String username);
}
