package com.fount.david.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fount.david.exception.AppointMentNotFoundException;
import com.fount.david.model.Appointment;
import com.fount.david.service.IAppointmentService;
import com.fount.david.service.IDoctorService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    	                    
	@Autowired      
	private IAppointmentService service;
	@Autowired
	private IDoctorService doctorService;
	
	
	private void crateDynamicUI(Model model) {
		
		model.addAttribute("doctors", doctorService.getDoctorIdAndNames());
	}
	@GetMapping("/register")
	public String showAppointment(@RequestParam( value="message", required=false)String message, 
									Model model){
		model.addAttribute("message", message);
		crateDynamicUI(model);
		return "appointment-register";
	}	               
	
	@PostMapping("/save")
	public String doSaveAppointment(@ModelAttribute Appointment appointment,
									RedirectAttributes attributes, Model model) {
		try {
			Long id = service.saveAppointment(appointment);
			crateDynamicUI(model);
			String message = "Appointment record with id '"+id+"' created";
			attributes.addAttribute("message", message);
			
		} catch (Exception e) {
			attributes.addAttribute("message", "Sorry Unable to save Appointment");
			e.printStackTrace();
		}
		
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String viewAppointment(Model model,
					@RequestParam(value ="message", required = false)
					String message) {
		
		try {
			List<Appointment> list = service.getAllAppointment();
			model.addAttribute("list", list);
			model.addAttribute("message",message);
			
		} catch (Exception e) {
			model.addAttribute("message","Sorry Unable to Load Appointment Records ");
			e.printStackTrace();
		}
		return "appointment-data";
	}
	
	@GetMapping("/delete")
	public String removeAppointment(@RequestParam Long id,
					RedirectAttributes attributes) {
		
		try {
			service.removeAppointment(id);
			attributes.addAttribute("message", "Appointment with id '"+id+"' Deleted");
		} catch (Exception e) {
			attributes.addAttribute("message", "Appointment Does not Exist");
			e.printStackTrace();
		}
		
		return "redirect:all";
		
	}
	
	@GetMapping("/edit")
	public String editAppointment(@RequestParam Long id, Model model,
									RedirectAttributes attributes) {
		String page = null;
			try {
				Appointment appointment = service.getOneAppointment(id);
				crateDynamicUI(model);
				model.addAttribute("appointment", appointment);
				page = "appointment-edit";
			} catch (AppointMentNotFoundException e) {
				e.printStackTrace();
				attributes.addAttribute("message", "Appointment Doest not Exist");
				page = "redirect:all";
			}
		return page;
	}
	
	@RequestMapping("/update")
	public String updateAppointment(@ModelAttribute Appointment appointment, 
							RedirectAttributes attributes) {

		try {
			service.updateAppointment(appointment);
			attributes.addAttribute("message", "Record with id(" + appointment.getId() + ") is updated");

		} catch (AppointMentNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
	

}
