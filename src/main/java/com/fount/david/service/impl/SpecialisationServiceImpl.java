package com.fount.david.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fount.david.exception.SpecialisationNotFoundException;
import com.fount.david.model.Specialisation;
import com.fount.david.repo.SpecialisationRepository;
import com.fount.david.service.ISpecialisationService;
import com.fount.david.util.MyCollectionsUtil;

@Service
public class SpecialisationServiceImpl implements ISpecialisationService {

	@Autowired
	private SpecialisationRepository repo;

	public Long saveSpecialisation(Specialisation spec) {
		Long id = repo.save(spec).getId();
		return id;
	}

	public List<Specialisation> getAllSpecialisation() {
		return repo.findAll();
	}

	public Specialisation getOneSpecialisation(Long id) {

		return repo.findById(id)
				.orElseThrow(() -> new SpecialisationNotFoundException("Record with '" + id + "' Not Found"));
	}

	public void removeSpecialisation(Long id) {
		/* repo.deleteById(id); */
		repo.delete(getOneSpecialisation(id));
	}

	public void updateSpecialisation(Specialisation spec) {
		repo.save(spec);

	}

	public boolean isSpecCodeExist(String specCode) {

		return repo.getSpecCodeCount(specCode) > 0;
	}

	public boolean isSpecCodeExistForExist(String specCode, Long id) {

		return repo.getSpecCodeCountForEdit(specCode, id)>0;
	}
	

	public boolean isSpecNameExist(String specName) {

		return repo.getSpecNameCount(specName) > 0;

	}
	
	@Override
	public boolean isSpecNameExistForEdit(String specName, Long id) {
		
		return repo.getSpecCodeCountForEdit(specName, id) >0;
	}

	@Override
	public Map<Long, String> getSpecIdAndName() {
		
		List<Object[]> list = repo.getSpecIdAndName();
		Map<Long, String> map= MyCollectionsUtil.convertToMap(list);
		return map;
	}

	@Override
	public Page<Specialisation> getAllSpecialisation(Pageable pageable) {
		
		return repo.findAll(pageable);
	}

	

}
