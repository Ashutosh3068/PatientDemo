package com.patientDemo.repository;

import com.patientDemo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo  extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(String name);
}
