package com.fount.david.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.fount.david.model.Specialisation;

public class SpecialisationExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
							Workbook workbook,
							HttpServletRequest request,
							HttpServletResponse response) throws Exception {
		
		
		//1. Define excel fine name
		response.addHeader("Content-Disposition", "attachment;filename=SPEC.xlsx");
		
		//2. Read data given by controller
		@SuppressWarnings("unchecked")
		List<Specialisation> list = (List<Specialisation>)model.get("list");
		
		//3. Create one sheet
		Sheet sheet = workbook.createSheet("SPECIALISATION");
		
		//4. Create row#0
		setHead(sheet);
		
		//5. create row#1 onwards from List<Specialisation>
		setBody(sheet, list);
	}

	

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("NAME");
		row.createCell(3).setCellValue("NOTE");
			
	}
	
	private void setBody(Sheet sheet, List<Specialisation> list) {
		int rowNum = 1;
		
		for(Specialisation spec: list) {
			
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(spec.getId());
			row.createCell(1).setCellValue(spec.getSpecCode());
			row.createCell(2).setCellValue(spec.getSpecName());
			row.createCell(3).setCellValue(spec.getSpecNote());
		}
		
	}

}
