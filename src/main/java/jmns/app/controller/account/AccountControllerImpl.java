package jmns.app.controller.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.domain.AccountsVO;
import jmns.app.manager.account.AccountManager;
import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountControllerImpl implements AccountController{
	
	private AccountManager accountManager;
	
	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@RequestMapping("/addAccount")
    public String addAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {         
        model.addAttribute("date", "");
        model.addAttribute("title", "");
        model.addAttribute("userStory", "");
        model.addAttribute("description", "");   
        
        String userId = CommonUtil.getSessionCookie(request,"_id");
        List<Document> aclist = accountManager.findAllCategory(userId);
        model.addAttribute("lists", aclist);        
        return "account/addaccount";         
    }
    
    @RequestMapping("/saveAccount")
    public String saveAccount(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String accountType = request.getParameter("accountType");
         String categoryName = request.getParameter("categoryName");
         String description = request.getParameter("description");
         long amount = 0;
         if(StringUtil.isNotBlank(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
         
         AccountsVO accountsVO = new AccountsVO(request);
         
         boolean isCheck = CommonUtil.accountValidation(model,date,accountType,categoryName,description,amount);
         
         if(isCheck){
        	 Document accountsDoc = accountsVO.AccountVOToDoc(accountsVO);
        	 
        	 String userId = CommonUtil.getSessionCookie(request,"_id");
        	 String taskId = accountManager.saveAccount(date,accountType,categoryName, description,amount,userId);        	 
        	
        		try {
					reponses.sendRedirect("accountlist");
				} catch (IOException e) {					
					e.printStackTrace();
				}
        		 
        		/* List<Document> aclist = accountManager.findAllAccount("","","","",0, userId);
                 model.addAttribute("lists", aclist);*/
                 return "account/accountlist";
              
         }else{
        	 model.addAttribute("date", date);
 	        model.addAttribute("accountType", accountType);
 	        model.addAttribute("categoryName", categoryName);
 	        model.addAttribute("description", description);      
 	        model.addAttribute("amount", amount);
        	 return "account/addaccount";	
         }
              
    }
    
    @RequestMapping("/accountlist")
    public String accountlist(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	 String date = request.getParameter("date");
    	 String endDate = request.getParameter("endDate");
         String accountType = request.getParameter("accountType"); 
         String categoryName = request.getParameter("categoryName");
         String description = request.getParameter("description");
         String Search = request.getParameter("Search");
         
         System.out.println("Search ===================== > "+Search);
         long amount = 0;
         if(StringUtil.isNotBlank(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
    	 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 
    	 /*List<Document> tasklist = accountManager.findAllAccount(date,endDate,accountType,description,amount,userId);
    	 model.addAttribute("lists", tasklist);*/
    	 
    	 String screenName = "Account";
    	 Document searchFeilds = accountManager.getMySearchFeilds(userId,screenName);
    	 if(searchFeilds==null){
    		    searchFeilds = new Document();
    		    searchFeilds.append(Constants._ID, UUID.randomUUID().toString());    		    
    		    searchFeilds.append("screenName", "Account");
    		    searchFeilds.append(Constants.ACCOUNT_TYPE, accountType);
    		    searchFeilds.append(Constants.CATEGORY_NAME, categoryName);
    		    searchFeilds.append(Constants.AMOUNT, request.getParameter("amount"));
    		    searchFeilds.append(Constants.DESCRIPTION, description);		
    		    searchFeilds.append(Constants.USER_ID, userId);		
    		    searchFeilds.append(Constants.START_DATE, date);
    		    searchFeilds.append(Constants.END_DATE, endDate);
    		    searchFeilds.append(Constants.CREATED_DATE, new Date());
    		    searchFeilds = accountManager.saveMySearchFeilds(searchFeilds);
    	 }else if(StringUtil.isNotBlank(date) || StringUtil.isNotBlank(endDate) || StringUtil.isNotBlank(accountType) ||
    			 StringUtil.isNotBlank(categoryName) || StringUtil.isNotBlank(description) || 
    			 (StringUtil.isNotBlank(Search) && Search.equalsIgnoreCase("Search"))){    		 
    		searchFeilds.append(Constants.ACCOUNT_TYPE, accountType);
 		    searchFeilds.append(Constants.CATEGORY_NAME, categoryName);
 		    searchFeilds.append(Constants.AMOUNT, request.getParameter("amount"));
 		    searchFeilds.append(Constants.DESCRIPTION, description);		
 		    searchFeilds.append(Constants.USER_ID, userId);		
 		    searchFeilds.append(Constants.START_DATE, date);
 		    searchFeilds.append(Constants.END_DATE, endDate);
    		searchFeilds = accountManager.updateMySearchFeilds(searchFeilds.get("_id").toString(),searchFeilds);
    	 }else{
    		 if(null!=searchFeilds.get(Constants.START_DATE)){
    			 date = searchFeilds.get(Constants.START_DATE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.END_DATE)){
    			 endDate = searchFeilds.get(Constants.END_DATE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.ACCOUNT_TYPE)){
    			 accountType = searchFeilds.get(Constants.ACCOUNT_TYPE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.CATEGORY_NAME)){
    			 categoryName = searchFeilds.get(Constants.CATEGORY_NAME).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.DESCRIPTION)){
    			 description = searchFeilds.get(Constants.DESCRIPTION).toString();
    		 }
    	 }
    	 
    	 model = accountManager.searchAccount(date,endDate,accountType,categoryName,description,amount,userId,model);
    	 
         model.addAttribute("date", date);
         model.addAttribute("endDate", endDate);
         model.addAttribute("accountType", accountType);
         model.addAttribute("categoryName", categoryName);
         model.addAttribute("amount", amount);
         model.addAttribute("description", description);
         
         if(StringUtil.isBlank(date) && StringUtil.isBlank(endDate) && StringUtil.isBlank(accountType) &&
        		 StringUtil.isBlank(description) && StringUtil.isBlank(categoryName)){        	 
        	 Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();    		
    		 String range = CommonUtil.dateToString(dates.getLeft())+" to "+CommonUtil.dateToString(dates.getRight());    		 
    		 model.addAttribute("range", range);    		
         }else{
        	 model.addAttribute("range", "Search Result");
         }
         
         return "account/accountlist";
    }
    
    
    @RequestMapping("/editaccount/{id}")
    public String editAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = accountManager.getAccountById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("amount", task.get("amount").toString());
         model.addAttribute("accountType", task.get("accountType").toString());
         
         List<Document> aclist = accountManager.findAllCategory(CommonUtil.getSessionCookie(request,"_id"));
         model.addAttribute("lists", aclist);
         
         if(null != task.get("categoryName")){
        	 model.addAttribute("categoryName", task.get("categoryName").toString());
         }else{
        	 model.addAttribute("categoryName", "");
         }
         model.addAttribute("description", task.get("description").toString());
         return "account/editaccountpage";          
    }
    
    @RequestMapping("/updateAccount")
    public String updateAccount(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");    	 
         String accountType = request.getParameter("accountType");
         String description = request.getParameter("description");
         long amount = Long.parseLong(request.getParameter("amount"));
         String categoryName = request.getParameter("categoryName");
         
         String acId = request.getParameter("_id");
         boolean isCheck = CommonUtil.accountValidation(model,date,accountType,categoryName,description,amount);
         if(isCheck){
        	 accountManager.updateAccount(acId,date,accountType,categoryName,description,amount);     
        	 try {
					reponses.sendRedirect("accountlist");
				} catch (IOException e) {					
					e.printStackTrace();
				}
        	 
        	 //model = accountManager.searchAccount("","","","",0,userId,model);
             return "account/accountlist";
         }else{
        	 model.addAttribute("_id", acId);
        	 model.addAttribute("date", date);
 	        model.addAttribute("accountType", accountType);
 	        model.addAttribute("categoryName", categoryName);
 	        model.addAttribute("description", description);      
 	        model.addAttribute("amount", amount);
             return "account/editaccountpage";
         }        
    }
    
    @RequestMapping("/deleteaccount/{id}")
    public String deleteAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {    	              
    	 boolean isRemove = accountManager.deleteAccountById(id);  
    	 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 model = accountManager.searchAccount("","","","","",0,userId,model);
               
         return "account/accountlist";      
    }

    @RequestMapping("/addcategory")
	public String addCategory(Model model) {
		// TODO Auto-generated method stub
		  model.addAttribute("categoryName", "");
	      model.addAttribute("categoryType", "");
	        
		return "account/addcategory";
	}

    @RequestMapping("/savecategory")
	public String saveCategory(HttpServletRequest request,
			HttpServletResponse reponses, Model model) {
		

    	 String categoryName = request.getParameter("categoryName");
         String categoryType = request.getParameter("categoryType");
        
         boolean isCheck = CommonUtil.categoryValidation(model,categoryType,categoryName);
        
         if(isCheck){
        	 String userId = CommonUtil.getSessionCookie(request,"_id");
        	 String taskId = accountManager.saveCategory(categoryName,categoryType,userId);        	 
        	 if(null!=taskId){
        		  List<Document> aclist = accountManager.findAllCategory(userId);
        	      model.addAttribute("lists", aclist);
                 return "account/categorylist";
               }else{
               	model.addAttribute("ecategoryType", "Save category Error");
               	return "account/addcategory"; 
               }        	 
         }else{
        	 model.addAttribute("categoryType", categoryType);
 	        model.addAttribute("categoryName", categoryName);
        	 return "account/addcategory"; 
         }
       
         
         
	}

	@RequestMapping("/categorylist")
	public String findAllCategory(HttpServletRequest request,
			HttpServletResponse reponses, Model model) {
		
		String userId = CommonUtil.getSessionCookie(request,"_id");
		List<Document> aclist = accountManager.findAllCategory(userId);
	    model.addAttribute("lists", aclist);
        return "account/categorylist";
	}
	
	
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request,
			HttpServletResponse reponses, Model model) throws IOException {	
		List<Document> aclist = accountManager.findAllCategory("1");
	    model.addAttribute("name", aclist);
	    
	    
	    String sportsName = request.getParameter("sportsName");
        List<String> list = new ArrayList<String>();
        String json = null;

        if (null!=sportsName && sportsName.equals("Football")) {
                list.add("Lionel Messi");
                list.add("Cristiano Ronaldo");
                list.add("David Beckham");
                list.add("Diego Maradona");
        } else if (null!=sportsName && sportsName.equals("Cricket")) {
                list.add("Sourav Ganguly");
                list.add("Sachin Tendulkar");
                list.add("Lance Klusener");
                list.add("Michael Bevan");
        } else if (null!=sportsName && sportsName.equals("Select Sports")) {
                list.add("Select Player");
        }else{
        	list.add("Murugan");
        }

        model.addAttribute("value", list);
        
        
        json = "murugan";
        
        		//new Gson().toJson(list);
        reponses.setContentType("application/json");
        reponses.getWriter().write(json);
        
        return "account/test";
	}
	
   
	@RequestMapping("/accExport")
    public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response){
				
		String date = request.getParameter("date");
   	    String endDate = request.getParameter("endDate");
        String accountType = request.getParameter("accountType"); 
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");
        
        
        String screenName = "Account";
        String userId = CommonUtil.getSessionCookie(request,"_id");
   	    Document searchFeilds = accountManager.getMySearchFeilds(userId,screenName);
   	 if(null!=searchFeilds){
		   	 if(null!=searchFeilds.get(Constants.START_DATE)){
				 date = searchFeilds.get(Constants.START_DATE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.END_DATE)){
				 endDate = searchFeilds.get(Constants.END_DATE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.ACCOUNT_TYPE)){
				 accountType = searchFeilds.get(Constants.ACCOUNT_TYPE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.CATEGORY_NAME)){
				 categoryName = searchFeilds.get(Constants.CATEGORY_NAME).toString();
			 }
			 if(null!=searchFeilds.get(Constants.DESCRIPTION)){
				 description = searchFeilds.get(Constants.DESCRIPTION).toString();
			 }
   	 }
        System.out.println("date "+date);
        System.out.println("endDate "+endDate);
        System.out.println("accountType "+accountType);
        
        long amount = 0;
        if(StringUtil.isNotBlank(request.getParameter("amount"))){
       	 amount = Long.parseLong(request.getParameter("amount"));
        }
   	    
		List<Document> acc = accountManager.findAllAccount(date, endDate, accountType, description, amount, userId);
    	 
		System.out.println("Count "+acc.size());
		return new ModelAndView("ExcelAccountsSummary","revenueData",acc);
			
	}
	
}