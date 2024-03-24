package com.lab.labappointment.service;


import com.lab.labappointment.entity.Doctor;
import com.lab.labappointment.entity.Patients;
import com.lab.labappointment.repositories.DoctorRepository;
import com.lab.labappointment.repositories.PatientsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PatientsService {

    private static PatientsRepo patientsRepo = null;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;



    public PatientsService(PatientsRepo patientsRepo, PasswordEncoder passwordEncoder) {
        this.patientsRepo = patientsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Patients createPatients(Patients patients) {
        patients.setPassword(passwordEncoder.encode(patients.getPassword()));
        return patientsRepo.save(patients);
    }

    public static List<Patients> getAllPatients() {
        return patientsRepo.findAll();
    }

    public static Optional<Patients> getPatientById(int id) {
        return patientsRepo.findById(id);
    }

    public Optional<Patients> login(String username, String password) {
        // Find a patient by username
        Optional<Patients> optionalPatient = patientsRepo.findByUsername(username);

        // Check if the patient with the given username exists
        if (optionalPatient.isPresent()) {
            Patients patient = optionalPatient.get();

            // Manually hash the entered password for comparison
            if (passwordEncoder.matches(password, patient.getPassword())) {
                return Optional.of(patient);
            }
        }

        return Optional.empty();
    }

    public long countAllPatients() {
        return patientsRepo.count();
    }

    @Transactional
    public void savePatientAndAssignDoctor(int patientId, Long doctorId) {
        Optional<Patients> optionalPatient = patientsRepo.findById(patientId);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalPatient.isPresent() && optionalDoctor.isPresent()) {
            Patients patient = optionalPatient.get();
            Doctor doctor = optionalDoctor.get();

            // Check if the doctor is already assigned to the patient
            if (patient.getDoctors().contains(doctor)) {
                throw new IllegalStateException("Doctor is already assigned to the patient.");
            }

            patient.getDoctors().add(doctor);
            patientsRepo.save(patient);
        } else {
            throw new IllegalArgumentException("Patient or doctor not found.");
        }
    }

}
