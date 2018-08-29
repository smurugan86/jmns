package jmns.app.controller.notes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.manager.notes.NoteManager;
import jmns.app.util.CommonUtil;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Murugan Srinivasan
 *
 */
@Controller
public class NoteControllerImpl implements NoteController{
	
	private NoteManager noteManager;	

	public NoteManager getNoteManager() {
		return noteManager;
	}

	public void setNoteManager(NoteManager noteManager) {
		this.noteManager = noteManager;
	}

	@RequestMapping("/addNote")
    public String addNote(Model model) {         
        model.addAttribute("date", "");
        model.addAttribute("title", "");        
        model.addAttribute("description", "");       
        return "note/addnewnote";         
    }
    
    @RequestMapping("/saveNote")
    public String saveNote(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String title = request.getParameter("title");        
         String description = request.getParameter("description");
         
         description = description.replaceAll("\\r?\\n", "<p>");
         
         boolean isCheck = CommonUtil.noteValidation(model,date,title,description);
         
         if(isCheck){
        	 String createdBy = CommonUtil.getSessionCookie(request,"email");
        	 String taskId = noteManager.saveNote(date,title,description,createdBy);        	 
        	 if(null!=taskId){
        		 List<Document> notelist = noteManager.findAllNotes("","","","",createdBy);
                 model.addAttribute("lists", notelist);
                 return "note/notelist";
               }else{
               	model.addAttribute("edate", "Save Notes Error");
               	return "note/addnewnote";
               }        	 
         }else{
        	 model.addAttribute("date", date);
 	         model.addAttribute("title", title);	       
 	         model.addAttribute("description", description);
        	 return "note/addnewnote";
         }
       
                  
    }
    
    @RequestMapping("/notelist")
    public String notelist(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	 String date = request.getParameter("date");
    	 String endDate = request.getParameter("endDate");
         String title = request.getParameter("title");
        
         String description = request.getParameter("description");
    	
    	 String createdBy = CommonUtil.getSessionCookie(request,"email");
    	 List<Document> tasklist = noteManager.findAllNotes(date,endDate,title,description,createdBy);
         model.addAttribute("lists", tasklist);
         
         model.addAttribute("date", date);
         model.addAttribute("endDate", endDate);
         model.addAttribute("title", title);        
         model.addAttribute("description", description);
         
         
         if(StringUtil.isBlank(date) && StringUtil.isBlank(endDate) && StringUtil.isBlank(title) &&
        		 StringUtil.isBlank(description)){     
        	 Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();    		
    		 String range = CommonUtil.dateToString(dates.getLeft())+" to "+CommonUtil.dateToString(dates.getRight());    		 
    		 model.addAttribute("range", range);    		
         }else{
        	 model.addAttribute("range", "Search Result");
         }
         
         return "note/notelist";
    }
    
    
    @RequestMapping("/editnote/{id}")
    public String editnote(Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = noteManager.getNotesById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("title", task.get("title").toString());
         
         String description =task.get("description").toString();
         description =	description.replaceAll("<p>", " ");
         description =	description.replaceAll("\\r?\\n", " ");	    	
         model.addAttribute("description", description );
                  
         return "note/editnotepage";          
    }
    
    @RequestMapping("/viewnote/{id}")
    public String viewnote(Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = noteManager.getNotesById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("title", task.get("title").toString());        
         model.addAttribute("description", task.get("description").toString());
         return "note/viewnotepage";          
    }
    
    
    @RequestMapping("/updateNote")
    public String updateNote(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String title = request.getParameter("title");
        
         String description = request.getParameter("description");
         
         
         String taskId = request.getParameter("_id");
         boolean isCheck = CommonUtil.noteValidation(model,date,title,description);
         if(isCheck){
        	 description = description.replaceAll("\\r?\\n", "<p>");
        	 noteManager.updateNote(taskId,date, title, description);        	          
        	 String createdBy = CommonUtil.getSessionCookie(request,"email");
        	 List<Document> noteslist = noteManager.findAllNotes("","","","",createdBy);
             model.addAttribute("lists", noteslist);
             return "note/notelist";
         }else{
        	 model.addAttribute("_id", taskId);
        	 model.addAttribute("date", date);
 	         model.addAttribute("title", title);	       
 	         model.addAttribute("description", description);
             return "note/editnotepage";
         }        
    }
    
    @RequestMapping("/deletenote/{id}")
    public String deletenote(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {    	              
    	 boolean isRemove = noteManager.deleteNoteById(id);  
    	 String createdBy = CommonUtil.getSessionCookie(request,"email");
    	 List<Document> notelist = noteManager.findAllNotes("","","","",createdBy);
         model.addAttribute("lists", notelist);
         return "note/notelist";      
    }
   
    
    @RequestMapping("/export")
    public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response){
				
		 String date = request.getParameter("date");
    	 String endDate = request.getParameter("endDate");
         String title = request.getParameter("title");
        
         String description = request.getParameter("description");
    	
    	 String createdBy = CommonUtil.getSessionCookie(request,"email");
    	 List<Document> tasklist = noteManager.findAllNotes(date,endDate,title,description,createdBy);
    	 
		
		return new ModelAndView("ExcelRevenueSummary","revenueData",tasklist);
		
		
	}
}