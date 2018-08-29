package jmns.app.manager.notes;



import java.util.ArrayList;
import java.util.List;

import jmns.app.dao.notes.NoteDAO;
import jmns.app.util.CommonUtil;

import org.bson.Document;



public class NoteManagerImpl implements NoteManager
{

	private NoteDAO noteDAO;

	public NoteDAO getNoteDAO() {
		return noteDAO;
	}

	public void setNoteDAO(NoteDAO noteDAO) {
		this.noteDAO = noteDAO;
	}

	public String saveNote(String date, String title, 
			String description,String createdBy) {
		// TODO Auto-generated method stub
		return noteDAO.saveNote(date, title,  description,createdBy);
	}

	public List<Document> findAllNotes(String date,String endDate,String title,String description,String createdBy) {
		// TODO Auto-generated method stub
		Iterable<Document> taskList = noteDAO.findAllNotes(date,endDate,title,description,createdBy);		
		if(null!=taskList){
			List<Document> userList = new ArrayList<Document>();
			for (Document document : taskList) {
				String de =document.get("description").toString();
				 de =	de.replaceAll("<p>", " ");
				 de =	de.replaceAll("\\r?\\n", " ");	
				 document.put("description", de);
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				userList.add(document);
			}
			return userList;
		}else{
			return null;
		}
	}

	public Document getNotesById(String id) {
		// TODO Auto-generated method stub
		return noteDAO.getNotesById(id);
	}

	public void updateNote(String taskId, String date, String title,
			 String description) {
		// TODO Auto-generated method stub
		noteDAO.updateNote(taskId,date, title, description);
	}
	public boolean deleteNoteById(String taskId) {
		// TODO Auto-generated method stub
		return noteDAO.deleteNoteById(taskId);
	}
	
}