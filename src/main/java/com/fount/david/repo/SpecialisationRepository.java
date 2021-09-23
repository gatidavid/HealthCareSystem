package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.Specialisation;

public interface SpecialisationRepository extends JpaRepository<Specialisation, Long> {


	@Query("SELECT COUNT(specCode) FROM Specialisation  WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("SELECT COUNT(specCode) FROM Specialisation WHERE specCode=:specCode AND id!=:id ")	
	Integer getSpecCodeCountForEdit(String specCode, Long id);
	
	@Query("SELECT COUNT(specName) FROM Specialisation  WHERE specName=:specName")
	Integer getSpecNameCount(String specName);

	@Query("SELECT id, specName FROM Specialisation")
	List<Object[]> getSpecIdAndName();
}
