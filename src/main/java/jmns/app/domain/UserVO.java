package jmns.app.domain;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jmns.app.util.CommonUtil;
import jmns.app.util.Constants;

import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;



public class UserVO{
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String textPassword;
	private String status = Constants.ACT;
	private String userRole = Constants.ROLE_USER;
	
	private String verify; 
	private Date date = new Date();
	private Date createdDate = new Date();
	private Date lastLoginDate = new Date();
	
	public UserVO(HttpServletRequest request) {
		super();
		this.id = request.getParameter(Constants._ID);
		this.firstName = request.getParameter(Constants.FIRST_NAME);
		this.lastName = request.getParameter(Constants.LAST_NAME);
		this.email = request.getParameter(Constants.EMAIL);
		this.password = request.getParameter(Constants.PASSWORD);
		this.verify = request.getParameter(Constants.TEXT_VERIFY_PASSWORD);
		this.textPassword = request.getParameter(Constants.PASSWORD);		
	}

	
	public UserVO() {
		// TODO Auto-generated constructor stub
	}


	public Document UserVOToDoc(UserVO userDoc) {			
		Document user = new Document();
		user.append(Constants._ID, UUID.randomUUID().toString());		
		user.append(Constants.FIRST_NAME, userDoc.getFirstName());
		user.append(Constants.LAST_NAME, userDoc.getLastName());
		user.append(Constants.EMAIL, userDoc.getEmail());
		user.append(Constants.PASSWORD, CommonUtil.makePasswordHash(userDoc.getPassword(), Integer.toString(CommonUtil.getRandom().nextInt())));		
		user.append(Constants.TEXT_PASSWORD, userDoc.getTextPassword());
		user.append(Constants.STATUS, Constants.ACT);
		user.append(Constants.USER_ROLE, Constants.ROLE_USER);
		user.append(Constants.DATE, new Date());
		user.append(Constants.CREATED_DATE, new Date());
		user.append(Constants.LAST_LOGIN_DATE, new Date());
		
		return user;
	}
	
	public Document UserVOToDocUpdate(UserVO userDoc) {		
		
		Document user = new Document();
		boolean isUpdate = false;
		
		if(StringUtil.isNotBlank(userDoc.getFirstName())){
			user.append(Constants.FIRST_NAME, userDoc.getFirstName());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getLastName())){
			user.append(Constants.LAST_NAME, userDoc.getLastName());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getPassword())){
			user.append(Constants.PASSWORD, CommonUtil.makePasswordHash(userDoc.getPassword(), Integer.toString(CommonUtil.getRandom().nextInt())));
			user.append(Constants.TEXT_PASSWORD,userDoc.getPassword());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getUserRole())){
			user.append(Constants.USER_ROLE, userDoc.getUserRole());
			isUpdate = true;
		}
		if(StringUtil.isNotBlank(userDoc.getStatus())){
			user.append(Constants.STATUS,userDoc.getStatus());
			isUpdate = true;
		}
		
		if(isUpdate){
			user.append(Constants.UPDATED_DATE, new Date());
			return user;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getTextPassword() {
		return textPassword;
	}


	public void setTextPassword(String textPassword) {
		this.textPassword = textPassword;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
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


	public Date getLastLoginDate() {
		return lastLoginDate;
	}


	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}


	public String getVerify() {
		return verify;
	}


	public void setVerify(String verify) {
		this.verify = verify;
	}
		
}