package com.fount.david.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fount.david.model.Specialisation;

public interface ISpecialisationService {
	
	public Long saveSpecialisation(Specialisation spec);
	public List<Specialisation> getAllSpecialisation();
	public Page<Specialisation> getAllSpecialisation(Pageable pageable);
	public void removeSpecialisation(Long id);
	public Specialisation getOneSpecialisation(Long id);
	public void updateSpecialisation(Specialisation spec);
	
	
	public boolean isSpecCodeExist(String specCode);
	public boolean isSpecCodeExistForExist(String specCode, Long id);
	
	public boolean isSpecNameExist(String specName);
	public boolean isSpecNameExistForEdit(String specName, Long id);

	Map<Long, String> getSpecIdAndName();
	
	long getSpecializationCount();

}
