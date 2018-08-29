package jmns.app.dao.account;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;



public interface AccountDAO {

  ////date,accountType, description,amount,userId
	String saveAccount(String date, String accountType, 
			String description,String categoryName,long amount,String userId);

	Iterable<Document> findAllAccount(String date,String endDate,String accountType,String categoryName,String description,long amount, String userId);

	Document getAccountById(String id);

	void updateAccount(String acId, String date,String accountType,String categoryName,String description,long amount);

	boolean deleteAccountById(String taskId);

	Iterable<Document> findAllCategory(String userId);

	String saveCategory(String categoryName, String categoryType, String userId);

	AggregateIterable<Document> getAccountTotal(String date,String endDate,
			String accountType, String categoryName, String description, long amount, String userId);

	Document getMySearchFeilds(String userId, String screenName);

	Document saveMySearchFeilds(Document searchFeilds);

	Document updateMySearchFeilds(String id, Document searchFeilds);
}