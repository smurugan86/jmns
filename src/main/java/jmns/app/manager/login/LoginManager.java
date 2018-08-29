package jmns.app.manager.login;

import org.bson.Document;

public interface LoginManager
{
	public boolean isValidUser(String username, String password);

	public Document getUserByEmail(String username);
	
}