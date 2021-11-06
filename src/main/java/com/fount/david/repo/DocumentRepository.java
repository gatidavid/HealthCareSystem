package com.fount.david.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fount.david.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query("SELECT docId, docName FROM Document")
	List<Object[]> getDocumentIdAndName();
}
