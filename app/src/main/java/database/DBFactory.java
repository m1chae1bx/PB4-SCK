package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBFactory {
	public static DBHelper db;
	public static SQLiteDatabase sqldb; 
	public static void instantiateDBHelper(Context c){
		db = new DBHelper(c);
		sqldb = db.getWritableDatabase();
	}
}
