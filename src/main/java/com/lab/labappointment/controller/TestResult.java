package com.lab.labappointment.controller;

import com.lab.labappointment.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/test-results")
public class TestResult {

    private final TestResultService testResultService;

    @Autowired
    public TestResult(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping("/patients/{patientId}")
    public ResponseEntity<?> saveTestResult(@PathVariable int patientId, @RequestBody com.lab.labappointment.entity.TestResult testResult) {
        testResultService.saveTestResult(patientId, testResult);
        return new ResponseEntity<>("Test result saved successfully", HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<com.lab.labappointment.entity.TestResult>> getAllTestResults() {
        List<com.lab.labappointment.entity.TestResult> allTestResults = testResultService.getAllTestResults();
        return new ResponseEntity<>(allTestResults, HttpStatus.OK);
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<com.lab.labappointment.entity.TestResult>> getTestResultsByPatientId(@PathVariable int patientId) {
        List<com.lab.labappointment.entity.TestResult> testResults = testResultService.getTestResultsByPatientId(patientId);
        return new ResponseEntity<>(testResults, HttpStatus.OK);
    }

    @PutMapping("/test-results/{testResultId}")
    public ResponseEntity<String> updateTestResult(@PathVariable int testResultId, @RequestBody com.lab.labappointment.entity.TestResult updatedTestResult) {
        boolean updated = testResultService.updateTestResult(testResultId, updatedTestResult);
        if (updated) {
            return ResponseEntity.ok("Test result updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test result not found.");
        }
    }

    @DeleteMapping("/test-results/{testResultId}")
    public ResponseEntity<String> deleteTestResult(@PathVariable int testResultId) {
        boolean deleted = testResultService.deleteTestResult(testResultId);
        if (deleted) {
            return ResponseEntity.ok("Test result deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test result not found.");
        }
    }
}
