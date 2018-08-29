package jmns.app.dao.login;

import static com.mongodb.client.model.Filters.eq;
import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class LoginDAOImpl implements LoginDAO{

	MongoCollection<Document> userCollection;
	
	 public LoginDAOImpl() {		
		 userCollection = CommonUtil.getMongoDBConnection(Constants.CLS_USERS);
	 }
	 
	public boolean isValidUser(String username, String password) {
		// TODO Auto-generated method stub
				
       Document user = null;
       user = userCollection.find(eq("_id", username)).first();        
       if (null!=user && 
       		!user.get("password").toString().equals(password)) {
       	return true;
       }
		return false;
	}
	
}