package jmns.app.dao.task;

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

public class TaskDAOImpl implements TaskDAO{
	
	MongoCollection<Document> taskCollection;
	
	public TaskDAOImpl() {
		try{		
		 taskCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_TASK);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}
	 
	public String saveTask(String date, String title, String userStory,
			String description,String createdBy) {
		try {
			description = description.replaceAll("\\r?\\n", "<p>");
			Document task = new Document();
			String id =UUID.randomUUID().toString();
	        task.append("_id", id).append("date", CommonUtil.stringToDate(date));
	        task.append("title", title);
	        task.append("userStory", userStory);
	        task.append("description", description);
	        task.append("createdBy", createdBy);	        
	        task.append("createdDate", new Date());
	        taskCollection.insertOne(task);	 
	      
			return id;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("userStory already in use: " + userStory);	               
	            }
		}
		return null;
	}

	public Iterable<Document> findAllTask(String date,String endDate,String title,String userStory,String description,String createdBy) {
	
		List<Document> searchList = new ArrayList<Document>();
		if(StringUtil.isNotBlank(date) || StringUtil.isNotBlank(endDate)){
			Document docQ= new Document();
			if(StringUtil.isNotBlank(date)){
				docQ.append("$gte",CommonUtil.stringToDate(date));
			}
			if(StringUtil.isNotBlank(endDate)){
				docQ.append("$lte",CommonUtil.stringToDate(endDate));
			}
			searchList.add(new Document("date",docQ));			
		}else if(StringUtil.isBlank(description) && StringUtil.isBlank(title) && StringUtil.isBlank(userStory)){		
			Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();
			Date startDate = dates.getLeft();
			Date endDatee = dates.getRight();			
			searchList.add(new Document("date",new Document("$gte",startDate).append("$lte",endDatee)));			
		}
		
		if(StringUtil.isNotBlank(title)){			
			searchList.add(new Document("title", new Document(Constants.REGEX,title).append(Constants.OPTIONS, "i")));
		}
		if(StringUtil.isNotBlank(userStory)){			
			searchList.add(new Document("userStory", new Document(Constants.REGEX,userStory).append(Constants.OPTIONS, "i")));
		}
		if(StringUtil.isNotBlank(description)){			
			searchList.add(new Document("description", new Document(Constants.REGEX,description).append(Constants.OPTIONS, "i")));
		}
		
		Document userDoc = new Document("createdBy", createdBy);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}		
		Iterable<Document> taskList = taskCollection.find(userDoc).sort(new Document("date", -1));
		return taskList;
	}

	public Document getTaskById(String id) {
		 Document task = null;
	        task = taskCollection.find(eq("_id", id)).first(); 
		return task;
	}

	public void updateTask(String noteId, String date, String title,
			String userStory, String description) {
		// TODO Auto-generated method stub
		try {		
			taskCollection.updateOne(new Document("_id", noteId),
			        new Document("$set", new Document("date", CommonUtil.stringToDate(date)).
			        		append("title", title).
			        		append("userStory", userStory).
			        		append("description", description)));			
		}catch (MongoWriteException e) {
			if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
				System.out.println("userStory already in use: " + noteId);	               
	        }
		}		
	}

	public boolean deleteTaskById(String noteId) {
		// TODO Auto-generated method stub
		
		taskCollection.deleteOne(new Document("_id", noteId));
		return true;
	}
	
}