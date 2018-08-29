package jmns.app.dao.thoughts;

import java.util.List;

import org.bson.Document;



public interface ThoughtsDAO {

	
	String savePost(String userId, String title,
			String post,List<String> tags,String author);

	Iterable<Document> findAllPost(String date,String endDate,String tags,String title,String description,String createdBy);

	Document getPostById(String id);

	void updatePost(String taskId, String date, String title,
			String description);

	boolean deletePostById(String taskId);

	Iterable<Document> getAllPost(int from, int to,String searchValue,String msgStr,String author);

	void addPostComment(String name, String email, String body, String postId);
}