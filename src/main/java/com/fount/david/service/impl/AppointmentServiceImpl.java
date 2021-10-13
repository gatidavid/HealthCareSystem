package com.fount.david.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.exception.AppointMentNotFoundException;
import com.fount.david.model.Appointment;
import com.fount.david.repo.AppointmentRepository;
import com.fount.david.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

	@Autowired
	private AppointmentRepository repo;
	
	@Override
	public Long saveAppointment(Appointment appointment) {
		
		return repo.save(appointment).getId();
	}

	@Override
	public List<Appointment> getAllAppointment() {
		List<Appointment> loadAppointment= repo.findAll();
		return loadAppointment;
	}

	@Override
	public Appointment getOneAppointment(Long id) {
		
		return repo.findById(id).orElseThrow(
				()->
				new AppointMentNotFoundException("Appointment Does not Exist"));
	}

	@Override
	public void removeAppointment(Long id) {
		repo.delete(getOneAppointment(id));

	}

	@Override
	public void updateAppointment(Appointment appointment) {
		
		if(repo.existsById(appointment.getId())) {
			repo.save(appointment);
		}else {
			throw new AppointMentNotFoundException("Appointment Does not Exist");
		}

	}

}
