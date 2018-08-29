package jmns.app.manager.task;



import java.util.ArrayList;
import java.util.List;

import jmns.app.dao.task.TaskDAO;
import jmns.app.util.CommonUtil;

import org.bson.Document;



public class TaskManagerImpl implements TaskManager
{

	private TaskDAO taskDAO;
	
	
	

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public String saveTask(String date, String title, String userStory,
			String description,String createdBy) {
		// TODO Auto-generated method stub
		return taskDAO.saveTask(date, title, userStory , description,createdBy);
	}

	public List<Document> findAllTask(String date,String endDate,String title,String userStory,String description,String createdBy) {
		// TODO Auto-generated method stub
		Iterable<Document> taskList = taskDAO.findAllTask(date,endDate,title,userStory,description,createdBy);		
		if(null!=taskList){
			List<Document> userList = new ArrayList<Document>();
			for (Document document : taskList) {
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				
				 String de =document.get("description").toString();
				 de =	de.replaceAll("<p>", " ");
				 de =	de.replaceAll("\\r?\\n", " ");	
				 document.put("description", de);
				 
				userList.add(document);
			}
			return userList;
		}else{
			return null;
		}
	}

	public Document getTaskById(String id) {
		// TODO Auto-generated method stub
		return taskDAO.getTaskById(id);
	}

	public void updateTask(String taskId, String date, String title,
			String userStory, String description) {
		// TODO Auto-generated method stub
		taskDAO.updateTask(taskId,date, title, userStory,description);
	}
	public boolean deleteTaskById(String taskId) {
		// TODO Auto-generated method stub
		return taskDAO.deleteTaskById(taskId);
	}
	
}