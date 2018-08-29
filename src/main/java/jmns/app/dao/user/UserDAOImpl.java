package jmns.app.dao.user;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;



public class UserDAOImpl implements UserDAO{

	MongoCollection<Document> userCollection;
	
	 public UserDAOImpl() {
		 userCollection = CommonUtil.getMongoDBConnection(Constants.CLS_USERS);		 		
	 }
	 
	public boolean isValidUser(String username, String password) {
		// TODO Auto-generated method stub
				
        Document user = null;
        user = userCollection.find(eq(Constants.EMAIL, username)).first();   
        
        if(null!=user){
	        String hashedAndSalted = user.get(Constants.PASSWORD).toString();
	        String salt = hashedAndSalted.split(",")[1];
	        if (hashedAndSalted.equals(CommonUtil.makePasswordHash(password, salt))) {    
	        	userCollection.updateOne(new Document(Constants._ID, user.get(Constants._ID).toString()),
	    		        new Document("$set", new Document(Constants.LAST_LOGIN_DATE, new Date())));
	            return true;
	        }
        }
        
        System.out.println("Submitted password is not a match");
        return false;
              
        
       
	}

	public String saveUser(Document user) {
		
		try {			
			Document checkUser = userCollection.find(eq(Constants.EMAIL, user.get(Constants.EMAIL).toString())).first();
	        if(checkUser==null){
	        	userCollection.insertOne(user);
	        }
			return "1";
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("Username already in use");	               
	            }
		}
		return null;
	}

	public Iterable<Document> findAllUsers(String firstName,String lastName,String email) {
		// TODO Auto-generated method stub
		
		List<Document> searchList = new ArrayList<Document>();
		
		if(StringUtil.isNotBlank(firstName)){			
			searchList.add(new Document(Constants.FIRST_NAME,  new Document("$regex",firstName).append("$options", "i")));			
		}
		if(StringUtil.isNotBlank(lastName)){			
			searchList.add(new Document(Constants.LAST_NAME, new Document("$regex",lastName).append("$options", "i")));
		}
		if(StringUtil.isNotBlank(email)){			
			searchList.add(new Document(Constants.EMAIL, new Document("$regex",email).append("$options", "i")));
		}
		
		if(searchList.size()>0){
			Document userDoc = new Document("$or", searchList);
			Iterable<Document> users = userCollection.find(userDoc);
			return users;
		}else{
			Iterable<Document> users = userCollection.find();
			return users;
		}
		
		
	}

	public Document getUserById(String userId) {
		 Document user = null;
	        user = userCollection.find(eq(Constants._ID, userId)).first(); 
		return user;
	}

	public void updateUser(String userId, Document userDoc) {
		// TODO Auto-generated method stub
		try {			
			userCollection.updateOne(new Document(Constants._ID, userId),
		        new Document("$set", userDoc));		
		}catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("userId " + userId);	               
            }
		}
	}

	public Document getUserByEmail(String username) {
		 Document user = null;
	     user = userCollection.find(eq(Constants.EMAIL, username)).first(); 
		 return user;
	}
	
}