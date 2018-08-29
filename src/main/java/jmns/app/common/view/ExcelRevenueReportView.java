package jmns.app.common.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.Document;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelRevenueReportView extends AbstractExcelView{
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		List<Document> revenueData = (List<Document>) model.get("revenueData");
		//create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Revenue Report");
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Date");
		header.createCell(1).setCellValue("Title");
		header.createCell(2).setCellValue("Description");
		
		int rowNum = 1;
		for (Document entry : revenueData) {
			//create the row data
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(entry.get("date").toString());
			row.createCell(1).setCellValue(entry.get("title").toString());
			row.createCell(2).setCellValue(entry.get("description").toString());
        }
	}
}