package com.fount.david.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fount.david.model.Document;
import com.fount.david.service.IDocumentService;


@Controller
@RequestMapping("/doc")
public class DocumentController {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
	@Autowired
	private IDocumentService service;

	// show Documents page
	@GetMapping("/all")
	public String showDocs(Model model) {
		model.addAttribute("idVal", System.currentTimeMillis());
		List<Object[]> list = service.getDocumentIdAndName();
		model.addAttribute("list", list);
		return "documents";
	}
	// Upload document
	@PostMapping("/upload")
	public String upload( 
					@RequestParam Long docId,
					@RequestParam MultipartFile docOb) {
		try {
			Document doc = new Document();
			doc.setDocId(docId);
			doc.setDocName(docOb.getOriginalFilename());
			
			service.saveDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	// Download by id
	@GetMapping("/download")
	public String downloadDoc(
			@RequestParam Long id,
			HttpServletResponse response) {
		
		try {
			// fetch Db object
			Document doc = service.getDocumentById(id);
			
			//provide file name using header
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="+doc.getDocName());
			//Copy data from Doc to response object
			FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	// delete by id
		@GetMapping("/delete")
		public String deleteDoc(
				@RequestParam Long id
				) 
		{
			try {
				service.deleteDocumentById(id);
			} catch (RuntimeException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
			return "redirect:all";
		}
	
}
