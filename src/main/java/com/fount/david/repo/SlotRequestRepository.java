package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.SlotRequest;

public interface SlotRequestRepository extends JpaRepository<SlotRequest, Long> {

	@Modifying
	@Query("UPDATE SlotRequest SET status=:status WHERE  id=:id")
	void updateSlotRequestStatus(Long id, String status);

	@Query("SELECT request FROM SlotRequest request INNER JOIN  request.patient as patient WHERE patient.email=:patientMail")
	List<SlotRequest> getAllPatientSlots(String patientMail);
	
	@Query("SELECT slotReq FROM SlotRequest slotReq INNER JOIN slotReq.appointment as appointment INNER JOIN appointment.doctor as doctor WHERE doctor.email = :doctorMail AND slotReq.status=:status")
	List<SlotRequest> getAllDoctorSlots(String doctorMail,String status);
}
