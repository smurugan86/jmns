package jmns.app.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


public interface AccountController{
	
	String addAccount(HttpServletRequest request,
			HttpServletResponse reponses,Model model);
	
	String saveAccount(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
            
	String accountlist(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
	String editAccount(HttpServletRequest request,
			HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	String updateAccount(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String deleteAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	String addCategory(Model model);
	
	String saveCategory(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String findAllCategory(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response);
}