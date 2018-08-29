package jmns.app.manager.thoughts;

import java.util.List;

import org.bson.Document;


public interface ThoughtsManager
{
	
	public String savePost(String userId, String title,
			String post,List<String> tagsArray,String author);

	public List<Document> findAllPost(String date,String endDate,String tags,String title,String description, String userId);

	public Document getPostById(String id);

	public void updatePost(String taskId, String date, String title,
			 String description);

	public boolean deletePostById(String taskId);

	public List<Document> getAllPost(int from, int to,String searchValue,String msgStr,String author);

	public void addPostComment(String name, String email, String body,
			String postId);
	
}