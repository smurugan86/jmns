package jmns.app.controller.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.manager.login.LoginManager;
import jmns.app.manager.task.TaskManager;
import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginControllerImpl{
	
	private LoginManager loginManager;
	private TaskManager taskManager;
		
	public TaskManager getTaskManager() {
		return taskManager;
	}
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	public LoginManager getLoginManager() {
		return loginManager;
	}
	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}


	//@RequestMapping("/login")
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
		try {
    	 String emailId = request.getParameter(Constants.USER_NAME);
         String password = request.getParameter(Constants.PASSWORD);
         
         
         if(StringUtil.isBlank(emailId) || StringUtil.isBlank(password)){
        	 model.addAttribute(Constants.LOGIN_ERROR, Constants.USER_PASS_REQUIRED);
        	 return "login";        	 
         }
         
         boolean isValid = loginManager.isValidUser(emailId, password);
        
        if(isValid){
        	 //reponses.addCookie(new Cookie("email", username));
        	Document user = loginManager.getUserByEmail(emailId);
        	             
        	Cookie userC;
			
				userC = new Cookie(Constants.EMAIL, URLEncoder.encode(emailId, "UTF-8"));
				userC.setMaxAge(60 * 60);
	        	String userNames = (String) user.get(Constants.FIRST_NAME) +" " +(String) user.get(Constants.LAST_NAME);
	        	reponses.addCookie(userC);
	        	reponses.addCookie(new Cookie("name", URLEncoder.encode(userNames, "UTF-8")));
	        	reponses.addCookie(new Cookie(Constants.USER_ROLE,  URLEncoder.encode((String) user.get(Constants.USER_ROLE), "UTF-8")));
	        	reponses.addCookie(new Cookie(Constants._ID, URLEncoder.encode((String) user.get(Constants._ID), "UTF-8")));
	        	
			          
        	
        	
        	
        	String createdBy = CommonUtil.getSessionCookie(request,Constants.EMAIL);
        	List<Document> tasklist = taskManager.findAllTask("","","","","",createdBy);
            model.addAttribute("lists", tasklist);
            
        	/*try {
				reponses.sendRedirect("tasklist");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
                     
           return "task/tasklist";
        }else{
        	 model.addAttribute(Constants.LOGIN_ERROR, Constants.USER_PASS_REQUIRED);
        }
                	
        return "login";
        
        } catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   
          
		return "login";
    }
	
	@RequestMapping("/autoLogin/{email}/{password}")
    public String autoLogin(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = Constants.EMAIL) String email,
            @PathVariable(value = Constants.PASSWORD) String password) {
		
		 model.addAttribute(Constants.EMAIL, email);
	     model.addAttribute(Constants.PASSWORD, password);
	        
		return "auto"; 
	}
	/*@RequestMapping("/autoLogin/{email}/{password}")
    public String autoLogin(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "email") String email,
            @PathVariable(value = "password") String password) {
    	       
		System.out.println("email = "+email);
		System.out.println("password = "+password);
		
		 boolean isValid = loginManager.isValidUser(email, password);
	        
	        if(isValid){
	        
	        	request.getSession().invalidate();
	        	request.getSession(false);
	        	
	        	for (Cookie cookie : request.getCookies()) {
	            	System.out.println("Old ="+ cookie.getName()+" "+cookie.getValue());	                
	            }
	        	
	        	 
	             
	             
	        	Document user = loginManager.getUserByEmail(email);
	        	           
	        	System.out.println("Get new user name "+user.get("firstName"));	        	
	        	
	        	String userNames = (String) user.get("firstName") +" " +(String) user.get("lastName");
	        	
	        	
	        	try { 
	                 for(Cookie cookie : request.getCookies()) { 
	                	 cookie.setMaxAge(0);
	                	 
	                	 
	                	 if(cookie.getName().equals("email")){
	                		 cookie.setValue(email);
	                    	}
	                    	if(cookie.getName().equals("name")){
	                    		cookie.setValue(userNames);
	                    	}
	                    	if(cookie.getName().equals("userRole")){
	                    		cookie.setValue((String) user.get("userRole"));
	                    	}
	                    	if(cookie.getName().equals("_id")){
	                    		cookie.setValue((String) user.get("_id"));
	                    	}
	                    	cookie.setMaxAge(60 * 60);
	                    	reponses.addCookie(cookie);
	                    	
	                     reponses.addCookie(cookie); 
	                 } 
	             }catch(Exception e) { 
	             	e.printStackTrace();
	                 System.out.println("del:::cookieValue::exception:::"+e); 
	             }
	        	
	        	
	        	Cookie[] cookies = request.getCookies();
	        	if (cookies != null) {
	        	int i = 0;
	        	boolean cookieExists = false;
	        		while (!cookieExists && i < cookies.length) {
	        	if (cookies[i].getName().equals("email")) {
	        		cookieExists = true;
	        		cookies[i].setValue(email);
	        		cookies[i].setMaxAge(60 * 60);
                	reponses.addCookie(cookies[i]);
	        	} else {
	        		i++;
	        	}
               }
	        	}
	        	
	        	///request.getSession();
	        	
	        	Cookie[] cookies2 = request.getCookies();
	            if (cookies2 != null) {
	            	System.out.println("Auto Login Started with");
	                for (Cookie cook : cookies2) {	
	                		
	                    	System.out.println("Old ="+cook.getName()+" "+cook.getValue());	
	                    	if(cook.getName().equals("email")){
	                    		cook.setValue(email);
	                    	}
	                    	if(cook.getName().equals("name")){
	                    		cook.setValue(userNames);
	                    	}
	                    	if(cook.getName().equals("userRole")){
	                    		cook.setValue((String) user.get("userRole"));
	                    	}
	                    	if(cook.getName().equals("_id")){
	                    		cook.setValue((String) user.get("_id"));
	                    	}
	                    	cook.setMaxAge(60 * 60);
	                    	reponses.addCookie(cook);
	                    	System.out.println("New => "+cook.getName()+" "+cook.getValue());	
	                   }
	            }
	          
	            Cookie userC = new Cookie("email", email);             
	        	userC.setMaxAge(80 * 60);
	        	reponses.addCookie(userC);
	        	reponses.addCookie(new Cookie("name", userNames));
	        	reponses.addCookie(new Cookie("userRole",(String) user.get("userRole")));
	        	reponses.addCookie(new Cookie("_id",(String) user.get("_id")));
	        	
	        	for (Cookie cookie : request.getCookies()) {
	            	System.out.println("new ="+cookie.getName()+" "+cookie.getValue());	                
	            }
	        	
	        	
	        	String createdBy = CommonUtil.getSessionCookie(request,"email");	        	
	        	System.out.println("autoLogin page Email Id "+createdBy);	        	 
	        	
	        	List<Document> tasklist = taskManager.findAllTask("","","","","",createdBy);
	            model.addAttribute("lists", tasklist); 
	            SessionHandler s = new SessionHandler();
	            s.set
	            return "auto"; 
	            
	        	try {
					reponses.sendRedirect("http://localhost:4040/jmns/tasklist");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                     
	           return "tasklist";
	        }else{
	        	 model.addAttribute("login_error", "Username or Password is invalid.");
	        }
	                	
	        return "login";         
    }*/
	
    
	@RequestMapping(value="/signup",method = RequestMethod.GET)
    public String signUp(Model model) {         
        model.addAttribute("firstName", "");
        model.addAttribute("lastName", "");
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        model.addAttribute("password_error", "");
        model.addAttribute("username_error", "");
        model.addAttribute("email_error", "");
        model.addAttribute("verify_error", "");       
        return "signup";         
    }
	
   
}