package com.lab.labappointment.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "files")
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID")
    private Long id;

    @Column(name = "File_Name")
    private String fileName;

    @Column(name = "File_Path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PatientID")
    private Patients patient;


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }


    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }


}
