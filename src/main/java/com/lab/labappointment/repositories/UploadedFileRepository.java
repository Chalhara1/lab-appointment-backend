package com.lab.labappointment.repositories;

import com.lab.labappointment.entity.Patients;
import com.lab.labappointment.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    List<UploadedFile> findByPatient(Patients patient);

}
