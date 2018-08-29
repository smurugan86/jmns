package jmns.app.dao.rate;

import org.bson.Document;



public interface RateDAO {

  ////date,accountType, description,amount,userId
	String saveRate(Document rate);

	Iterable<Document> searchRate(String quantity,String productName,long amount, String userId);

	Document getRateById(String id);

	void updateRate(String rateId,Document rateDoc);

	boolean deleteRateById(String taskId);
	
}