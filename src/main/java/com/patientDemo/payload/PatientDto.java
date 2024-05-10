package com.patientDemo.payload;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {


    private long id;
    @NotEmpty
    @Size(min=2,message = "name cannot be less than 2 character")
    private String name;
    @NotEmpty
    @Email
    private String email;
    private String gender;
    private String disease;
    private int age;
    private long phoneNumber;
    private String message;
}
