package jmns.app.controller.notes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


public interface NoteController{
	
	String addNote(Model model);
	
	String saveNote(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
            
	String notelist(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
	String editnote(Model model,
            @PathVariable(value = "id") String id);
	
	String viewnote(Model model,
            @PathVariable(value = "id") String id);
	
	String updateNote(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String deletenote(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response);
}