package process.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import database.DBFactory;
import database.DBField;
import model.storymodel.Story;
import model.storymodel.StoryElement;

public class DataRetriever { // todo rename, specify what kind of data does it retrieve
	
	public ArrayList<Story> retrieveStories() {
		
		ArrayList<Story> storyList = new ArrayList();
		
		SQLiteDatabase db = DBFactory.sqldb;

		String sql = "SELECT * FROM "+ DBField.TABLE_STORY;

		Cursor c = db.rawQuery(sql, new String[] {});
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Story story = new Story();
    	    story.setId(c.getInt(c.getColumnIndex(DBField.COLUMN_STORY_ID)));
    	    story.setTitle(c.getString(c.getColumnIndex(DBField.COLUMN_STORY_TITLE)));
    	    story.setAuthor(c.getString(c.getColumnIndex(DBField.COLUMN_STORY_AUTHOR)));
    	    story.setImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_STORY_IMAGEPATH)));
    	    story.setStoryText(c.getString(c.getColumnIndex(DBField.COLUMN_STORY_STORYTEXT)));
    	    
    	    storyList.add(story);
			c.moveToNext();
		}
		c.close();
		
		return storyList;
	}

	// edited starting on 6-20
	public ArrayList<StoryElement> retrieveBackgrounds(Context context) { // TODO retrieve name and other details from KB

		ArrayList<StoryElement> backgroundStickers = new ArrayList();
		SQLiteDatabase db = DBFactory.sqldb;

		String sql = "SELECT * FROM " + DBField.TABLE_BACKGROUND + " INNER JOIN " + DBField.TABLE_CONCEPT + " ON " + DBField.TABLE_BACKGROUND + "." + DBField.COLUMN_BACKGROUND_CONCEPTID + " = " + DBField.TABLE_CONCEPT + "." + DBField.COLUMN_CONCEPT_ID;

		Cursor c = db.rawQuery(sql, new String[]{});

		c.moveToFirst();
		while (!c.isAfterLast()) {
			StoryElement element = new StoryElement();
			System.out.println("DEBUG: id = " + c.getInt(c.getColumnIndex(DBField.COLUMN_BACKGROUND_ID)));
			System.out.println("DEBUG: name = " + c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME)));
			System.out.println("DEBUG: imagepath = " + c.getString(c.getColumnIndex(DBField.COLUMN_BACKGROUND_IMAGEPATH)));
			element.setnId(c.getInt(c.getColumnIndex(DBField.COLUMN_BACKGROUND_ID)));
			element.setnConceptId(c.getInt(c.getColumnIndex(DBField.COLUMN_BACKGROUND_CONCEPTID)));
			element.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME)));
			element.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_BACKGROUND_IMAGEPATH)));

//          commented on 6-20
//    	    element.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_BACKGROUND_NAME)));
//    	    element.setsDescription("<description here>");

			int imageResource = context.getResources().getIdentifier(c.getString(c.getColumnIndex(DBField.COLUMN_BACKGROUND_IMAGEPATH)), null, context.getPackageName());
			element.setImageResource(imageResource);

			backgroundStickers.add(element);
			c.moveToNext();
		}
		c.close();

		return backgroundStickers;
	}

	// edited starting on 6-20
	public ArrayList<StoryElement> retrieveChars(Context context) { // TODO retrieve name and other details from KB
		
		ArrayList<StoryElement> charStickers = new ArrayList();
		
		SQLiteDatabase db = DBFactory.sqldb;

//		commented on 6-20
//		String sql = "SELECT * FROM "+ DBField.TABLE_CHARACTER;
		String sql = "SELECT * FROM " + DBField.TABLE_CHARACTER + " INNER JOIN " + DBField.TABLE_CONCEPT + " ON " + DBField.TABLE_CHARACTER + "." + DBField.COLUMN_CHAR_CONCEPTID + " = " + DBField.TABLE_CONCEPT + "." + DBField.COLUMN_CONCEPT_ID;

		Cursor c = db.rawQuery(sql, new String[] {});
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			StoryElement element = new StoryElement();
			element.setnId(c.getInt(c.getColumnIndex(DBField.COLUMN_CHAR_ID)));
			element.setnConceptId(c.getInt(c.getColumnIndex(DBField.COLUMN_CHAR_CONCEPTID)));
			element.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME)));
			element.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_CHAR_IMAGEPATH)));

			int imageResource = context.getResources().getIdentifier(c.getString(c.getColumnIndex(DBField.COLUMN_CHAR_IMAGEPATH)), null, context.getPackageName());
    	    element.setImageResource(imageResource);

			System.out.println("DEBUG: " + element.toString());
			charStickers.add(element);
			c.moveToNext();
		}
		c.close();
		
		return charStickers;
	}

	// edited starting on 6-20
	public ArrayList<StoryElement> retrieveObjs(Context context) { // TODO retrieve name and other details from KB
		
		ArrayList<StoryElement> objStickers = new ArrayList();
		
		SQLiteDatabase db = DBFactory.sqldb;

//		commented on 6-20
//		String sql = "SELECT * FROM "+ DBField.TABLE_OBJECT;
		String sql = "SELECT * FROM " + DBField.TABLE_OBJECT + " INNER JOIN " + DBField.TABLE_CONCEPT + " ON " + DBField.TABLE_OBJECT + "." + DBField.COLUMN_OBJECT_CONCEPTID + " = " + DBField.TABLE_CONCEPT + "." + DBField.COLUMN_CONCEPT_ID;

		Cursor c = db.rawQuery(sql, new String[] {});
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			StoryElement element = new StoryElement();
			element.setnId(c.getInt(c.getColumnIndex(DBField.COLUMN_OBJECT_ID)));
			element.setnConceptId(c.getInt(c.getColumnIndex(DBField.COLUMN_OBJECT_CONCEPTID)));
			element.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME)));
			element.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)));

			int imageResource = context.getResources().getIdentifier(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)), null, context.getPackageName());
    	    element.setImageResource(imageResource);
			
    	    objStickers.add(element);
			c.moveToNext();
		}
		c.close();
		
		return objStickers;
	}

	// edited starting on 6-20
	public ArrayList<StoryElement> retrieveObjsOnBg(Context context, StoryElement background) {
		
		ArrayList<StoryElement> objStickers = new ArrayList();
		
		SQLiteDatabase db = DBFactory.sqldb;

		String sql = "SELECT * FROM "+ DBField.TABLE_BGOBJECT + " LEFT OUTER JOIN " + DBField.TABLE_OBJECT + " ON (" + DBField.COLUMN_BGOBJECT_OBJECTID + " = " + DBField.COLUMN_OBJECT_ID + ") WHERE " + DBField.COLUMN_BACKGROUND_ID + " = ?";

		Cursor c = db.rawQuery(sql, new String[]{Integer.toString(background.getnId())});
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			StoryElement element = new StoryElement();
			element.setnId(c.getInt(c.getColumnIndex(DBField.COLUMN_BGOBJECT_OBJECTID)));
			element.setnConceptId(c.getInt(c.getColumnIndex(DBField.COLUMN_OBJECT_CONCEPTID)));
			element.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)));

			int imageResource = context.getResources().getIdentifier(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)), null, context.getPackageName());
    	    element.setImageResource(imageResource);

    	    objStickers.add(element);
			c.moveToNext();
		}
		c.close();

		for (StoryElement e : objStickers) {
			sql = "SELECT * FROM " + DBField.TABLE_CONCEPT + " WHERE " + DBField.COLUMN_CONCEPT_ID + " = ?";
			c = db.rawQuery(sql, new String[]{Integer.toString(e.getnConceptId())});
			c.moveToFirst();
			if (!c.isAfterLast())
				e.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME)));
			c.close();
		}
		
		return objStickers;
	}
}
