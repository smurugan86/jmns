package jmns.app.controller.bill;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.domain.RateVO;
import jmns.app.manager.bill.BillManager;
import jmns.app.util.CommonUtil;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BillControllerImpl implements BillController{
	
	private BillManager billManager;
	
	public BillManager getBillManager() {
		return billManager;
	}

	public void setBillManager(BillManager billManager) {
		this.billManager = billManager;
	}

	@RequestMapping("/addBill")
    public String addBill(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {         
        model.addAttribute("productName", "");
        model.addAttribute("quantity", "");
        model.addAttribute("amount", "");
         
        String userId = CommonUtil.getSessionCookie(request,"_id");
        model.addAttribute("userId", userId);
        String name = CommonUtil.getSessionCookie(request,"name");
        model.addAttribute("userName", name);
        
       /* String userId = CommonUtil.getSessionCookie(request,"_id");
        List<Document> aclist = billManager.findAllCategory(userId);
        model.addAttribute("lists", aclist);  */      
        return "rate/addbill";         
    }
    
    @RequestMapping("/saveBill")
    public String saveBill(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String quantity = request.getParameter("quantity");
         String productName = request.getParameter("productName");         
         long amount = 0;
         if(StringUtil.isNotBlank(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
         
         RateVO rateVO = new RateVO(request);         
         boolean isCheck = CommonUtil.rateValidation(model,rateVO);
         
         if(isCheck){
        	 Document rateDoc = rateVO.rateVOToDoc(rateVO);        	         	 
        	 String taskId = billManager.saveRate(rateDoc);        	         	
        		try {
					reponses.sendRedirect("ratelist");
				} catch (IOException e) {					
					e.printStackTrace();
				}        		
                return "rate/ratelist";             
         }else{
        	 model.addAttribute("quantity", quantity);
 	         model.addAttribute("productName", productName); 	            
 	         model.addAttribute("amount", amount);
        	 return "rate/addrate";	
         }
              
    }
    
    @RequestMapping("/ratelist")
    public String billList(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	
         String quantity = request.getParameter("quantity");
         String productName = request.getParameter("productName");
         long amount = 0;
         if(StringUtil.isNotBlank(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
    	 String userId = CommonUtil.getSessionCookie(request,"_id");    	     
    	 model = billManager.searchRate(quantity,productName,amount,userId,model);
    	         
    	 
    	
         
         model.addAttribute("quantity", quantity);
         model.addAttribute("amount", amount);
         model.addAttribute("productName", productName);
              
         
         return "rate/ratelist";
    }
    
    
    @RequestMapping("/editrate/{id}")
    public String editBill(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document rate = billManager.getRateById(id);         
    	 model.addAttribute("_id", rate.get("_id").toString());    	 
         model.addAttribute("amount", rate.get("amount").toString());
         model.addAttribute("quantity", rate.get("quantity").toString());
         
         String userId = CommonUtil.getSessionCookie(request,"_id");
         model.addAttribute("userId", userId);
         String name = CommonUtil.getSessionCookie(request,"name");
         model.addAttribute("userName", name);
         
         if(null != rate.get("productName")){
        	 model.addAttribute("productName", rate.get("productName").toString());
         }else{
        	 model.addAttribute("productName", "");
         }
        
         return "rate/editratepage";          
    }
    
    @RequestMapping("/updateRate")
    public String updateBill(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String quantity = request.getParameter("quantity");    	 
         String productName = request.getParameter("productName");
         long amount = Long.parseLong(request.getParameter("amount"));
         
         
         String rateId = request.getParameter("_id");
         RateVO rateVO = new RateVO(request);         
         boolean isCheck = CommonUtil.rateValidation(model,rateVO);
         if(isCheck){
        	 Document rateDoc = rateVO.rateVOToDocUpdate(rateVO);
        	 billManager.updateRate(rateId,rateDoc);     
        	 try {
					reponses.sendRedirect("ratelist");
				} catch (IOException e) {					
					e.printStackTrace();
				}        	
             return "rate/ratelist";
         }else{
        	 model.addAttribute("_id", rateId);
        	 model.addAttribute("quantity", quantity);
 	        model.addAttribute("productName", productName); 	           
 	        model.addAttribute("amount", amount);
             return "rate/editratepage";
         }        
    }
    
    @RequestMapping("/deleterate/{id}")
    public String deleteBill(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {    	              
    	 boolean isRemove = billManager.deleteRateById(id);  
    	 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 model = billManager.searchRate("","",0,userId,model);
               
         return "rate/ratelist";      
    }

    
	
	
   
}