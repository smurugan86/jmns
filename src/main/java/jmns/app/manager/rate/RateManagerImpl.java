package jmns.app.manager.rate;



import java.util.ArrayList;
import java.util.List;

import jmns.app.dao.rate.RateDAO;
import jmns.app.util.CommonUtil;

import org.bson.Document;
import org.springframework.ui.Model;



public class RateManagerImpl implements RateManager
{

	private RateDAO rateDAO;
	
	public RateDAO getRateDAO() {
		return rateDAO;
	}
	public void setRateDAO(RateDAO rateDAO) {
		this.rateDAO = rateDAO;
	}

	
	public String saveRate(Document rateDoc) {	
		return rateDAO.saveRate(rateDoc);
	}



	public List<Document> findAllAccount(String date,String endDate,String accountType,String description,long amount, String userId) {
		// TODO Auto-generated method stub
		Iterable<Document> acList = rateDAO.searchRate(description,description,amount,userId);		
		if(null!=acList){
			List<Document> accList = new ArrayList<Document>();
			for (Document document : acList) {
				String de =document.get("description").toString();
				 de =	de.replaceAll("<p>", " ");
				 de =	de.replaceAll("\\r?\\n", " ");	
				 document.put("description", de);
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				accList.add(document);
			}
			return accList;
		}else{
			return null;
		}
	}

	public Document getRateById(String id) {
		// TODO Auto-generated method stub
		return rateDAO.getRateById(id);
	}
	
	public void updateRate(String rateId,Document rateDoc) {
		// TODO Auto-generated method stub
		rateDAO.updateRate(rateId,rateDoc);
	}
	public boolean deleteRateById(String taskId) {
		// TODO Auto-generated method stub
		return rateDAO.deleteRateById(taskId);
	}
	
	
	public Model searchRate(String quantity,
			String productName, long amount, String userId, Model model) {
		// TODO Auto-generated method stub		
		Iterable<Document> acList = rateDAO.searchRate(quantity,productName,amount,userId);				
		List<Document> accountlist = new ArrayList<Document>();
		for (Document document : acList) {
			document.put("date", CommonUtil.dateToString(document.getDate("date")));
			document.put("amount", CommonUtil.convertLongToString(document.getLong("amount")));
			accountlist.add(document);
		}
		
		 if(accountlist.isEmpty()){
        	 model.addAttribute("error", "Account Information Not Found");
         }else{
        	 model.addAttribute("error", null);
        	 model.addAttribute("lists", accountlist);        	        	
         }		
		return model;
	}
	
}