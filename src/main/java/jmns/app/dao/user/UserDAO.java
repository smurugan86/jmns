package jmns.app.dao.user;

import org.bson.Document;



public interface UserDAO {

	boolean isValidUser(String username, String password);
	
	String saveUser(Document userDoc);  
	
	Iterable<Document> findAllUsers(String firstName,String lastName,String email);

	Document getUserById(String userId);

	void updateUser(String userId, Document userDoc);

	Document getUserByEmail(String username);
	
}