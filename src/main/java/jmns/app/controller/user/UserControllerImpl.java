package jmns.app.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.domain.UserVO;
import jmns.app.manager.user.UserManager;
import jmns.app.util.CommonUtil;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserControllerImpl implements UserController{
 	
	private UserManager userManager;
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	

    @RequestMapping("/saveUser")
    public String saveUser(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String firstName = request.getParameter("firstName");
         String lastName = request.getParameter("lastName");
         String email = request.getParameter("email");
         String password = request.getParameter("password");
         String verify = request.getParameter("verify");
        
        
         UserVO userVO = new UserVO(request);        
        // Document userDoc = CommonUtil.convertRequestToUserVO(userVO);               
         boolean isCheck = CommonUtil.userValidation(model,userVO,false);         
         if(isCheck){     
        	 Document userDoc = userVO.UserVOToDoc(userVO);
        	 String userId = userManager.saveUser(userDoc);        	 
        	 if(null!=userId){
        		 List<Document> users = userManager.findAllUsers("","","");
                 model.addAttribute("lists", users);
                 return "user/userlist";
               }else{
               	model.addAttribute("efirstName", "Save User Error");
                return "user/addnewuser";
               }        	 
         }else{
        	model.addAttribute("firstName", firstName);
 	        model.addAttribute("lastName", lastName);
 	        model.addAttribute("email", email);
 	        model.addAttribute("password", password);      
 	        model.addAttribute("verify", verify);
        	return "user/addnewuser";
         }
       
                  
    }
    
    
    @RequestMapping("/updateUser")
    public String updateUser(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String firstName = request.getParameter("firstName");
         String lastName = request.getParameter("lastName");
         String email = request.getParameter("email");
         String userId = request.getParameter("_id");
         String userRole= request.getParameter("userRole");
         System.out.println("updateUser email = "+email);
         
         UserVO userVO = new UserVO(request);
         
         
         boolean isCheck = CommonUtil.userValidation(model,userVO,true);
         
         if(isCheck){
        	 Document userDoc = userVO.UserVOToDocUpdate(userVO);
        	 userManager.updateUser(userId,userDoc);        	        	
       		 List<Document> users = userManager.findAllUsers("","","");
             model.addAttribute("lists", users);       
             return "user/userlist";
         }else{
           	model.addAttribute("_id", userId);
            return "user/editpage";
         }
    }
    
    //editpage
    @RequestMapping("/editpage/{id}")
    public String editPage(Model model,
            @PathVariable(value = "id") String id) {    	               
    	 Document user = userManager.getUserById(id);         
    	 model.addAttribute("_id", user.get("_id").toString());
    	 model.addAttribute("firstName", user.get("firstName").toString());
         model.addAttribute("lastName", user.get("lastName").toString());
         model.addAttribute("email", user.get("email").toString());         
         model.addAttribute("userRole", user.get("userRole").toString());
         
         return "user/editpage";          
    }
    
    @RequestMapping("/addUser")
    public String addUserPage(Model model) {         
        model.addAttribute("firstName", "");
        model.addAttribute("lastName", "");
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        model.addAttribute("password_error", "");
        model.addAttribute("username_error", "");
        model.addAttribute("email_error", "");
        model.addAttribute("verify_error", "");       
        return "user/addnewuser";         
    }
    
    @RequestMapping("/userlist")
    public String userList(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {    	
    	String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");         
    	List<Document> users = userManager.findAllUsers(firstName,lastName,email);
        model.addAttribute("lists", users);        
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", email);        
        return "user/userlist";         
    }
     
}