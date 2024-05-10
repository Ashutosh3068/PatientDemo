package com.patientDemo.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private long id;
    private String username;
    private String email;
    private String password;
}
