package com.fount.david.service;

import java.util.List;

import com.fount.david.model.Document;

public interface IDocumentService {

	void saveDocument(Document doc);
	void deleteDocumentById(Long id);
	Document getDocumentById(Long id);
	List<Object[]> getDocumentIdAndName();
}
