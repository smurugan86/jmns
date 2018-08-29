package jmns.app.controller.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


public interface TaskController{
	
	String addTask(Model model);
	
	String saveTask(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
            
	String tasklist(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
	String edittask(Model model,
            @PathVariable(value = "id") String id);

	String viewtask(Model model,
            @PathVariable(value = "id") String id);
	
	String updateTask(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String deletetask(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
}