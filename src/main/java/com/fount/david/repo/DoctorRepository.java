package com.fount.david.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fount.david.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
