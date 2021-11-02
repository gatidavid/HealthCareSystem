package com.fount.david.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fount.david.model.Appointment;
import com.fount.david.model.Patient;
import com.fount.david.model.SlotRequest;
import com.fount.david.model.User;
import com.fount.david.service.IAppointmentService;
import com.fount.david.service.IPatientService;
import com.fount.david.service.ISlotRequestService;

@Controller
@RequestMapping("/slots")
public class SlotRequestController {

	@Autowired
	private ISlotRequestService service;

	@Autowired
	private IAppointmentService appointmentService;

	@Autowired
	private IPatientService patientService;	
	
	// patient id, appointment id
		@GetMapping("/book")
		public String bookSlot(
				@RequestParam Long appid,
				HttpSession session,
				Model model
				) 
		{
			Appointment app = appointmentService.getOneAppointment(appid);

			//for patient object
			User user = (User) session.getAttribute("userOb");
			//email was used as the username
			String email = user.getUsername(); 
			Patient patient = patientService.getOneByEmail(email);

			// create slot object
			SlotRequest slotReq = new SlotRequest();
			slotReq.setAppointment(app);
			slotReq.setPatient(patient);
			slotReq.setStatus("PENDING");
			try {

				service.saveSlotRequest(slotReq);

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				String appDte = sdf.format( app.getDate());

				String message = " Patient " + (patient.getFirstName()+" "+patient.getLastName())
						+", Request for Dr. " + app.getDoctor().getFirstName() +" "+app.getDoctor().getLastName()
						+", On Date : " + appDte +", submitted with status: "+slotReq.getStatus();

				model.addAttribute("message", message);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "BOOKING REQUEST ALREADY MADE FOR THIS APPOINTMENT/DATE");
			}

			return "slot-request-mesage";
		}

}
