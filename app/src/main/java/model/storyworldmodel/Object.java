package model.storyworldmodel;

import java.util.ArrayList;

import database.SOMEOBJECT;
import model.storyplanmodel.Agent;
import model.storyplanmodel.Patient;

@Deprecated
public class Object implements Agent, Patient, Element {

	private int id;
	private String name;
	private String type;
	private String imagePath;
	
	private ArrayList<String> relatedActions; //possible actions done to or done by
	
	// CONSTRUTORS
	public Object() {
		
	}
	public Object(String name) {
		this.name = name;
		relatedActions = SOMEOBJECT.getRelatedActions(this);
	}
	public Object(int id, String name, String type, String imagePath) {
		this.id = id;
		this.name = name;
		this.type = type; // todo check if this is needed
		this.imagePath = imagePath;
		
		//retrieve actions from db
		relatedActions = SOMEOBJECT.getRelatedActions(this);
		
	}
	
	public int getID() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public ArrayList<String> getRelatedActions() {
		return relatedActions;
	}
	public void setRelatedActions(ArrayList<String> relatedActions) {
		this.relatedActions = relatedActions;
	}
	public void addRelatedAction(String relatedAction) {
		if(relatedActions == null)
			relatedActions = new ArrayList<>();
		relatedActions.add(relatedAction);
	}
	public boolean isCapableOf(String action) {
		return relatedActions != null && relatedActions.contains(action);
	}
	
	/**
	 * Inserts an object in the database
	 * @param db - SQL database
	 * @param name - name of the object
	 * @param type - 
	 * @param imagePath - file path of the iamge
	 */
//	public void addObject(SQLiteDatabase db, String name, String type, String imagePath){
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(DBField.COLUMN_OBJECT_NAME, name);
//		cv.put(DBField.COLUMN_OBJECT_TYPE, type);
//		cv.put(DBField.COLUMN_OBJECT_IMAGEPATH, imagePath);
//
//		db.insert(DBField.TABLE_OBJECT, null, cv);
//	}

	/**
	 * Returns an object with its information from the database
	 * @param name
	 * @return
	 */
	public Object getObject(String name) {
//		commented on 6-21
//		SQLiteDatabase db = DBFactory.db.getReadableDatabase();
//
//		String sql = "SELECT * FROM " + DBField.TABLE_OBJECT + " WHERE + " + DBField.COLUMN_OBJECT_NAME + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] {name});
//		 c.moveToFirst();
//
//		 Object t = new Object();
//		    c.moveToFirst();
//		    while (!c.isAfterLast()) {
//
//			 t.setnLocationId(c.getInt(c.getColumnIndex(DBField.COLUMN_OBJECT_ID)));
//			 //t.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_NAME))); // commented on 6-21
//			 t.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)));
//			 //t.setTempObject(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_OBJECT)));
//			 c.moveToNext();
//		 }
//		 return t;
		return null; //
	}
	
	@Override
	public String toString() {
		return name;
	}

}
