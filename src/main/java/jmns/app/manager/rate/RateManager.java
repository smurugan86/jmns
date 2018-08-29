package jmns.app.manager.rate;

import java.util.List;

import org.bson.Document;
import org.springframework.ui.Model;


public interface RateManager
{
	
	public String saveRate(Document rateDoc);

	public List<Document> findAllAccount(String date,String endDate,String accountType,String description,long amount, String userId);

	public Document getRateById(String id);

	public void updateRate(String rateId,Document rateDoc);

	public boolean deleteRateById(String taskId);
	
	public Model searchRate(String quantity,
			String productName, long amount, String userId, Model model);
	
}