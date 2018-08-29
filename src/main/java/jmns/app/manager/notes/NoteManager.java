package jmns.app.manager.notes;

import java.util.List;

import org.bson.Document;


public interface NoteManager
{
	
	public String saveNote(String date, String title,
			String description,String createdBy);

	public List<Document> findAllNotes(String date, String endDate,String title,String description, String createdBy);

	public Document getNotesById(String id);

	public void updateNote(String taskId, String date, String title,
			 String description);

	public boolean deleteNoteById(String taskId);
	
}