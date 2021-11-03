package com.fount.david.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fount.david.constants.SlotStatus;
import com.fount.david.model.SlotRequest;
import com.fount.david.repo.SlotRequestRepository;
import com.fount.david.service.ISlotRequestService;

@Service
public class SlotRequestServiceImpl implements ISlotRequestService {
  
	@Autowired
	private SlotRequestRepository repo;

	public Long saveSlotRequest(SlotRequest slotReq) {
		return repo.save(slotReq).getId();
	}

	public SlotRequest getOneSlotRequest(Long id) {
		Optional<SlotRequest> opt = repo.findById(id);
		if(opt!=null) {
			return opt.get();
		}
		return null;
	}
	public List<SlotRequest> getAllSlotRequests() {
		return repo.findAll();
	}

	@Transactional
	public void updateSlotRequestStatus(Long id, String status) {
		repo.updateSlotRequestStatus(id, status);
	}

	public List<SlotRequest> viewSlotsByPatientMail(String patientMail) {
		return repo.getAllPatientSlots(patientMail);
	}
	
	@Override
	public List<SlotRequest> viewSlotsByDoctorMail(String doctorMail) {
		return repo.getAllDoctorSlots(doctorMail,SlotStatus.ACCEPTED.name());
	}

}
