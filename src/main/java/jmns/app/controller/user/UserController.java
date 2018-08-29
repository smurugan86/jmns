package jmns.app.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserController{
	
	String saveUser(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	String updateUser(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	String editPage(Model model,
            @PathVariable(value = "id") String id);
	String addUserPage(Model model);
	String userList(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
}