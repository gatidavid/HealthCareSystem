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

import com.fount.david.exception.PatientNotFoundException;
import com.fount.david.model.Patient;
import com.fount.david.service.IPatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private IPatientService service;
	
	@GetMapping("/register")
	public String registerPatient(@RequestParam(value="message", required=false) String message)  {
		
		return "patient-register";
	}
	
	@PostMapping("/save")
	public String savePatient(@ModelAttribute Patient patient,
							   Model model) {
		
		try {
			Long id =service.savePatient(patient);
			String message = "Patient Record with id '"+id+"'saved";
			model.addAttribute("message", message);
			
		} catch (PatientNotFoundException e) {
			model.addAttribute("message", "Unable to save Patient");
			e.printStackTrace();
		
		}
		
		return "patient-register";
	}
	
	@GetMapping("/all")
	public String displayPatients(Model model, 
						@RequestParam(value ="message", required=false) String message){
	
		try {
			List<Patient> list = service.getAllPatients();
			model.addAttribute("list", list);
			model.addAttribute("message", message);
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("message", "No Records Available");
		}
		return "patient-data";
	}
	
	@GetMapping("/delete")
	public String deletePatient(@RequestParam Long id, Model model) {
		
		try {
		
			service.removePatient(id);
			String message ="Patient ('"+id+"' Deleted)";
			model.addAttribute("message", message);
			
			
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("message", "Unable to Delete Patient");
		}
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String editPatient(@RequestParam Long id, Model model,
								RedirectAttributes attributes) {
		
		String page = null;
		
		try {
			
			Patient patient = service.getOnePatient(id);
			model.addAttribute("patient", patient);
			
			page = "patient-edit";
			
		} catch (PatientNotFoundException e) {
			 e.printStackTrace();
			 attributes.addAttribute("message", "Unable to Retrieve Patient Record for Edit");
			 page="redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String doPatientUpdate(@ModelAttribute Patient patient,
			RedirectAttributes attributes) {
		
		try {
			
			service.updatePatient(patient);
			String message = "Patient ('"+patient.getId()+"') Updated";
			attributes.addAttribute("message", message);
	
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", "Unable to Update Patient Record");
		}
		return "redirect:all";
	}
	
	
	
}
