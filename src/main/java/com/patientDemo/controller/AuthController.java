package com.patientDemo.controller;

import com.patientDemo.entity.Roles;
import com.patientDemo.entity.User;
import com.patientDemo.payload.JWTAuthResponse;
import com.patientDemo.payload.LoginDto;
import com.patientDemo.payload.SignUpDto;
import com.patientDemo.repository.RoleRepo;
import com.patientDemo.repository.UserRepo;
import com.patientDemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/signUp")
    public ResponseEntity<String > registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepo.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("email exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepo.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("user exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Roles role = roleRepo.findByRoleName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepo.save(user);
        return new ResponseEntity<>("user register successfully", HttpStatus.CREATED);

    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authentication(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authenticate);
        return ResponseEntity.ok(new JWTAuthResponse(token));

    }

}
