package jmns.app.manager.bill;



import java.util.List;

import jmns.app.dao.bill.BillDAO;

import org.bson.Document;
import org.springframework.ui.Model;



public class BillManagerImpl implements BillManager
{

	private BillDAO billDAO;
			
	public BillDAO getBillDAO() {
		return billDAO;
	}

	public void setBillDAO(BillDAO billDAO) {
		this.billDAO = billDAO;
	}

	public String saveRate(Document rateDoc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Document> findAllAccount(String date, String endDate,
			String accountType, String description, long amount, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Document getRateById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRate(String rateId, Document rateDoc) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteRateById(String taskId) {
		// TODO Auto-generated method stub
		return false;
	}

	public Model searchRate(String quantity, String productName, long amount,
			String userId, Model model) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}