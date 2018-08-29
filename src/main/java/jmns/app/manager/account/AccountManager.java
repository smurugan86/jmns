package jmns.app.manager.account;

import java.util.List;

import org.bson.Document;
import org.springframework.ui.Model;


public interface AccountManager
{
	
	//date,accountType, description,amount
	public String saveAccount(String date, String accountType, String categoryName,String description,
			long amount,String userId);

	//date,accountType,description,amount,userId
	public List<Document> findAllAccount(String date,String endDate,String accountType,String description,long amount, String userId);

	public Document getAccountById(String id);

//	/acId,date,accountType,description,amount
	public void updateAccount(String acId, String date,String accountType,String categoryName,String description,long amount);

	public boolean deleteAccountById(String taskId);

	public List<Document> findAllCategory(String userId);

	public String saveCategory(String categoryName, String categoryType,
			String userId);

	public Model searchAccount(String date, String endDate,String accountType, String categoryName,
			String description, long amount, String userId, Model model);

	public Document getMySearchFeilds(String userId, String screenName);

	public Document saveMySearchFeilds(Document searchFeilds);

	public Document updateMySearchFeilds(String string, Document searchFeilds);
	
}