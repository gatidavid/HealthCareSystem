package com.fount.david.service;

import java.util.List;

import com.fount.david.model.Appointment;

public interface IAppointmentService {

	public Long saveAppointment(Appointment appointment);
	
	public List<Appointment> getAllAppointment();
	
	public Appointment getOneAppointment(Long id);
	
	public void removeAppointment(Long id);
	
	public void updateAppointment(Appointment appointment);
	
	List<Object[]> getAppoinmentsByDoctor(Long docId);
	
	List<Object[]> getAppoinmentsByDoctorEmail(String username);
	
}
