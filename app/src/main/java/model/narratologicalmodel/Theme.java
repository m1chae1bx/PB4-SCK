package model.narratologicalmodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import database.DBFactory;
import database.DBField;
import model.storyworldmodel.Background;
import model.storyworldmodel.Object;
public class Theme {

	private int id;

	private String moralLesson;
	private Background relatedBackground;
	private Object relatedItem;
	private String relatedTrait;
	
	private String tempObject;

	public Theme() {

	}

	public Theme(String moralLesson, Background relatedBackground, Object relatedItem, String relatedTrait) {
		this.moralLesson = moralLesson;
		this.relatedBackground = relatedBackground;
		this.relatedItem = relatedItem;
		this.relatedTrait = relatedTrait;
	}

	public String getTempObject() {
		return tempObject;
	}

	public void setTempObject(String tempObject) {
		this.tempObject = tempObject;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMoralLesson() {
		return moralLesson;
	}

	public void setMoralLesson(String moralLesson) {
		this.moralLesson = moralLesson;
	}

	public Background getRelatedBackground() {
		return relatedBackground;
	}

	public void setRelatedBackground(Background relatedBackground) {
		this.relatedBackground = relatedBackground;
	}

	public Object getRelatedItem() {
		return relatedItem;
	}

	public void setRelatedItem(Object relatedItem) {
		this.relatedItem = relatedItem;
	}

	public String getRelatedTrait() {
		return relatedTrait;
	}

	public void setRelatedTrait(String relatedTrait) {
		this.relatedTrait = relatedTrait;
	}
	
	public void addTheme(SQLiteDatabase db, String moralLesson, String bg, String item, String trait) {
		
		ContentValues cv = new ContentValues();
		
		cv.put(DBField.COLUMN_MORAL_LESSON, moralLesson);
		cv.put(DBField.COLUMN_RELATED_BACKGROUND, bg);
		cv.put(DBField.COLUMN_RELATED_OBJECT, item);
		cv.put(DBField.COLUMN_RELATED_TRAIT, trait);
		
		db.insert(DBField.TABLE_THEME, null, cv);
	}
	
	public Theme getTheme(int id) {
		
		SQLiteDatabase db = DBFactory.db.getReadableDatabase();
		
		String sql = "SELECT * FROM " + DBField.TABLE_THEME + " WHERE "+ DBField.COLUMN_THEME_ID + " = " + id;
		
		Cursor c = db.rawQuery(sql, null);
		 c.moveToFirst();

		 Theme t = new Theme();
		    c.moveToFirst();
		    while (!c.isAfterLast()) {
			 
			 
		 	t.setId(c.getInt(c.getColumnIndex(DBField.COLUMN_THEME_ID)));
			 t.setMoralLesson(c.getString(c.getColumnIndex(DBField.COLUMN_MORAL_LESSON)));
			 t.setRelatedTrait(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_TRAIT)));
			 
			 Object relatedObject = new Object();
			
			 t.setRelatedItem(relatedObject.getObject(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_OBJECT))));

			 c.moveToNext();
		 }
		 return t;
	}
}
