package jmns.app.manager.thoughts;



import java.util.ArrayList;
import java.util.List;

import jmns.app.dao.thoughts.ThoughtsDAO;
import jmns.app.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;



public class ThoughtsManagerImpl implements ThoughtsManager
{

	private ThoughtsDAO thoughtsDAO;

	

	public ThoughtsDAO getThoughtsDAO() {
		return thoughtsDAO;
	}

	public void setThoughtsDAO(ThoughtsDAO thoughtsDAO) {
		this.thoughtsDAO = thoughtsDAO;
	}

	public String savePost(String userId, String title, 
			String post,List<String> tagsArray,String author) {
		// TODO Auto-generated method stub
		return thoughtsDAO.savePost(userId,title,post,tagsArray,author);
	}

	public List<Document> findAllPost(String date,String endDate,String tags,String title,String post,String userId) {
		// TODO Auto-generated method stub
		Iterable<Document> taskList = thoughtsDAO.findAllPost(date,endDate,tags,title,post,userId);		
		if(null!=taskList){
			List<Document> postList = new ArrayList<Document>();
			for (Document document : taskList) {				 
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				List<String> tagList =  (List<String>) document.get("tags");				 
				String tagss = StringUtils.join(tagList,",");
				document.put("tags",tagss );	
				
				String posts =document.get("post").toString();
				posts =	posts.replaceAll("<p>", " ");
				posts =	posts.replaceAll("\\r?\\n", " ");	
				 document.put("post", posts);
				 
				postList.add(document);
			}
			return postList;
		}else{
			return null;
		}
	}

	public Document getPostById(String id) {
		// TODO Auto-generated method stub
		return thoughtsDAO.getPostById(id);
	}

	public void updatePost(String postId, String title, String post,
			 String tags) {
		// TODO Auto-generated method stub
		thoughtsDAO.updatePost(postId,title, post, tags);
	}
	public boolean deletePostById(String taskId) {
		// TODO Auto-generated method stub
		return thoughtsDAO.deletePostById(taskId);
	}

	public List<Document> getAllPost(int from, int to,String searchValue,String msgStr,String author) {
		// TODO Auto-generated method stub
		Iterable<Document> docs = thoughtsDAO.getAllPost(from,to,searchValue,msgStr,author);
		if(null!=docs){
			List<Document> postList = new ArrayList<Document>();
			for (Document document : docs) {
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				postList.add(document);
			}
			return postList;
		}else{
			return null;
		}
	}

	public void addPostComment(String name, String email, String body,
			String postId) {
		// TODO Auto-generated method stub
		thoughtsDAO.addPostComment(name, email, body, postId);
	}
	
}