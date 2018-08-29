package jmns.app.dao.notes;

import org.bson.Document;



public interface NoteDAO {

	
	String saveNote(String date, String title,
			String description,String createdBy);

	Iterable<Document> findAllNotes(String date,String endDate,String title,String description,String createdBy);

	Document getNotesById(String id);

	void updateNote(String taskId, String date, String title,
			String description);

	boolean deleteNoteById(String taskId);
}