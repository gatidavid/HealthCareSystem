package com.fount.david.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.exception.DoctorNotFoundException;
import com.fount.david.model.Doctor;
import com.fount.david.repo.DoctorRepository;
import com.fount.david.service.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorRepository repo;
	
	@Override
	public Long saveDoctor(Doctor doc) {
		
		return repo.save(doc).getId();
	}

	@Override
	public List<Doctor> getAllDoctors() {
		
		return repo.findAll();
	}

	@Override
	public Doctor getOneDoctor(Long id) {
		
		return repo.findById(id).orElseThrow(
				()-> new DoctorNotFoundException(id +" Doctor Record Do not Exist"));
	}

	@Override
	public void removeDoctor(Long id) {
		repo.delete(getOneDoctor(id));

	}

	@Override
	public void updateDoctor(Doctor doc) {
		
		if(repo.existsById(doc.getId())) {
			repo.save(doc);
		}else {
			throw new DoctorNotFoundException(doc.getId()+", Record Does not Exist");
		}

	}

}
