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
public class BillAmountVO{

	private String id;
	private String quantity;		
	private String productName;
	private Long amount;
	
	private String billQuantity;
	private Long billAmount;
	
	private String userId;
	private String billId;
	private Date date = new Date();
	private String createdBy;
	private Date createdDate = new Date();
		
	public BillAmountVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);
		this.amount = Long.parseLong(request.getParameter(Constants.AMOUNT));
		this.quantity = request.getParameter(Constants.QUANTITY);		
		this.productName = request.getParameter(Constants.PRODUCT_NAME);
		
		this.billId = request.getParameter(Constants.BILL_Id);
		this.billQuantity = request.getParameter(Constants.BILL_QNY);
		this.billAmount = Long.parseLong(request.getParameter(Constants.BILL_AMOUNT));
		
		this.createdBy = request.getParameter(Constants.CREATED_BY);		
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document billAmountVOToDoc(BillAmountVO accountVO) {			
		Document rate = new Document();
		rate.append(Constants._ID, UUID.randomUUID().toString());		
		rate.append(Constants.QUANTITY, accountVO.getQuantity());
		rate.append(Constants.PRODUCT_NAME, accountVO.getProductName());
		rate.append(Constants.AMOUNT, accountVO.getAmount());
		rate.append(Constants.CREATED_BY, accountVO.getCreatedBy());					
		rate.append(Constants.USER_ID, accountVO.getUserId());
		
		rate.append(Constants.BILL_Id, accountVO.getBillId());
		rate.append(Constants.BILL_QNY, accountVO.getBillQuantity());
		rate.append(Constants.BILL_AMOUNT, accountVO.getBillAmount());
		
		rate.append(Constants.DATE, new Date());
		rate.append(Constants.CREATED_DATE, new Date());				
		return rate;
	}
	
	public Document billAmountVOToDocUpdate(BillAmountVO rateDoc) {	
		Document rate = new Document();
		boolean isUpdate = false;	
		

		if(StringUtil.isNotBlank(rateDoc.getQuantity())){
			rate.append(Constants.QUANTITY, rateDoc.getQuantity());
			isUpdate = true;
		}
		if(rateDoc.getAmount()!=null){
			rate.append(Constants.AMOUNT, rateDoc.getAmount());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(rateDoc.getProductName())){
			rate.append(Constants.PRODUCT_NAME,rateDoc.getProductName());
			isUpdate = true;
		}
		
		/*if(rateDoc.getBillId()!=null){
			rate.append(Constants.BILL_Id, rateDoc.getBillId());
			isUpdate = true;
		}
		*/
		if(rateDoc.getBillAmount()!=null){
			rate.append(Constants.BILL_AMOUNT, rateDoc.getBillAmount());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(rateDoc.getBillQuantity())){
			rate.append(Constants.BILL_QNY,rateDoc.getBillQuantity());
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
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getBillQuantity() {
		return billQuantity;
	}

	public void setBillQuantity(String billQuantity) {
		this.billQuantity = billQuantity;
	}

	public Long getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Long billAmount) {
		this.billAmount = billAmount;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}	
	
}