package jmns.app.controller.rate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


public interface RateController{
	
	String addRate(HttpServletRequest request,
			HttpServletResponse reponses,Model model);
	
	String saveRate(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
            
	String ratelist(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
	String editAccount(HttpServletRequest request,
			HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	String updateRate(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String deleteRate(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	
}