package jmns.app.dao.thoughts;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

public class ThoughtsDAOImpl implements ThoughtsDAO{
	
	MongoCollection<Document> postCollection;
	
	public ThoughtsDAOImpl() {
		try{		 	
		 postCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_POST);		 
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}
	 
	public String savePost(String userId, String title, 
			String post,List<String> tags,String author) {
		try {
			Document task = new Document();
			String id =UUID.randomUUID().toString();
	        task.append(Constants._ID, id).append(Constants.USER_ID, userId);
	        task.append(Constants.TITLE, title);	       
	        task.append(Constants.POST, post);
	        task.append(Constants.TAGS, tags);	
	        task.append(Constants.DATE, new Date());
	        task.append(Constants.AUTHOR,author);
	        task.append(Constants.CREATED_DATE, new Date());
	        postCollection.insertOne(task);	      
			return id;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("userStory already in use: " + userId);	               
	            }
		}
		return null;
	}

	public Iterable<Document> findAllPost(String date,String endDate,String tags,String title,String post,String userId) {
		List<Document> searchList = new ArrayList<Document>();		
		if(StringUtil.isNotBlank(date) || StringUtil.isNotBlank(endDate)){
			Document docQ= new Document();
			if(StringUtil.isNotBlank(date)){
				docQ.append(Constants.GTE,CommonUtil.stringToDate(date));
			}
			if(StringUtil.isNotBlank(endDate)){
				docQ.append(Constants.LTE,CommonUtil.stringToDate(endDate));
			}
			searchList.add(new Document(Constants.DATE,docQ));			
		}else if(StringUtil.isBlank(title) && StringUtil.isBlank(post)){			
			Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();
			Date startDate = dates.getLeft();
			Date endDatee = dates.getRight();			
			searchList.add(new Document(Constants.DATE,new Document(Constants.GTE,startDate).append(Constants.LTE,endDatee)));			
		}
		
		if(StringUtil.isNotBlank(title)){			
			searchList.add(new Document(Constants.TITLE, new Document(Constants.REGEX,title).append(Constants.OPTIONS, "i")));
		}
		
		if(StringUtil.isNotBlank(tags)){			
			searchList.add(new Document(Constants.TAGS, new Document(Constants.REGEX,tags).append(Constants.OPTIONS, "i")));
		}
		
		if(StringUtil.isNotBlank(post)){			
			searchList.add(new Document(Constants.POST, new Document(Constants.REGEX,post).append(Constants.OPTIONS, "i")));
		}
		
		Document userDoc = new Document(Constants.USER_ID, userId);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}		
		Iterable<Document> taskList = postCollection.find(userDoc).sort(new Document(Constants.DATE, -1));
		return taskList;
	}

	public Document getPostById(String id) {
		 Document task = null;
	        task = postCollection.find(eq(Constants._ID, id)).first(); 
		return task;
	}

	public void updatePost(String taskId, String title, String post,
			String tags) {
		try {				
	    	List<String> tagList = CommonUtil.extractTags(tags);	    	
			postCollection.updateOne(new Document(Constants._ID, taskId),
			        new Document("$set", new Document(Constants.TITLE,title).append(Constants.POST, post).
			        		append(Constants.TAGS, tagList)));			
		}catch (MongoWriteException e) {
			if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
				System.out.println("userStory already in use: ");	               
	        }
		}		
	}

	public boolean deletePostById(String taskId) {		
		postCollection.deleteOne(new Document(Constants._ID, taskId));
		return true;
	}

	public Iterable<Document> getAllPost(int from, int to,String searchValue,String msgStr,String author){
		Document userDoc = new Document();
		List<Document> searchList = new ArrayList<Document>();
		
		if(StringUtil.isNotBlank(searchValue)){					
			searchList.add(new Document(Constants.TITLE, new Document(Constants.REGEX,searchValue).append(Constants.OPTIONS, "i")));				
		}
		if(StringUtil.isNotBlank(msgStr)){
			searchList.add(new Document(Constants.POST, new Document(Constants.REGEX,msgStr).append(Constants.OPTIONS, "i")));
		}
		if(StringUtil.isNotBlank(author)){
			searchList.add(new Document(Constants.AUTHOR, new Document(Constants.REGEX,author).append(Constants.OPTIONS, "i")));
		}
		
		if(!searchList.isEmpty()){
			userDoc.append("$and",searchList);
		}
		Iterable<Document> taskList = postCollection.find(userDoc).sort(new Document(Constants.DATE, -1));
		return taskList;
	}

	public void addPostComment(String name, String email, String body,
			String postId) {
		// TODO Auto-generated method stub
		Document comment = new Document(Constants.AUTHOR,name)
        .append(Constants.BODY, body);
		comment.append(Constants.DATE, new Date());
		if (email != null && !email.isEmpty()) {
				comment.append(Constants.EMAIL, email);
		}
		postCollection.updateOne(eq(Constants._ID, postId),
               new Document("$push", new Document(Constants.COMMENTS, comment)));
	}
	
}