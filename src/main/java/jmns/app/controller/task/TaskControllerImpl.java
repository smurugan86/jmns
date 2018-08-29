package jmns.app.controller.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.manager.task.TaskManager;
import jmns.app.util.CommonUtil;
import jmns.app.util.ExportUtil;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;




@Controller
public class TaskControllerImpl  extends PdfRevenueReportView implements TaskController{
	
	private TaskManager taskManager;	

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	@RequestMapping("/addTask")
    public String addTask(Model model) {         
        model.addAttribute("date", "");
        model.addAttribute("title", "");
        model.addAttribute("userStory", "");
        model.addAttribute("description", "");       
        return "task/addnewtask";         
    }
    
    @RequestMapping("/saveTask")
    public String saveTask(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String title = request.getParameter("title");
         String userStory = request.getParameter("userStory");
         String description = request.getParameter("description");
        
         boolean isCheck = CommonUtil.taskValidation(model,date,title,userStory,description);
         
         if(isCheck){
        	 String createdBy = CommonUtil.getSessionCookie(request,"email");
        	 String taskId = taskManager.saveTask(date, title, userStory, description,createdBy);        	 
        	 if(null!=taskId){
        		 List<Document> tasklist = taskManager.findAllTask("","","","","",createdBy);
                 model.addAttribute("lists", tasklist);
                 return "task/tasklist";
               }else{
            	   model.addAttribute("date", date);
       	        model.addAttribute("title", title);
       	        model.addAttribute("userStory", userStory);
       	        model.addAttribute("description", description);
            	   return "task/addnewtask";
               }        	 
         }else{
        	model.addAttribute("date", date);
 	        model.addAttribute("title", title);
 	        model.addAttribute("userStory", userStory);
 	        model.addAttribute("description", description);
        	 return "task/addnewtask";
         }
       
                 
    }
    
    @RequestMapping("/tasklist")
    public String tasklist(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	 String date = request.getParameter("date");
    	 String endDate = request.getParameter("endDate");
         String title = request.getParameter("title");
         String userStory = request.getParameter("userStory");
         String description = request.getParameter("description");
    	
                
      
       
         
    	 String createdBy = CommonUtil.getSessionCookie(request,"email");
    	 
    	// System.out.println("tasklist page Email Id "+createdBy);
    	 
    	 List<Document> tasklist = taskManager.findAllTask(date,endDate,title,userStory,description,createdBy);
         model.addAttribute("lists", tasklist);
         
         if(StringUtil.isBlank(date) && StringUtil.isBlank(endDate) && StringUtil.isBlank(title) &&
        		 StringUtil.isBlank(userStory) && StringUtil.isBlank(description)){     
        	 Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();    		
    		 String range = CommonUtil.dateToString(dates.getLeft())+" to "+CommonUtil.dateToString(dates.getRight());    		 
    		 model.addAttribute("range", range);    		
         }else{
        	 model.addAttribute("range", "Search Result");
         }
                
         model.addAttribute("date", date);
         model.addAttribute("endDate", endDate);               
         model.addAttribute("title", title);
         model.addAttribute("userStory", userStory);
         model.addAttribute("description", description);
         return "task/tasklist";
    }
    
    
    @RequestMapping("/edittask/{id}")
    public String edittask(Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = taskManager.getTaskById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("title", task.get("title").toString());
         model.addAttribute("userStory", task.get("userStory").toString());
         
         
         String description =task.get("description").toString();
         description =	description.replaceAll("<p>", " ");
         description =	description.replaceAll("\\r?\\n", " ");	    	
         model.addAttribute("description", description );
         return "task/edittaskpage";          
    }
    
    @RequestMapping("/viewtask/{id}")
    public String viewtask(Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = taskManager.getTaskById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("title", task.get("title").toString());
         model.addAttribute("userStory", task.get("userStory").toString());
         model.addAttribute("description", task.get("description").toString());
         return "task/viewtaskpage";          
    }
    
    @RequestMapping("/updateTask")
    public String updateTask(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String title = request.getParameter("title");
         String userStory = request.getParameter("userStory");
         String description = request.getParameter("description");
         String taskId = request.getParameter("_id");
             
         boolean isCheck = CommonUtil.taskValidation(model,date,title,userStory,description);
         if(isCheck){
        	 description = description.replaceAll("\\r?\\n", "<p>");
        	 taskManager.updateTask(taskId,date, title, userStory,description);          	
         	 String createdBy = CommonUtil.getSessionCookie(request,"email");
         	 List<Document> tasklist = taskManager.findAllTask("","","","","",createdBy);
             model.addAttribute("lists", tasklist);
             return "task/tasklist";         
         }else{
        	 model.addAttribute("_id", "taskId");
        	 model.addAttribute("date", date);
 	        model.addAttribute("title", title);
 	        model.addAttribute("userStory", userStory);
 	        model.addAttribute("description", description);
             return "task/edittaskpage";
         }        
    }
    
    @RequestMapping("/deletetask/{id}")
    public String deletetask(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {    	              
    	 boolean isRemove = taskManager.deleteTaskById(id);  
    	 String createdBy = CommonUtil.getSessionCookie(request,"email");
    	 List<Document> tasklist = taskManager.findAllTask("","","","","",createdBy);
         model.addAttribute("lists", tasklist);
         return "task/tasklist";      
    }
    
 
    @RequestMapping(value = "/report", method = RequestMethod.GET)
	protected ModelAndView pdfTask(HttpServletRequest req,
            HttpServletResponse res,Model model) throws BadElementException, DocumentException {
    	
    	Map<String,String> revenueData = new HashMap<String,String>();
		revenueData.put("1/20/2010", "$100,000");
		revenueData.put("1/21/2010", "$200,000");
		revenueData.put("1/22/2010", "$300,000");
		revenueData.put("1/23/2010", "$400,000");
		revenueData.put("1/24/2010", "$500,000");
    	
		ExportUtil.createPDF();
		
		com.itextpdf.text.Document  document = new com.itextpdf.text.Document();
	    try{
	    	res.setContentType("application/pdf");
	        PdfWriter.getInstance(document, res.getOutputStream());
	        document.open();
	        document.add(new Paragraph("Hello Kiran"));
	        document.add(new Paragraph(new Date().toString()));
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    
	    Paragraph title1 = new Paragraph("Chapter 1", 

	    		   FontFactory.getFont(FontFactory.HELVETICA, 
	    		   
	    		   18,  new CMYKColor(0, 255, 255,17)));
	    Chapter chapter1 = new Chapter(title1, 1);
	      
		chapter1.setNumberDepth(0);
		
 
  Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", 

	       FontFactory.getFont(FontFactory.HELVETICA, 16, 
	   
	       new CMYKColor(0, 255, 255,17)));
	    Section section1 = chapter1.addSection(title11);

	      Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");

	      section1.add(someSectionText);

	      someSectionText = new Paragraph("Following is a 3 X 2 table.");

	      section1.add(someSectionText);
	      
	      PdfPTable t = new PdfPTable(3);

	      t.setSpacingBefore(25);
	      
	      t.setSpacingAfter(25);
	      
	      PdfPCell c1 = new PdfPCell(new Phrase("Header1"));  
	      
	      t.addCell(c1);
	      
	      PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
	      
	      t.addCell(c2);
	      
	      PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
	      
	      t.addCell(c3);
	      
	      t.addCell("1.1");
	      
	      t.addCell("1.2");
	      
	      t.addCell("1.3");
	      
	      section1.add(t);
	      
	      document.add(section1);
	      
	    document.close();
	    //return null;
	 
	    
    
		return new ModelAndView("revenuereport","revenueData",revenueData);
		//return "task/tasklist";
    }


    @RequestMapping(value = "/report2", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
    	
    	List<Book> listBooks = new ArrayList<Book>();
		listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358",
				"June 29th 2011", 31.98F));
		listBooks.add(new Book("Spring in Practice", "Willie Wheeler, Joshua White",
				"1935182056", "May 16th 2013", 31.95F));
		listBooks.add(new Book("Pro Spring 3",
				"Clarence Ho, Rob Harrop", "1430241071", "April 18th 2012", 31.85F));
		listBooks.add(new Book("Spring Integration in Action", "Mark Fisher", "1935182439",
				"September 26th 2012", 28.73F));

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("pdfView", "listBooks", listBooks);
	}


    
   /* protected static byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException{
        JRPdfExporter exporter = new JRPdfExporter();       
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print({bUI: true,bSilent: false,bShrinkToFit: true});");     
        exporter.exportReport();        
        return baos.toByteArray();
    }*/
   
}