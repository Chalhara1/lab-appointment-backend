package com.lab.labappointment.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "TestResults")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestResultID")
    private int testResultId;

    @Column(name = "TestName")
    private String testName;

    @Column(name = "Result")
    private String result;

    @ManyToOne
    @JoinColumn(name = "PatientID")
    private Patients patient;

    // Constructors, getters, and setters

    public TestResult() {
    }

    public TestResult(String testName, String result) {
        this.testName = testName;
        this.result = result;
    }

    public int getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(int testResultId) {
        this.testResultId = testResultId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }
}
