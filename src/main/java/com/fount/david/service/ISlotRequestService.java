package com.fount.david.service;

import java.util.List;

import com.fount.david.model.SlotRequest;

public interface ISlotRequestService {

	   //patient can book slot
		Long saveSlotRequest(SlotRequest slotReq);
		
		//ADMIN can view all slots
		List<SlotRequest> getAllSlotRequests();
		
		//ADMIN/patient can update status
		void updateSlotRequestStatus(Long id,String status);
		
		//PATIENT can see his slots
		List<SlotRequest> viewSlotsByPatientMail(String patientMail);
}
