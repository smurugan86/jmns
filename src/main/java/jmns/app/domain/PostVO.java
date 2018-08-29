package jmns.app.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jmns.app.util.Constants;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;

/**
 * @author Murugan
 *
 */
public class PostVO{

	private String id;
	private String title;		
	private String post;
	private String userId;
	private String author;
	private List<String> tags;
	private Date date = new Date();
	private String createdBy;
	private Date createdDate = new Date();
		
	public PostVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);
		this.author = request.getParameter(Constants.USER_STORY);
		this.title = request.getParameter(Constants.TITLE);
		this.createdBy = request.getParameter(Constants.CREATED_BY);		
		this.post = request.getParameter(Constants.DESCRIPTION);
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document AccountVOToDoc(PostVO accountVO) {			
		Document task = new Document();
		task.append(Constants._ID, UUID.randomUUID().toString());		
		task.append(Constants.TITLE, accountVO.getTitle());
		task.append(Constants.AUTHOR, accountVO.getAuthor());
		task.append(Constants.CREATED_BY, accountVO.getCreatedBy());		
		task.append(Constants.POST, accountVO.getPost());		
		task.append(Constants.USER_ID, accountVO.getUserId());		
		task.append(Constants.DATE, new Date());
		task.append(Constants.CREATED_DATE, new Date());				
		return task;
	}
	
	public Document AccountVOToDocUpdate(PostVO userDoc) {	
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
		/*if(StringUtil.isNotBlank(userDoc.getUserStory())){
			task.append(Constants.USER_STORY, userDoc.getUserStory());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getDescription())){
			task.append(Constants.DESCRIPTION,userDoc.getDescription());
			isUpdate = true;
		}*/		
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
	
	

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
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