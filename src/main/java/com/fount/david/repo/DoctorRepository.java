package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	
	@Query("SELECT id, firstName, lastName FROM Doctor")
	List<Object[]> getDoctorIdAndNames();
	
	
}
