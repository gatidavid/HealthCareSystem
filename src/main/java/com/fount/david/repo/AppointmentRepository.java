package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


	@Query("SELECT appointment.date, appointment.noOfSlots, appointment.consultationFee FROM Appointment appointment INNER JOIN appointment.doctor as doctor WHERE doctor.id=:docId")
	public List<Object[]> getAppoinmentsByDoctor(Long docId);
}
