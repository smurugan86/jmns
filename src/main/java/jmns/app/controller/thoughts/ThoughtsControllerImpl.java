package jmns.app.controller.thoughts;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmns.app.manager.thoughts.ThoughtsManager;
import jmns.app.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ThoughtsControllerImpl implements ThoughtsController{
 	
	private ThoughtsManager thoughtsManager;
	
	public ThoughtsManager getThoughtsManager() {
		return thoughtsManager;
	}
	public void setThoughtsManager(ThoughtsManager thoughtsManager) {
		this.thoughtsManager = thoughtsManager;
	}


	@RequestMapping("/addpost")
    public String addNote(Model model) {         
       
        model.addAttribute("title", "");        
        model.addAttribute("post", ""); 
        model.addAttribute("tags", "");
        
        return "post/addnewpost";         
    }
    
	
	@RequestMapping("/saveMyPost")
    public String saveMyPost(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String tags = request.getParameter("tags");
         String title = request.getParameter("title");        
         String post = request.getParameter("post");
         
         
         // extract tags
        List<String> tagsArray = CommonUtil.extractTags(tags);

         // substitute some <p> for the paragraph breaks
         post = post.replaceAll("\\r?\\n", "<p>");
         
        boolean isCheck = CommonUtil.postValidation(model,tags,title,post);
         
         if(isCheck){
        	 String userId = CommonUtil.getSessionCookie(request,"_id");
        	 String author = CommonUtil.getSessionCookie(request,"name");
        	 String taskId = thoughtsManager.savePost(userId,title,post,tagsArray,author);        	 
        	 if(null!=taskId){
        		 List<Document> postList = thoughtsManager.findAllPost("","","","","",userId);
                 model.addAttribute("lists", postList);
                 return "post/postlist";
               }else{
               	model.addAttribute("edate", "Save Notes Error");
               	return "post/addPost";
               }        	 
         }else{
        	 model.addAttribute("tags", tags);
 	         model.addAttribute("title", title);	       
 	         model.addAttribute("post", post);
        	 return "post/addPost";
         }           
    }
   
	@RequestMapping("/postlist")
    public String postList(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	 String date = request.getParameter("date");
    	 
    	 String endDate = request.getParameter("endDate");
    	 
    	 String tags = request.getParameter("tags");
    	 
         String title = request.getParameter("title");
        
         String post = request.getParameter("post");
    	
    	 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 List<Document> postList = thoughtsManager.findAllPost(date,endDate,tags,title,post,userId);
         model.addAttribute("lists", postList);
         
         model.addAttribute("date", date);
         model.addAttribute("endDate", endDate);
         model.addAttribute("tags", tags);
         model.addAttribute("title", title);        
         model.addAttribute("post", post);
         
         
         if(StringUtil.isBlank(date) && StringUtil.isBlank(tags) && StringUtil.isBlank(title) &&
        		 StringUtil.isBlank(post)){     
        	 Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();    		
    		 String range = CommonUtil.dateToString(dates.getLeft())+" to "+CommonUtil.dateToString(dates.getRight());    		 
    		 model.addAttribute("range", range);    		
         }else{
        	 model.addAttribute("range", "Search Result");
         }
         
         return "post/postlist";
    }
	
	@RequestMapping("/postlistbytag/{tag}")
    public String postListByTag(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "tag") String tag) {
		
		 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 List<Document> postList = thoughtsManager.findAllPost("","",tag,"","",userId);
       
         model.addAttribute("lists", postList);
    	 return "post/allpost";
    	 
		
	}
	
	
	
	 @RequestMapping("/editpost/{id}")
	    public String editpost(Model model,
	            @PathVariable(value = "id") String id) {
	    	               
	    	 Document task = thoughtsManager.getPostById(id);         
	    	 model.addAttribute("_id", task.get("_id").toString());
	    	 
	    	String post =task.get("post").toString();
	    	post =	post.replaceAll("<p>", " ");
	    	post =	post.replaceAll("\\r?\\n", " ");
	    	//post =	post.replaceAll("r?n", " ");
	    	
	    	 model.addAttribute("post", post);
	         model.addAttribute("title", task.get("title").toString());
	         //model.addAttribute("userStory", task.get("userStory").toString());
	         
	         List<String> tagList = (List<String>) task.get("tags");
				String tagss = StringUtils.join(tagList,",");
				
	       // String tt = StringUtils.join(task.get("tags"),",");
	        		 
	         model.addAttribute("tags", tagss);
	         return "post/editpostpage";          
	    }
	    
	    @RequestMapping("/viewpost/{id}")
	    public String viewpost(Model model,
	            @PathVariable(value = "id") String id) {
	    	               
	    	 Document task = thoughtsManager.getPostById(id);         
	    	 model.addAttribute("_id", task.get("_id").toString());
	    	 model.addAttribute("post", task.get("post"));
	         model.addAttribute("title", task.get("title").toString()); 
	         
	         String tags = StringUtils.join(task.get("tags"),",");
	         model.addAttribute("tags", tags);
	         return "post/viewpostpage";          
	    }
	    
	    
	    @RequestMapping("/post/{id}")
	    public String post(Model model,
	            @PathVariable(value = "id") String id) {
	    	               
	    	 Document document = thoughtsManager.getPostById(id);  
	    	 document.put("date", CommonUtil.dateToString(document.getDate("date")));
	    	 
	    	 model.addAttribute("_id", document.get("_id").toString());	    		    	
	    	 model.addAttribute("post", document);	        	         	      
	         return "post/postpage";          
	    }
	    
	    @RequestMapping("/newcomment")
	    public String newComment(HttpServletRequest request,
	            HttpServletResponse reponses, Model model) {
	    	
	    	 String postId = request.getParameter("_id");
	    	 String name = request.getParameter("commentName");
	         String email = request.getParameter("commentEmail");
	        
	         String body = request.getParameter("commentBody");
	         thoughtsManager.addPostComment(name, email, body, postId);
	         
	         System.out.println("postId = " +postId);
	         Document document = thoughtsManager.getPostById(postId);  
	    	 document.put("date", CommonUtil.dateToString(document.getDate("date")));
	    	 
	    	 model.addAttribute("_id", document.get("_id").toString());	    		    	
	    	 model.addAttribute("post", document);	
	         
	    	return "post/postpage";
	    }
	    /*newcomment
	    thoughtsManager.addPostComment(name, email, body, permalink);*/
	    
	    @RequestMapping("/updatePost")
	    public String updatePost(HttpServletRequest request,
	            HttpServletResponse reponses, Model model) {
	    	
	    	 String post = request.getParameter("post");
	    	
	    	 
	         String title = request.getParameter("title");
	        
	         String tags = request.getParameter("tags");
	         String postId = request.getParameter("_id");
	         
	         
	         // substitute some <p> for the paragraph breaks
	         
	         
	         boolean isCheck = CommonUtil.postValidation(model,post,title,tags);
	         if(isCheck){
	        	 post = post.replaceAll("\\r?\\n", "<p>");
	        	 thoughtsManager.updatePost(postId, title,post, tags);        	          
	        	 String userId = CommonUtil.getSessionCookie(request,"_id");
	        	 List<Document> postList = thoughtsManager.findAllPost("", "", "", "", "", userId);
	             model.addAttribute("lists", postList);
	             return "post/postlist";
	         }else{
	        	 model.addAttribute("_id", postId);
	        	 model.addAttribute("post", post);
	 	         model.addAttribute("title", title);	       
	 	         model.addAttribute("tags", tags);
	             return "post/editpostpage";
	         }        
	    }
	    
	    @RequestMapping("/deletepost/{id}")
	    public String deletepost(HttpServletRequest request,
	            HttpServletResponse reponses,Model model,
	            @PathVariable(value = "id") String id) {    	              
	    	 boolean isRemove = thoughtsManager.deletePostById(id);  
	    	 String userId = CommonUtil.getSessionCookie(request,"_id");
	    	 List<Document> postList = thoughtsManager.findAllPost("", "", "", "", "", userId);
	         model.addAttribute("lists", postList);
	         return "post/postlist";      
	    }
	
	
	
	
	    @RequestMapping("/allPost")
	    public String allPost(HttpServletRequest request,
	            HttpServletResponse reponses,Model model) {
	    	
	    	String titleStr = request.getParameter("titleStr");
	    	String msgStr = request.getParameter("msgStr");
	    	String author = request.getParameter("author");
	    	int from=0;
	    	int to = 50;
	    	
	    	 List<Document> postList = thoughtsManager.getAllPost(from,to,titleStr,msgStr,author);
	         model.addAttribute("lists", postList);
	         
	         model.addAttribute("titleStr", titleStr);
	         model.addAttribute("msgStr", msgStr);
	         model.addAttribute("author", author);
	    	 return "post/allpost";    
	    }
	
	
	
	

}