package com.patientDemo.service;

import com.patientDemo.payload.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto addPatient(PatientDto patientDto);

    PatientDto updatePatient(long id, PatientDto patientDto);

    String deleteRecord(long id);

    List<PatientDto> getAllPatient(int pageNo, int pageSize, String sortBy, String sortDir);
}
