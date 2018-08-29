package jmns.app.controller.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


public interface BillController{
	
	String addBill(HttpServletRequest request,
			HttpServletResponse reponses,Model model);
	
	String saveBill(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
            
	String billList(HttpServletRequest request,
            HttpServletResponse reponses,Model model);
	
	String editBill(HttpServletRequest request,
			HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	String updateBill(HttpServletRequest request,
            HttpServletResponse reponses, Model model);
	
	String deleteBill(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id);
	
	
}