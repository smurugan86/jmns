package jmns.app.dao.notes;

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

public class NoteDAOImpl implements NoteDAO{
	
	MongoCollection<Document> noteCollection;
	
	public NoteDAOImpl() {
		try{		
		 noteCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_NOTES);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}
	 
	public String saveNote(String date, String title, 
			String description,String createdBy) {
		try {
			Document task = new Document();
			String id =UUID.randomUUID().toString();
	        task.append("_id", id).append("date", CommonUtil.stringToDate(date));
	        task.append("title", title);	       
	        task.append("description", description);
	        task.append("createdBy", createdBy);	        
	        task.append("createdDate", new Date());
	        noteCollection.insertOne(task);	 
	      
			return id;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("userStory already in use: " + date);	               
	            }
		}
		return null;
	}

	public Iterable<Document> findAllNotes(String date,String endDate,String title,String description,String createdBy) {
		
		//db.getCollection("restaurants").find(
       /* new Document("$or", asList(new Document("cuisine", "Italian"),
                new Document("address.zipcode", "10075"))));*/
		
		//$regex   - $regex: /^ABC/i
		
		//regex("^"+ValidationUtil.escapeSpecialCharacters(title)+"$", "i");
		//db.products.find( { description: { $regex: /S/ } } )
		//db.products.find( { description: { $regex: /m.*line/, $options: 'si' } } )
		
		// TODO Auto-generated method stub
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
		}else if(StringUtil.isBlank(title) && StringUtil.isBlank(description)){			
			Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();
			Date startDate = dates.getLeft();
			Date endDatee = dates.getRight();			
			searchList.add(new Document("date",new Document("$gte",startDate).append("$lte",endDatee)));			
		}
		
		if(StringUtil.isNotBlank(title)){			
			searchList.add(new Document("title", new Document("$regex",title).append("$options", "i")));
		}
		
		if(StringUtil.isNotBlank(description)){			
			searchList.add(new Document("description", new Document("$regex",description).append("$options", "i")));
		}
		
		Document userDoc = new Document("createdBy", createdBy);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}		
		Iterable<Document> taskList = noteCollection.find(userDoc).sort(new Document("date", -1));
		return taskList;
	}

	public Document getNotesById(String id) {
		 Document task = null;
	        task = noteCollection.find(eq("_id", id)).first(); 
		return task;
	}

	public void updateNote(String taskId, String date, String title,
			String description) {
		// TODO Auto-generated method stub
		try {		
			noteCollection.updateOne(new Document("_id", taskId),
			        new Document("$set", new Document("date", CommonUtil.stringToDate(date)).append("title", title).
			        		append("description", description)));			
		}catch (MongoWriteException e) {
			if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
				System.out.println("userStory already in use: ");	               
	        }
		}		
	}

	public boolean deleteNoteById(String taskId) {
		// TODO Auto-generated method stub
		
		noteCollection.deleteOne(new Document("_id", taskId));
		return true;
	}
	
}