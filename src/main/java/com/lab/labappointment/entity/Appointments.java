package com.lab.labappointment.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Appointments", schema = "labappointment")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentID")
    private int appointmentId;

    @Column(name = "AppointmentTime")
    private Timestamp appointmentTime;

    @Column(name = "AppointmentNumber")
    private String appointmentNumber;

    @Column(name = "Test")
    private String test;

    @Column(name = "Status", nullable = false, columnDefinition = "VARCHAR(20) default 'Pending'")
    private String status;


    @ManyToOne
    @JoinColumn(name = "PatientID")
    private Patients patient;

    // Constructors, getters, setters, equals, hashCode, etc.

    // Constructors
    public Appointments() {
        this.status = "Pending";

    }

    public Appointments(Timestamp appointmentTime, Integer appointmentNumber, Patients patient, String test) {
        this.appointmentTime = appointmentTime;
        this.appointmentNumber = String.valueOf(appointmentNumber);
        this.patient = patient;
        this.status = "Pending";
        this.test = test;


    }

    // Getters and setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Timestamp getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = String.valueOf(appointmentNumber);
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointments that = (Appointments) o;

        if (appointmentId != that.appointmentId) return false;
        if (appointmentTime != null ? !appointmentTime.equals(that.appointmentTime) : that.appointmentTime != null)
            return false;
        if (appointmentNumber != null ? !appointmentNumber.equals(that.appointmentNumber) : that.appointmentNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appointmentId;
        result = 31 * result + (appointmentTime != null ? appointmentTime.hashCode() : 0);
        result = 31 * result + (appointmentNumber != null ? appointmentNumber.hashCode() : 0);
        return result;
    }
}
