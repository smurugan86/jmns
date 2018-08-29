package jmns.app.dao.login;



public interface LoginDAO {

	boolean isValidUser(String username, String password);
	
}