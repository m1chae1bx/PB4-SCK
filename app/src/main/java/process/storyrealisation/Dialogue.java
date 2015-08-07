package process.storyrealisation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import database.DBFactory;
import database.DBField;

public class Dialogue {
	public static final int SINGULAR =1;
	public static final int PLURAL =2;
	public static final String EMOTION ="emotion";
	public static final String NEGOTIATION ="negotiation";
	public static final String PERSUASION ="persuasion";
	public static final String RESOLUTION ="resolution";
	public static final String INTRODUCTION ="introduction";
	public static final String DELIBERATION ="deliberation";

	private int id;
	private String dialogue;
	private String type;
	private int isSingular;

	public static String getDialogue(String type, int isSingular) {

		SQLiteDatabase db = DBFactory.sqldb;
		String sql = "SELECT " + DBField.COLUMN_DIALOGUE + " FROM " + DBField.TABLE_DIALOGUE + " WHERE " + DBField.COLUMN_DIALOGUE_TYPE + " = ? AND " + DBField.COLUMN_DIALOGUE_ISSINGULAR + " =? " + " ORDER BY RANDOM() LIMIT 1";
		Cursor c = db.rawQuery(sql, new String[]{type, Integer.toString(isSingular)});

		c.moveToFirst();
		String dialogue = "";
		while (!c.isAfterLast()) {


			dialogue = c.getString((c.getColumnIndex(DBField.COLUMN_DIALOGUE)));

			c.moveToNext();
		}
		c.close();

		return dialogue;

	}

	public static String getDialogue(String type) {
		if (type == null)
			return "";
		else {
			SQLiteDatabase db = DBFactory.sqldb;
			String sql = "SELECT " + DBField.COLUMN_DIALOGUE + " FROM " + DBField.TABLE_DIALOGUE + " WHERE " + DBField.COLUMN_DIALOGUE_TYPE + " = ? " + " ORDER BY RANDOM() LIMIT 1";
			Cursor c = db.rawQuery(sql, new String[]{type});

			c.moveToFirst();
			String dialogue = "";
			while (!c.isAfterLast()) {


				dialogue = c.getString((c.getColumnIndex(DBField.COLUMN_DIALOGUE)));

				c.moveToNext();
			}
			c.close();

			return dialogue;
		}

	}

	public int getIsSingular() {
		return isSingular;
	}

	public void setIsSingular(int isSingular) {
		this.isSingular = isSingular;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDialogue() {
		return dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void addDialogue(SQLiteDatabase db, String dialogue, String type, int issingular){

		ContentValues cv = new ContentValues();

		cv.put(DBField.COLUMN_DIALOGUE, dialogue);
		cv.put(DBField.COLUMN_DIALOGUE_TYPE, type);
		cv.put(DBField.COLUMN_DIALOGUE_ISSINGULAR, issingular);

		db.insert(DBField.TABLE_DIALOGUE, null, cv);


	}
	
}
