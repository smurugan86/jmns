package jmns.app.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;


public interface LoginController{
	
	String login(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	//String signUp(Model model);
}