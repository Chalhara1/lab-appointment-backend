package com.lab.labappointment.service;


import com.lab.labappointment.entity.TestResult;

import java.util.List;

public interface TestResultService {
    void saveTestResult(int patientId, TestResult testResult);

    List<TestResult> getTestResultsByPatientId(int patientId);

    boolean updateTestResult(int testResultId, TestResult updatedTestResult); // Add this method
    boolean deleteTestResult(int testResultId);

    List<TestResult> getAllTestResults();
}
