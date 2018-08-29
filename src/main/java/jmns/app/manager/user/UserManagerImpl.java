package jmns.app.manager.user;



import java.util.ArrayList;
import java.util.List;

import jmns.app.dao.user.UserDAO;
import jmns.app.util.CommonUtil;

import org.bson.Document;



public class UserManagerImpl implements UserManager
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
		return userDAO.isValidUser(username, password);
		//return false;
	}

	public String saveUser(Document userDoc) {
		// TODO Auto-generated method stub
		return userDAO.saveUser(userDoc);
	}

	public List<Document> findAllUsers(String firstName,String lastName,String email) {
		// TODO Auto-generated method stub
		Iterable<Document> users = userDAO.findAllUsers(firstName, lastName, email);
		//
		if(null!=users){
			List<Document> userList = new ArrayList<Document>();
			for (Document document : users) {
				if(document.getDate("lastLoginDate")!=null){
				document.put("lastLoginDate", CommonUtil.dateToString(document.getDate("lastLoginDate")));
				}
				userList.add(document);
			}
			return userList;
		}else{
			return null;
		}
	}

	public Document getUserById(String userId) {
		// TODO Auto-generated method stub
		return userDAO.getUserById(userId);
	}

	public void updateUser(String userId, Document userDoc) {
		userDAO.updateUser(userId,userDoc);
	}	
}