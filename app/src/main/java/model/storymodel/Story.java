package model.storymodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import database.DBField;

public class Story {
	private int id;
	private String title;
	private String author;
	private String imagePath;
	private String storyText;
	
	private StoryElement backgroundElement;
	private ArrayList<StoryElement> characterElements;
	private ArrayList<StoryElement> objectElements;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	public StoryElement getBackgroundElement() {
		return backgroundElement;
	}

	public void setBackgroundElement(StoryElement backgroundElement) {
		this.backgroundElement = backgroundElement;
	}

	public ArrayList<StoryElement> getCharacterElements() {
		return characterElements;
	}

	public void setCharacterElements(ArrayList<StoryElement> characterElements) {
		this.characterElements = characterElements;
	}

	public ArrayList<StoryElement> getObjectElements() {
		return objectElements;
	}

	public void setObjectElements(ArrayList<StoryElement> objectElements) {
		this.objectElements = objectElements;
	}
	
	public void addCharacterElement(StoryElement characterElement) {
		characterElements.add(characterElement);
	}
	public void addObjectElement(StoryElement objectElement) {
		objectElements.add(objectElement);
	}
	
	public void removeCharacterElement(StoryElement characterElement) {
		for (StoryElement e : characterElements) {
			if (e.getsName().equals(characterElement.getsName())) {
				characterElements.remove(e);
				break;
			}
		}
	}
	public void removeObjectElement(StoryElement objectElement) {
		for (StoryElement e : objectElements) {
			if (e.getsName().equals(objectElement.getsName())) {
				objectElements.remove(e);
				break;
			}
		}
	}
	
	public void modifyCharacterLocation(StoryElement characterElement, int x, int y) {
		for (int i = 0; i < characterElements.size(); i ++) {
			if (characterElements.get(i) == characterElement) {
				characterElements.get(i).setX(x);
				characterElements.get(i).setX(y);
			}
		}
	}
	public void modifyObjectLocation(StoryElement objectElement, int x, int y) {
		for (int i = 0; i < objectElements.size(); i ++) {
			if (objectElements.get(i) == objectElement) {
				objectElements.get(i).setX(x);
				objectElements.get(i).setX(y);
			}
		}
	}

	public void addStory(SQLiteDatabase db, String title, String author, String imagePath, String storyText){
		ContentValues cv = new ContentValues();
		
		cv.put(DBField.COLUMN_STORY_TITLE, title);
		cv.put(DBField.COLUMN_STORY_AUTHOR, author);
		cv.put(DBField.COLUMN_STORY_IMAGEPATH, imagePath);
		cv.put(DBField.COLUMN_STORY_STORYTEXT, storyText);
		
		db.insert(DBField.TABLE_STORY, null, cv);
	}
	
	public void deleteStory(SQLiteDatabase db, Story story){
		
		db.delete(DBField.TABLE_STORY, DBField.COLUMN_STORY_ID + "=" + story.getId(), null);
	}
}
