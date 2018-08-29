package jmns.app.dao.task;

import org.bson.Document;



public interface TaskDAO {

	
	String saveTask(String date, String title,String userStory,
			String description,String createdBy);

	Iterable<Document> findAllTask(String date,String endDate,String title,String userStory,String description,String createdBy);

	Document getTaskById(String id);

	void updateTask(String taskId, String date, String title, String userStory,
			String description);

	boolean deleteTaskById(String taskId);
}