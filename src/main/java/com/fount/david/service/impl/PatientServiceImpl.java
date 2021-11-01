package com.fount.david.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.constants.UserRoles;
import com.fount.david.exception.PatientNotFoundException;
import com.fount.david.model.Patient;
import com.fount.david.model.User;
import com.fount.david.repo.PatientRepository;
import com.fount.david.service.IPatientService;
import com.fount.david.service.IUserService;
import com.fount.david.util.MyMailUtil;
import com.fount.david.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private MyMailUtil mailUtil; 

	

	@Override
	public Long savePatient(Patient patient) {
		
		Long id = repo.save(patient).getId();

		if (id!=null) {
			
			String pwd = userUtil.generatePassword();
			
			User user = new User();
			user.setDisplayName(patient.getFirstName() + " " + patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.PATIENT.name());
			
			Long generatedId =userService.saveUser(user);
			if(generatedId!=null) {
				
				new Thread(
						new Runnable() {
							
							@Override
							public void run() {
								String text = "Your name is "+patient.getEmail()+", password is "+pwd;
											
								 mailUtil.send(patient.getEmail(), 
											  "PATIENT ADDED", 
											  text);
							}
						}
						).start();
			}
		}
		return id;
	}

	@Override
	public List<Patient> getAllPatients() {

		return repo.findAll();
	}

	@Override
	public Patient getOnePatient(Long id) {

		return repo.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient Record Does Not Exist"));
	}

	@Override
	public void removePatient(Long id) {

		repo.delete(getOnePatient(id));

	}

	@Override
	public void updatePatient(Patient patient) {

		repo.save(patient);
	}

}
