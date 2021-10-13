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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fount.david.exception.SpecialisationNotFoundException;
import com.fount.david.model.Specialisation;
import com.fount.david.service.ISpecialisationService;
import com.fount.david.view.SpecialisationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecialisationController {

	@Autowired 
	private ISpecialisationService service;
	

		/**
		 * If End user enters /register, GET type
		 *  Display one Register page in browser
		 */
	@GetMapping("/register")
	public String showSpecForm() {
		
		return "specialisation-register";
	}
	
	/***
	 * On Click HTML Form Submit
	 * Read data as  ModelAttribute
	 * Save using service 
	 * Return to UI page( register)
	 */
	@PostMapping("/save")
	public String saveSpec(@ModelAttribute Specialisation spec, 
				Model model) {
		
		try {
			Long id = service.saveSpecialisation(spec);
			String message = "Specialisation '"+id+"'  Details Save!";
			model.addAttribute("message", message);
		} catch (SpecialisationNotFoundException e) {
			model.addAttribute("message", "Unable To Save Specialisation Details");
			e.printStackTrace();
		}
		return "specialisation-register";
	}
	/***
	 * FETCH DATA FROM DATABASE as LIST<T>
	 * SEND IT TO UI .
	 * 
	 */
	
	@GetMapping("/all")
	public String getAllSpecData(Model model,
								 @RequestParam(value ="message", 
								 required=false) String message ){
		
		try {
			List<Specialisation> list= service.getAllSpecialisation();
			model.addAttribute("list", list);
			model.addAttribute("message", message);
		} catch (Exception e) {
			model.addAttribute("message", "Sorry Unable to Load Specialisation Details");
			e.printStackTrace();
		}
		 return "specialisation-data";
	}
	/**
	 *  Delete by id
	 */
	@GetMapping("/delete")
	public String deleteSpecData(@RequestParam Long id, 
			RedirectAttributes attributes){
		
		try {
			service.removeSpecialisation(id);
			attributes.addAttribute("message", "Record with id ("+id+") is removed");
		
		} catch (SpecialisationNotFoundException e) {
			attributes.addAttribute("message", e.getMessage());
			e.printStackTrace();
		
		}
		
		
		return "redirect:all";
	}
	/**
	 * Fetch Data into Edit page
	 *  End user clicks on Link, may enter ID manually.
	 *  If entered id is present in DB
	 *     > Load Row as Object
	 *     > Send to Edit Page
	 *     > Fill in Form
	 *  Else
	 *    > Redirect to all (Data Page)
	 *    > Show Error message (Not found)     
	 */
	
	@GetMapping("/edit")
	public String editSpec(@RequestParam Long id, Model model,
						   RedirectAttributes attributes){
		String page =null;
		try {
			Specialisation spec =service.getOneSpecialisation(id);
			model.addAttribute("specialisation", spec);
			page="specialisation-edit";
			
		} catch (SpecialisationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", "Chosen Specialisation does not exist");
			page = "redirect:all";
		}
			return page;
	}
	
	/***
	 *  Update Form data and redirect to all
	 */
	@RequestMapping("/update")
	public String updateSpec(@ModelAttribute Specialisation specialisation, 
							RedirectAttributes attributes) {

		try {
			service.updateSpecialisation(specialisation);
			attributes.addAttribute("message", "Record with id(" + specialisation.getId() + ") is updated");

		} catch (SpecialisationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}

	
	@GetMapping("/checkCode")
	public @ResponseBody String validateSpecCode(
					@RequestParam String code,
					@RequestParam Long id) {
		
		String message ="";
		
		if(id == 0 && service.isSpecCodeExist(code)) {//register check
			message = code +", already exist";
		}else if(id !=0 && service.isSpecCodeExistForExist(code, id)) {//edit check
			message = code + ", already exist";
		}
		return message;
	}
	
	
	@GetMapping("/checkName")
	public @ResponseBody String validateSpecName(@RequestParam String name,
												 @RequestParam Long id) {
		
		String message ="";
		
		if(id==0 && service.isSpecNameExist(name)) {
			message = name +", already exist";
		}else if(id !=0 && service.isSpecNameExistForEdit(name, id));
		
		return message;
	}
	
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		
	ModelAndView mav = new ModelAndView();
	mav.setView(new SpecialisationExcelView());
	
	List<Specialisation> list = service.getAllSpecialisation();
	mav.addObject("list", list);
	
	return mav;
	}
	
	
	
}
