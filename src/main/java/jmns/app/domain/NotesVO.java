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
public class NotesVO{

	private String id;
	private String title;
	private String createdBy;	
	private String description;
	private String userId;
	private Date date = new Date();
	private Date createdDate = new Date();
		
	public NotesVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);
		this.title = request.getParameter(Constants.TITLE);
		this.createdBy = request.getParameter(Constants.CREATED_BY);		
		this.description = request.getParameter(Constants.DESCRIPTION);
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document AccountVOToDoc(NotesVO accountVO) {			
		Document notes = new Document();
		notes.append(Constants._ID, UUID.randomUUID().toString());		
		notes.append(Constants.TITLE, accountVO.getTitle());
		notes.append(Constants.CREATED_BY, accountVO.getCreatedBy());		
		notes.append(Constants.DESCRIPTION, accountVO.getDescription());		
		notes.append(Constants.USER_ID, accountVO.getUserId());		
		notes.append(Constants.DATE, new Date());
		notes.append(Constants.CREATED_DATE, new Date());				
		return notes;
	}
	
	public Document AccountVOToDocUpdate(NotesVO userDoc) {	
		Document notes = new Document();
		boolean isUpdate = false;	
		
		if(userDoc.getDate()!=null){
			notes.append(Constants.DATE,userDoc.getDate());
			isUpdate = true;
		}		
		if(StringUtil.isNotBlank(userDoc.getTitle())){
			notes.append(Constants.TITLE, userDoc.getTitle());
			isUpdate = true;
		}		
		if(StringUtil.isNotBlank(userDoc.getDescription())){
			notes.append(Constants.DESCRIPTION,userDoc.getDescription());
			isUpdate = true;
		}		
		if(isUpdate){
			notes.append(Constants.UPDATED_DATE, new Date());
			return notes;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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