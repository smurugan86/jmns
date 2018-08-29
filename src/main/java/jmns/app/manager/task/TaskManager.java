package jmns.app.manager.task;

import java.util.List;

import org.bson.Document;


public interface TaskManager
{
	
	public String saveTask(String date, String title, String userStory,
			String description,String createdBy);

	public List<Document> findAllTask(String date, String endDate,String title,String userStory,String description, String createdBy);

	public Document getTaskById(String id);

	public void updateTask(String taskId, String date, String title,
			String userStory, String description);

	public boolean deleteTaskById(String taskId);
	
}