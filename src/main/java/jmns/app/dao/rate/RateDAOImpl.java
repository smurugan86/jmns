package jmns.app.dao.rate;

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

public class RateDAOImpl implements RateDAO{
	
	MongoCollection<Document> rateCollection;
	MongoCollection<Document> acTypeCollection;
	
	public RateDAOImpl() {
		try{				
		 rateCollection = CommonUtil.getMongoDBConnection(Constants.CLS_RATE);		 		
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}
	 
	 ////date,accountType, description,amount,userId
	public String saveRate(Document rateDoc) {
		try {
			
			//description = description.replaceAll("\\r?\\n", "<p>");			 		
	        rateCollection.insertOne(rateDoc);	 
	        System.out.println("saveAccount userId");	
			return "1";
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("userStory already in use:");	               
	            }
		}
		return null;
	}

	public Iterable<Document> searchRate(String quantity,String productName,long amount, String userId) {
			
		List<Document> searchList = new ArrayList<Document>();					
		if(StringUtil.isNotBlank(productName)){			
			searchList.add(new Document("productName", new Document("$regex",productName).append("$options", "i")));
		}		
		if(StringUtil.isNotBlank(quantity)){			
			searchList.add(new Document("quantity", new Document("$regex",quantity).append("$options", "i")));			
		}		
		Document userDoc = new Document("userId", userId);
		if(searchList.size()>0){
			userDoc.append("$and",searchList);
		}
		Iterable<Document> rateList = rateCollection.find(userDoc).sort(new Document("date", -1));
		System.out.println("findAllAccount userId" + userId);
		return rateList;
	}

	public Document getRateById(String id) {
		Document rate = null;
		rate = rateCollection.find(eq("_id", id)).first(); 	       
	    System.out.println("getAccountById userId" + id);	        		     
		return rate;
	}

	public void updateRate(String rateId, Document rateDoc) {
		// TODO Auto-generated method stub
		try {		
			rateCollection.updateOne(new Document("_id", rateId),
			        new Document("$set", rateDoc));	
			
			System.out.println("updateAccount account Id" + rateId);
			
		}catch (MongoWriteException e) {
			if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
				System.out.println("userStory already in use: " + rateId);	               
	        }
		}		
	}

	public boolean deleteRateById(String taskId) {
		// TODO Auto-generated method stub		
		System.out.println("deleteAccountById account Id" + taskId);
		rateCollection.deleteOne(new Document("_id", taskId));
		
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
	    	     
	    AggregateIterable<Document> doc =  rateCollection.aggregate(pipeline);
	     
		return doc;
	}
	
}