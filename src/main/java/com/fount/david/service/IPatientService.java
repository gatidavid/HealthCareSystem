package com.fount.david.service;

import java.util.List;

import com.fount.david.model.Patient;

public interface IPatientService {

	public Long savePatient(Patient patient);
	
	public List<Patient> getAllPatients();
	
	public Patient getOnePatient(Long id);
	
	public void removePatient(Long id);
	
	public void updatePatient(Patient patient);
}
