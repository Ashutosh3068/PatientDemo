package com.patientDemo.service;

import com.patientDemo.entity.Patient;
import com.patientDemo.exception.ResourceNotFound;
import com.patientDemo.payload.PatientDto;
import com.patientDemo.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Override
    public PatientDto addPatient(PatientDto patientDto) {
        Patient patient = mapToEntity(patientDto);
        Patient savePatient = patientRepo.save(patient);

        PatientDto dto = mapToDto(savePatient);
        dto.setMessage("New user");
        return dto;
    }

    @Override
    public PatientDto updatePatient(long id, PatientDto patientDto) {
        Patient patient = patientRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Record Not Found with id: " + id)
        );

        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setDisease(patientDto.getDisease());
        patient.setAge(patientDto.getAge());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        Patient saved = patientRepo.save(patient);
        PatientDto dto = mapToDto(saved);
        return dto;
    }

    @Override
    public String deleteRecord(long id) {
        patientRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Record Not Found By id " + id)
        );
        patientRepo.deleteById(id);
        return "Record Deleted With id " + id;
    }

    @Override
    public List<PatientDto> getAllPatient(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Patient> all = patientRepo.findAll(pageable);
        List<Patient> content = all.getContent();
        List<PatientDto> collect = content.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
        return collect;
    }

    public Patient mapToEntity(PatientDto patientDto){
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setGender(patientDto.getGender());
        patient.setDisease(patientDto.getDisease());
        patient.setAge(patientDto.getAge());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        return patient;
    }
    public PatientDto mapToDto(Patient savePatient){
        PatientDto dto = new PatientDto();
        dto.setId(savePatient.getId());
        dto.setName(savePatient.getName());
        dto.setEmail(savePatient.getEmail());
        dto.setGender(savePatient.getGender());
        dto.setDisease(savePatient.getDisease());
        dto.setAge(savePatient.getAge());
        dto.setPhoneNumber(savePatient.getPhoneNumber());
        dto.setMessage("Updated user");
        return dto;
    }

}

