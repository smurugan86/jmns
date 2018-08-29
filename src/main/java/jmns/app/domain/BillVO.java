package jmns.app.domain;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jmns.app.util.Constants;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;

/**
 * @author Murugan
 *
 */
public class BillVO{

	private String id;
	private String billName;
	
	private Long billAmount;
	private Long receivedAmount;
	private Long balanceAmount;	
	
	private String userId;
	private Date date = new Date();
	private String createdBy;
	private Date createdDate = new Date();
		
	public BillVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);		
		this.billName = request.getParameter(Constants.BILL_NAME);	
		this.createdBy = request.getParameter(Constants.CREATED_BY);		
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document billVOToDoc(BillVO accountVO) {			
		Document rate = new Document();
		rate.append(Constants._ID, UUID.randomUUID().toString());		
		rate.append(Constants.BILL_NAME, accountVO.getBillName());		
		rate.append(Constants.CREATED_BY, accountVO.getCreatedBy());					
		rate.append(Constants.USER_ID, accountVO.getUserId());		
		rate.append(Constants.DATE, new Date());
		rate.append(Constants.CREATED_DATE, new Date());				
		return rate;
	}
	
	public Document billVOToDocUpdate(BillVO rateDoc) {	
		Document rate = new Document();
		boolean isUpdate = false;	
		
				
		if(StringUtil.isNotBlank(rateDoc.getBillName())){
			rate.append(Constants.BILL_NAME, rateDoc.getBillName());
			isUpdate = true;
		}
				
		if(isUpdate){
			rate.append(Constants.UPDATED_DATE, new Date());
			return rate;
		}else{
			return null;
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Long getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Long billAmount) {
		this.billAmount = billAmount;
	}

	public Long getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(Long receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Long getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Long balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
}