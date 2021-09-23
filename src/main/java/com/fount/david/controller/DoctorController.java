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

import com.fount.david.exception.DoctorNotFoundException;
import com.fount.david.model.Doctor;
import com.fount.david.service.IDoctorService;
import com.fount.david.service.ISpecialisationService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private IDoctorService service;
	
	@Autowired
	private ISpecialisationService specialisationService;
	
	private void createDynamicUI(Model model) {
		model.addAttribute("specialisations", specialisationService.getSpecIdAndName());
	}
	
	
	@GetMapping("/register")
	public String displayRegForm(@RequestParam(value="message", required = false) String message,
								  Model model) {
		
		createDynamicUI(model);
		
		return "doctor-register";
	}
	@PostMapping("/save")
	public String saveDoctor(@ModelAttribute Doctor doc, Model model) {
		
		try {
			
			Long id = service.saveDoctor(doc);
			String message ="Doctor record with '"+id+"' saved";
			model.addAttribute("message", message);
			
			
		} catch (DoctorNotFoundException e) {
			model.addAttribute("message","Unable to Save Doctor Record");
			e.printStackTrace();
		}
		return "doctor-register";
	}
	@GetMapping("/all")
	public String viewAddDoctorData(Model model, @RequestParam(value="message",
									required=false) String message) {
		
		try {
			List<Doctor> list = service.getAllDoctors();
			model.addAttribute("list", list);
			model.addAttribute("message", message);
		} catch (Exception e) {
			model.addAttribute("message", "Sorry Unable to Load Doctor Record");
			e.printStackTrace();
		}
		return "doctor-data";
	}
	
	@GetMapping("/delete")
	public String removeDoctorData(@RequestParam Long id, 
								 RedirectAttributes attributes) {
		try {
			service.removeDoctor(id);
			attributes.addAttribute("message", "Record with id ('"+id+"') removed");
		} catch (DoctorNotFoundException e) {
			attributes.addAttribute("message", "Unable to Delete Doctor Record");
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String doDoctorEdit(@RequestParam Long id, Model model,
									RedirectAttributes attributes) {
		
		String page = null;
		try {
			Doctor doc = service.getOneDoctor(id);
			model.addAttribute("doctor", doc);
			page="doctor-edit";
		} catch (DoctorNotFoundException e) {
			attributes.addAttribute("message", "Chosen Doctor record does not exist");
			e.printStackTrace();
			page ="redirect:all";
		}
		
		return page;
	}
	
	@PostMapping("/update")
	public String doDoctorUpdate(@ModelAttribute Doctor doc, Model model) {
		
		try {
			service.updateDoctor(doc);
			model.addAttribute("message", "Doctor record with id ('"+doc.getId()+"') updated");
			
		} catch (DoctorNotFoundException e) {
			model.addAttribute("message", "Unable to update Doctor record with id ('"+doc.getId()+"')");
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	

}
