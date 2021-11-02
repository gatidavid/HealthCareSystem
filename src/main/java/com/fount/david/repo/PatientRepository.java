package com.fount.david.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fount.david.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByEmail(String email);
}
