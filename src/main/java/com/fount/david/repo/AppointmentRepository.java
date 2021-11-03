package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

      
	@Query("SELECT appoint.date, appoint.noOfSlots, appoint.consultationFee, appoint.id FROM Appointment appoint INNER JOIN appoint.doctor as doctor WHERE doctor.id=:docId")
	public List<Object[]> getAppoinmentsByDoctor(Long docId);
	
	@Query("SELECT appoint.date, appoint.noOfSlots, appoint.consultationFee, appoint.details FROM Appointment appoint INNER JOIN appoint.doctor as doctor WHERE doctor.email=:username")
	public List<Object[]> getAppointmentByDoctorEmail(String username);
	

	@Modifying
	@Query("UPDATE Appointment SET noOfSlots = noOfSlots + :count WHERE id=:id")
	void updateSlotCountForAppoinment(Long id,int count);
}
