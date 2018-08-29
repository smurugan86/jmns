package jmns.app.dao.bill;

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
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

public class BillDAOImpl implements BillDAO{
	
	MongoCollection<Document> acCollection;
	MongoCollection<Document> acTypeCollection;
	
	public BillDAOImpl() {
		try{				
		 acCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_ACC);
		 acTypeCollection = CommonUtil.getMongoDBConnection(Constants.CLS_CATEGORY);		 
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}
	 
	 ////date,accountType, description,amount,userId
	public String saveAccount(String date, String accountType,String categoryName,
			String description,long amount, String userId) {
		try {
			
			description = description.replaceAll("\\r?\\n", "<p>");
			 
			Document task = new Document();
			String id =UUID.randomUUID().toString();
	        task.append("_id", id).append("date", CommonUtil.stringToDate(date));
	        task.append("accountType", accountType);
	        task.append("categoryName", categoryName);
	        task.append("amount", amount);
	        task.append("description", description);
	        task.append("userId", userId);	        
	        task.append("createdDate", new Date());
	        acCollection.insertOne(task);	 
	        System.out.println("saveAccount userId" + userId);	
			return id;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("userStory already in use:");	               
	            }
		}
		return null;
	}

	public Iterable<Document> findAllAccount(String date,String endDate,String accountType,String categoryName,String description,long amount, String userId) {
			
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
		}else if(StringUtil.isBlank(description) && StringUtil.isBlank(accountType) && StringUtil.isBlank(categoryName)){			
			Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();
			Date startDate = dates.getLeft();
			Date endDatee = dates.getRight();			
			searchList.add(new Document("date",new Document("$gte",startDate).append("$lte",endDatee)));			
		}
		
		if(StringUtil.isNotBlank(accountType)){			
			searchList.add(new Document("accountType", accountType));
		}		
		//searchList.add(new Document("amount", amount));		
		if(StringUtil.isNotBlank(description)){			
			searchList.add(new Document("description", new Document("$regex",description).append("$options", "i")));
		}		
		
		if(StringUtil.isNotBlank(categoryName)){			
			searchList.add(new Document("categoryName", new Document("$regex",categoryName).append("$options", "i")));			
		}
		
		Document userDoc = new Document("userId", userId);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}			
		
		Iterable<Document> taskList = acCollection.find(userDoc).sort(new Document("date", -1));
		
		System.out.println("findAllAccount userId" + userId);
		
		
		/*List<Document> pipeline = new ArrayList<Document>();	 
		pipeline.add(new Document("$match",new Document("userId", userId)));
	    pipeline.add(new Document("$group",new Document("_id", null).append("total", new Document("$sum", "$amount"))));
	     
	     
	     AggregateIterable<Document> doc =  acCollection.aggregate(pipeline);	       
	     System.out.println("doc==============>"+doc.first());
	     System.out.println("doc==============>"+doc.first().getLong("total"));*/
	    
	    
		return taskList;
	}

	public Document getAccountById(String id) {
		Document task = null;
		task = acCollection.find(eq("_id", id)).first(); 	       
	    System.out.println("getAccountById userId" + id);	        		     
		return task;
	}

	public void updateAccount(String acId, String date,String accountType,String categoryName,String description,long amount) {
		// TODO Auto-generated method stub
		try {		
			acCollection.updateOne(new Document("_id", acId),
			        new Document("$set", new Document("date", CommonUtil.stringToDate(date)).append("accountType", accountType).
			        		append("amount", amount).append("description", description).append("categoryName", categoryName)));	
			
			System.out.println("updateAccount account Id" + acId);
			
		}catch (MongoWriteException e) {
			if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
				System.out.println("userStory already in use: " + amount);	               
	        }
		}		
	}

	public boolean deleteAccountById(String taskId) {
		// TODO Auto-generated method stub		
		System.out.println("deleteAccountById account Id" + taskId);
		acCollection.deleteOne(new Document("_id", taskId));
		
		//acCollection.aggregate(pipeline)
		return true;
	}

	public Iterable<Document> findAllCategory(String userId) {
		// TODO Auto-generated method stub	
		List<Document> searchList = new ArrayList<Document>();
		searchList.add(new Document("userId", "all"));
		searchList.add(new Document("userId", userId));
		Document userDoc = new Document();
		userDoc.append("$or",searchList);
		
		
		return acTypeCollection.find(userDoc).sort(new Document("name", 1));
	}

	public String saveCategory(String categoryName, String categoryType,
			String userId) {
		// TODO Auto-generated method stub
		Document doc = new Document();
		String id =UUID.randomUUID().toString();
        doc.append("_id", id).append("name", categoryName);
        doc.append("type", categoryType);
        doc.append("userId", userId);
        doc.append("createdDate", new Date());
		acTypeCollection.insertOne(doc);
		return id;
	}

	public AggregateIterable<Document> getAccountTotal(String date,String endDate,
			String accountType, String categoryName,String description, long amount, String userId) {
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
		}else if(StringUtil.isBlank(description) && StringUtil.isBlank(accountType) && StringUtil.isBlank(categoryName)){	
			Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();
			Date startDate = dates.getLeft();
			Date endDatee = dates.getRight();			
			searchList.add(new Document("date",new Document("$gte",startDate).append("$lte",endDatee)));			
		}
		
		if(StringUtil.isNotBlank(accountType)){			
			searchList.add(new Document("accountType", accountType));
		}	
		
		if(StringUtil.isNotBlank(categoryName)){			
			searchList.add(new Document("categoryName", new Document("$regex",categoryName).append("$options", "i")));
		}
		//searchList.add(new Document("amount", amount));		
		if(StringUtil.isNotBlank(description)){			
			searchList.add(new Document("description", new Document("$regex",description).append("$options", "i")));
		}		
		Document userDoc = new Document("userId", userId);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}
		
		
		
		List<Document> pipeline = new ArrayList<Document>();	 
		pipeline.add(new Document("$match",userDoc));
	    pipeline.add(new Document("$group",new Document("_id", "$accountType").append("total", new Document("$sum", "$amount"))));
	    	     
	    AggregateIterable<Document> doc =  acCollection.aggregate(pipeline);
	     
		return doc;
	}
	
}