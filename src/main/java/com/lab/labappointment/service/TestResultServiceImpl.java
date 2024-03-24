package com.lab.labappointment.service;

import com.lab.labappointment.entity.Patients;
import com.lab.labappointment.entity.TestResult;
import com.lab.labappointment.repositories.PatientsRepo;
import com.lab.labappointment.repositories.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final PatientsRepo patientsRepository;
    private final TestResultRepository testResultRepository;

    @Autowired
    public TestResultServiceImpl(PatientsRepo patientsRepository, TestResultRepository testResultRepository) {
        this.patientsRepository = patientsRepository;
        this.testResultRepository = testResultRepository;
    }

    @Override
    public void saveTestResult(int patientId, TestResult testResult) {
        Patients patient = patientsRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        testResult.setPatient(patient);
        testResultRepository.save(testResult);
    }

    @Override
    public List<TestResult> getTestResultsByPatientId(int patientId) {
        return testResultRepository.findByPatient_PatientId(patientId);
    }

    @Override
    public boolean updateTestResult(int testResultId, TestResult updatedTestResult) {
        // Logic to update test result
        Optional<TestResult> optionalTestResult = testResultRepository.findById(testResultId);
        if (optionalTestResult.isPresent()) {
            TestResult existingTestResult = optionalTestResult.get();
            existingTestResult.setTestName(updatedTestResult.getTestName());
            existingTestResult.setResult(updatedTestResult.getResult());
            testResultRepository.save(existingTestResult);
            return true; // Updated successfully
        }
        return false; // Test result not found
    }

    @Override
    public boolean deleteTestResult(int testResultId) {
        // Logic to delete test result
        if (testResultRepository.existsById(testResultId)) {
            testResultRepository.deleteById(testResultId);
            return true; // Deleted successfully
        }
        return false; // Test result not found
    }

    @Override
    public List<TestResult> getAllTestResults() {
        // Logic to get all test results
        return testResultRepository.findAll();
    }

}
