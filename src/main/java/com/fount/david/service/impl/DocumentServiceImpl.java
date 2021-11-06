package com.fount.david.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fount.david.model.Document;
import com.fount.david.repo.DocumentRepository;
import com.fount.david.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository repo;

	@Override
	public void saveDocument(Document doc) {
		
		repo.save(doc);
	}

	@Override
	public void deleteDocumentById(Long id) {
		if(repo.existsById(id))
		repo.deleteById(id);
		else
			throw new RuntimeException("Document Not Exist");
	}

	@Override
	public Document getDocumentById(Long id) {
		
		return repo.findById(id).orElseThrow(
				()-> new RuntimeException("Document Do Not Exist")	
				);
	}

	@Override
	public List<Object[]> getDocumentIdAndName() {
		return repo.getDocumentIdAndName();
	}
	
	

}
