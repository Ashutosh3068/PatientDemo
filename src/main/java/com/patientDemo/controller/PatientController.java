package com.patientDemo.controller;

import com.patientDemo.payload.PatientDto;
import com.patientDemo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@Validated @RequestBody PatientDto patientDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PatientDto dto=patientService.addPatient(patientDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto>updatePatient(@PathVariable long id, @RequestBody PatientDto patientDto){
        PatientDto update =patientService.updatePatient(id, patientDto);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> DeleteRecord(@RequestParam long id){
        String message = patientService.deleteRecord(id);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping("/get")
    public ResponseEntity<List<PatientDto>> getAllPatient(
            @RequestParam(name = "pageNo",defaultValue = "0", required = false)int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3", required = false)int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "name", required = false)String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "DESC", required = false)String sortDir
    ) {

        List<PatientDto> dto = patientService.getAllPatient(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
