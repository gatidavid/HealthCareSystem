package com.fount.david.service;

import java.util.List;
import java.util.Map;

import com.fount.david.model.Doctor;

public interface IDoctorService {

	public Long saveDoctor(Doctor doc);
	
	public List<Doctor> getAllDoctors();
	
	public Doctor getOneDoctor(Long id);
	
	public void removeDoctor(Long id);
	
	public void updateDoctor(Doctor doc);
	
	Map<Long, String> getDoctorIdAndNames();

	
}
