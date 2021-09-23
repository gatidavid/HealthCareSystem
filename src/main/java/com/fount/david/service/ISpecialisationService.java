package com.fount.david.service;

import java.util.List;
import java.util.Map;

import com.fount.david.model.Specialisation;

public interface ISpecialisationService {
	
	public Long saveSpecialisation(Specialisation spec);
	
	public List<Specialisation> getAllSpecialisation();
	
	public void removeSpecialisation(Long id);
	
	public Specialisation getOneSpecialisation(Long id);
	
	void updateSpecialisation(Specialisation spec);
	
	
	public boolean isSpecCodeExist(String specCode);
	
	public boolean isSpecCodeExistForExist(String specCode, Long id);
	
	public boolean isSpecNameExist(String specName);

	Map<Long, String> getSpecIdAndName();

}
