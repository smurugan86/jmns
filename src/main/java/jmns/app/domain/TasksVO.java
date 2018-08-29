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
public class TasksVO{

	private String _id;
	private String title;		
	private String description;
	private String userId;
	private String userStory;
	private Date date = new Date();
	private String createdBy;
	private Date createdDate = new Date();
		
	public TasksVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);
		this.userStory = request.getParameter(Constants.USER_STORY);
		this.title = request.getParameter(Constants.TITLE);
		this.createdBy = request.getParameter(Constants.CREATED_BY);		
		this.description = request.getParameter(Constants.DESCRIPTION);
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document AccountVOToDoc(TasksVO accountVO) {			
		Document task = new Document();
		task.append(Constants._ID, UUID.randomUUID().toString());		
		task.append(Constants.TITLE, accountVO.getTitle());
		task.append(Constants.USER_STORY, accountVO.getUserStory());
		task.append(Constants.CREATED_BY, accountVO.getCreatedBy());		
		task.append(Constants.DESCRIPTION, accountVO.getDescription());		
		task.append(Constants.USER_ID, accountVO.getUserId());		
		task.append(Constants.DATE, new Date());
		task.append(Constants.CREATED_DATE, new Date());				
		return task;
	}
	
	public Document AccountVOToDocUpdate(TasksVO userDoc) {	
		Document task = new Document();
		boolean isUpdate = false;	
		
		if(userDoc.getDate()!=null){
			task.append(Constants.DATE,userDoc.getDate());
			isUpdate = true;
		}		
		if(StringUtil.isNotBlank(userDoc.getTitle())){
			task.append(Constants.TITLE, userDoc.getTitle());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getUserStory())){
			task.append(Constants.USER_STORY, userDoc.getUserStory());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getDescription())){
			task.append(Constants.DESCRIPTION,userDoc.getDescription());
			isUpdate = true;
		}		
		if(isUpdate){
			task.append(Constants.UPDATED_DATE, new Date());
			return task;
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
	
	public String getUserStory() {
		return userStory;
	}

	public void setUserStory(String userStory) {
		this.userStory = userStory;
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