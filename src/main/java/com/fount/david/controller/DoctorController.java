package com.fount.david.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import com.fount.david.util.MyMailUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private IDoctorService service;
	
	@Autowired
	private MyMailUtil mailUtil;
	
	
	@Autowired
	private ISpecialisationService specialisationService;
	
	/*Send specialisation id and name 
	to ui to populate dynamic 
	dropdown for doctor specialisation
	*/
	private void createDynamicUI(Model model) {
		model.addAttribute("specialisations", specialisationService.getSpecIdAndName());
	}
	
	/*  
	 * Show Register page
	 * */
	@GetMapping("/register")
	public String displayRegForm(@RequestParam(value="message", required = false) String message,
								  Model model) {
		
		model.addAttribute("message", message);
		createDynamicUI(model);
		
		return "doctor-register";
	}
	
	/*  
	 * Save on submit 
	 * 
	 * */
	@PostMapping("/save")
	public String saveDoctor(@ModelAttribute Doctor doc, 
							Model model,
							RedirectAttributes attributes) {
		
		try {
			
			Long id = service.saveDoctor(doc);
			String message ="Doctor record with '"+id+"' created";
			attributes.addAttribute("message", message);
			
			if(id!=null) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						mailUtil.send(
								doc.getEmail(), 
								"SUCCESS",
								message,
								new ClassPathResource("/static/myres/mongocheatsheat.pdf"));
					}
					
				}).start();
			}
			model.addAttribute("message", message);
			
			
		} catch (DoctorNotFoundException e) {
			model.addAttribute("message","Unable to Save Doctor Record");
			e.printStackTrace();
		} catch (Exception e) {
			model.addAttribute("message","Unable to Save Doctor Record");
			e.printStackTrace();
		}
		return "redirect:register";
	}
	
	/*
	 * Display Register Data 
	 * */
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

	/*
	 * Delete by id
	 * */
	
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
	
	/*
	 * Show Doctor edit page if it exist else for doctor data
	 * */
	@GetMapping("/edit")
	public String doDoctorEdit(@RequestParam Long id, Model model,
									RedirectAttributes attributes) {
		
		String page = null;
		try {
			Doctor doc = service.getOneDoctor(id);
			model.addAttribute("doctor", doc);
			createDynamicUI(model);
			page="doctor-edit";
		} catch (DoctorNotFoundException e) {
			attributes.addAttribute("message", "Chosen Doctor record does not exist");
			e.printStackTrace();
			page ="redirect:all";
		}
		
		return page;
	}
	
	/*
	 * Do update
	 * */
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
	
	//TODO: email and mobile duplicate validations (AJAX)
	
	//TODO: excel export

}
