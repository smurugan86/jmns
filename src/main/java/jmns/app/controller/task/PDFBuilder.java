package jmns.app.controller.task;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Table;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<Book> listBooks = (List<Book>) model.get("listBooks");
		
		doc.add(new Paragraph("Recommended books for Spring framework"));
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
		table.setSpacingBefore(10);
		
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		
		// write table header 
		cell.setPhrase(new Phrase("Book Title", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Author", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("ISBN", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Published Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);
		
		// write table row data
		for (Book aBook : listBooks) {
			table.addCell(aBook.getTitle());
			table.addCell(aBook.getAuthor());
			table.addCell(aBook.getIsbn());
			table.addCell(aBook.getPublishedDate());
			table.addCell(String.valueOf(aBook.getPrice()));
		}
		
		doc.add(table);
		
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			com.lowagie.text.Document document,
			com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,String> revenueData = (Map<String,String>) model.get("revenueData");
		
		Table table = new Table(2);
		table.addCell("Month");
		table.addCell("Revenue");
		
		for (Map.Entry<String, String> entry : revenueData.entrySet()) {
			table.addCell(entry.getKey());
			table.addCell(entry.getValue());
                }

		document.add(table);
	}

}