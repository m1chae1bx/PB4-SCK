package model.storyworldmodel;

@Deprecated
public class Background implements Element {

	public static final int NTIME = 3;
	public static final String MORNING = "morning";
	public static final String AFTERNOON = "afternoon";
	public static final String EVENING = "evening";
	
	private int id;
	private String name;
	private String imagePath;
	
	private String time;
	private String timeDesc;
	private String bgDesc;
	
	// CONSTRUCTORS
	public Background() {
		
	}	
	public Background(int id, String name, String imagePath) {
		this.id = id;
		this.name = name;
		this.imagePath = imagePath;
	}

	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public String getBGDesc() {
		return bgDesc;
	}
	public void setBGDesc(String bgDesc) {
		this.bgDesc = bgDesc;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTimeDesc() {
		return timeDesc;
	}
	public void setTimeDesc(String timeDesc) {
		this.timeDesc = timeDesc;
	}

//	/**
//	 * Inserts a background in the database
//	 * @param db 		- SQL database
//	 * @param name 		- name of the background
//	 * @param imagePath - file path of the image
//	 */
//	public void addBackground(SQLiteDatabase db, String name, String imagePath){
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(DBField.COLUMN_BACKGROUND_NAME, name);
//		cv.put(DBField.COLUMN_BACKGROUND_IMAGEPATH, imagePath);
//
//		db.insert(DBField.TABLE_BACKGROUND, null, cv);
//
//	}

//	/**
//	 * Inserts an object related to the background in the BGObject table
//	 * @param db		- SQL database
//	 * @param bgID		- background ID
//	 * @param objectID	- object ID
//	 */
//	public void addObject(SQLiteDatabase db, int bgID, int objectID){
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(DBField.COLUMN_BACKGROUND_ID, bgID);
//		cv.put(DBField.COLUMN_BGOBJECT_OBJECTID, objectID);
//
//		db.insert(DBField.TABLE_BGOBJECT, null, cv);
//
//	}
//

	@Override
	public String toString() {
		return name + " " + time;
	}
	
}
