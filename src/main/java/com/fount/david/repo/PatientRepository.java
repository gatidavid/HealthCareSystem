package com.fount.david.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fount.david.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
