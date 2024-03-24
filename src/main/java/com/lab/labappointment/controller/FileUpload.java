package com.lab.labappointment.controller;

import com.lab.labappointment.entity.Patients;
import com.lab.labappointment.entity.UploadedFile;
import com.lab.labappointment.repositories.PatientsRepo;
import com.lab.labappointment.repositories.UploadedFileRepository;
import com.lab.labappointment.response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FileUpload {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Autowired
    private UploadedFileRepository fileRepository;

    @Autowired
    private PatientsRepo patientsRepository; // Assuming you have a PatientsRepository

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("patientId") int patientId) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setFileName(file.getOriginalFilename());
            uploadedFile.setFilePath(path.toString());

            // Fetch patient from database using patientId
            Patients patient = patientsRepository.findById(patientId).orElse(null);
            if (patient == null) {
                return "Patient not found.";
            }

            uploadedFile.setPatient(patient);

            fileRepository.save(uploadedFile);

            return "File uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }

    @GetMapping("/files/{patientId}")
    public ResponseEntity<FileResponse> getFilePathsByPatientId(@PathVariable int patientId) {
        Patients patient = patientsRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        List<UploadedFile> uploadedFiles = fileRepository.findByPatient(patient);
        if (uploadedFiles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FileResponse fileResponse = new FileResponse();
        for (UploadedFile uploadedFile : uploadedFiles) {
            fileResponse.getFilePaths().add(uploadedFile.getFilePath());
            fileResponse.getFileNames().add(uploadedFile.getFileName());
        }

        return ResponseEntity.ok(fileResponse);
    }

    @PutMapping("/files/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

            UploadedFile existingFile = fileRepository.findById(fileId).orElse(null);
            if (existingFile == null) {
                return ResponseEntity.notFound().build();
            }

            // Delete the existing file
            Files.deleteIfExists(Paths.get(existingFile.getFilePath()));

            // Update file details
            existingFile.setFileName(file.getOriginalFilename());
            existingFile.setFilePath(path.toString());

            fileRepository.save(existingFile);

            return ResponseEntity.ok("File updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update file.");
        }
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        UploadedFile fileToDelete = fileRepository.findById(fileId).orElse(null);
        if (fileToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Delete the file from the filesystem
            Files.deleteIfExists(Paths.get(fileToDelete.getFilePath()));

            // Delete the file record from the database
            fileRepository.delete(fileToDelete);

            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file.");
        }
    }


}
