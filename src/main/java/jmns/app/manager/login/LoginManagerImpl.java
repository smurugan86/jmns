package jmns.app.manager.login;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;

import jmns.app.dao.user.UserDAO;

public class LoginManagerImpl implements LoginManager
{

	private UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public boolean isValidUser(String username, String password) {
		// TODO Auto-generated method stub
		if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
			return false;
		}
		return userDAO.isValidUser(username, password);
		//return false;
	}
	public Document getUserByEmail(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUserByEmail(username);
	}
	
}