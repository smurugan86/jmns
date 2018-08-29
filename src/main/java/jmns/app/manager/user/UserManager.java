package jmns.app.manager.user;

import java.util.List;

import org.bson.Document;


public interface UserManager
{
	public boolean isValidUser(String username, String password);
	
	String saveUser(Document userDoc);  
	
	List<Document> findAllUsers(String firstName,String lastName,String email);

	public Document getUserById(String userId);

	public void updateUser(String userId, Document userDoc);

	
}