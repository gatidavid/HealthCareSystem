package com.fount.david.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.constants.UserRoles;
import com.fount.david.exception.DoctorNotFoundException;
import com.fount.david.model.Doctor;
import com.fount.david.model.User;
import com.fount.david.repo.DoctorRepository;
import com.fount.david.service.IDoctorService;
import com.fount.david.service.IUserService;
import com.fount.david.util.MyCollectionsUtil;
import com.fount.david.util.UserUtil;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorRepository repo;
	
	@Autowired
	private UserUtil util;

	@Autowired
	private IUserService userService;
	
	@Override
	public Long saveDoctor(Doctor doc) {
		
		Long id = repo.save(doc).getId();
		if(id !=null) {
			User user = new User();
			user.setDisplayName(doc.getFirstName()+ " "+doc.getLastName());
			user.setUsername(doc.getEmail());
			user.setPassword(util.generatePassword());
			user.setRole(UserRoles.DOCTOR.name());
			userService.saveUser(user);
			
			// TODO : Email part pending
		}
		return id;
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

	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		
		List<Object[]> list = repo.getDoctorIdAndNames();
		return MyCollectionsUtil.convertToMapIndex(list);
	}

	
}
