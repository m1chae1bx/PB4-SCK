package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.charmodel.Relationship;
import model.narratologicalmodel.Theme;
import model.storyplanmodel.Event;
import model.storyworldmodel.Background;
import model.storyworldmodel.Character;
import model.storyworldmodel.Element;
import model.storyworldmodel.StoryWorldOld;

public class SOMEOBJECT { // TODO rename

	/**
	 * Returns all the possible themes for the given background.
	 * @param bg - background
	 * @return an ArrayList containing all the possible themes, or an empty list if there are no possible theme. 
	 */
	public static ArrayList<Theme> getPossibleThemes(Background bg) {
		
		ArrayList<Theme> possibleThemes = new ArrayList<Theme>();
		
		SQLiteDatabase db = DBFactory.sqldb;
		String sql = "SELECT * FROM "+ DBField.TABLE_THEME + " WHERE " + DBField.COLUMN_RELATED_BACKGROUND + " = ?";
		Cursor c = db.rawQuery(sql, new String[] { bg.getName() });

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Theme t = new Theme();
			
			t.setId(c.getInt(c.getColumnIndex(DBField.COLUMN_THEME_ID)));
			t.setMoralLesson(c.getString(c.getColumnIndex(DBField.COLUMN_MORAL_LESSON)));
			t.setRelatedTrait(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_TRAIT)));
			t.setTempObject(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_OBJECT)));
			
			possibleThemes.add(t);
			c.moveToNext();
		 }
		 c.close();
		
		 return possibleThemes;
		 
	}
//
//	/**
//	 * Returns all the possible conflicts for the given theme.
//	 * @param theme
//	 * @return an ArrayList containing all the possible conflicts, or an empty list if there are no possible conflict.
//	 */
	public static ArrayList<Event> getPossibleConflicts(Theme theme) {
//	    commented on 6-22
//		ArrayList<Event> possibleConflicts = new ArrayList<Event>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.CONFLICT_OF, theme.getMoralLesson()});
//		c.moveToFirst();
//
//		while (!c.isAfterLast()) {
//			String conflict = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2));
//
//			sql = "SELECT * FROM " + DBField.TABLE_EVENT +
//					" WHERE " + DBField.COLUMN_POSTCONDITION + " LIKE ? OR " + DBField.COLUMN_EVENT_NAME + " = ?";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {"%" + conflict +"%",conflict});
//			cursor.moveToFirst();
//			while (!cursor.isAfterLast()) {
//
//				Event e = new Event(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_NAME)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_AGENTS))),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_PATIENTS))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_PRECONDITION)),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_POSTCONDITION)),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_INTERACTIONSCORE))),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_IRSCORE))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_TYPE)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_DIALOGUE_TYPE)));
//
//				e.setConflict(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2)));
//				possibleConflicts.add(e);
//				cursor.moveToNext();
//			}
//			c.moveToNext();
//		}
//		 c.close();
//		return possibleConflicts;
		return null; // added on 6-22 temporary
	}
	
//	/**
//	 * Returns all the possible resolutions for the given conflict.
//	 * @param conflict
//	 * @return an ArrayList containing all the possible resolutions, or an empty list if there are no possible resolution.
//	 */
	public static ArrayList<Event> getPossibleResolutions(Event conflict) {
//		commented on 6-22
//        ArrayList<Event> possibleResolutions = new ArrayList<Event>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.HAS_RESOLUTION, conflict.getConflict()});
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			String resolution = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2));
//
//			sql = "SELECT * FROM " + DBField.TABLE_EVENT +
//					" WHERE " + DBField.COLUMN_EVENT_NAME + " LIKE ?";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {"%" + resolution +"%"});
//			cursor.moveToFirst();
//
//			while (!cursor.isAfterLast()) {
//
//				Event e = new Event(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_NAME)),
//						Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_AGENTS))),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_PATIENTS))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_PRECONDITION)),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_POSTCONDITION)),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_INTERACTIONSCORE))),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_IRSCORE))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_TYPE)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_DIALOGUE_TYPE)));
//
//				possibleResolutions.add(e);
//				cursor.moveToNext();
//			}
//			c.moveToNext();
//		}
//		 c.close();
//
//		return possibleResolutions;
		return null; // added on 6-22
	}
	
//	/**
//	 * Returns all the possible events for the given list of actions.
//	 * @param actions
//	 * @return an ArrayList of events that are related to the list of actions, or an empty list if there are no possible events
//	 */
	public static ArrayList<Event> getPossibleEvents(ArrayList<String> actions) {
//		commented on 6-22
//		ArrayList<Event> possibleEvents = new ArrayList<Event>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.CAPABLE_OF, "#character"});
//		c.moveToFirst();
//
//		while (!c.isAfterLast()) {
//			String action = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2));
//
//			sql = "SELECT * FROM " + DBField.TABLE_EVENT +
//					" WHERE " + DBField.COLUMN_EVENT_NAME + " LIKE ? ORDER BY RANDOM() ";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {"%" + action +"%"});
//			cursor.moveToFirst();
//
//			while (!cursor.isAfterLast()) {
//				Event e = new Event(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_NAME)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_AGENTS))),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_PATIENTS))),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_PRECONDITION)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_POSTCONDITION)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_INTERACTIONSCORE))),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_IRSCORE))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_TYPE)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_DIALOGUE_TYPE)));
//				possibleEvents.add(e);
//				cursor.moveToNext();
//			}
//			cursor.close();
//			c.moveToNext();
//		}
//		 c.close();
//		return possibleEvents;
		return null; // added on 6-22
	}
	
//	/**
//	 * Checks if characters can perform the selected action.
//	 * @param selectedEvent
//	 * @return true if characters can perform the action/event. Otherwise, false.
//	 */
//	@Deprecated
	public static boolean isCapableOf(Event selectedEvent) {
//        commented on 6-22
//		if(selectedEvent == null) {
//			return true;
//		}
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT count(*) FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ? AND " + DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.CAPABLE_OF, "#character", selectedEvent.getAction()});
//
//		c.moveToFirst();
//		int count = c.getInt(0);
//		 c.close();
//
//		if(count != 0) {
//			return true;
//		}
//
		return false;
	}
	
	public static Character getCharacter(int id) {
		
		Character charStickers = new Character();
		SQLiteDatabase db = DBFactory.sqldb;

		String sql = "SELECT * FROM "+ DBField.TABLE_CHARACTER + " WHERE " + DBField.COLUMN_CHAR_ID + " = ?";

		Cursor c = db.rawQuery(sql, new String[] { id + "" });
		
		c.moveToFirst();
//			commented on 6-20 // todo
//			charStickers = new Character(c.getInt(c.getColumnIndex(DBField.COLUMN_CHAR_ID)),
//										c.getString(c.getColumnIndex(DBField.COLUMN_CHAR_NAME)),
//										c.getInt(c.getColumnIndex(DBField.COLUMN_CHAR_GENDER)),
//										c.getString(c.getColumnIndex(DBField.COLUMN_CHAR_IMAGEPATH)));
//
			
			 c.close();
		
		return charStickers;
	}
	
	
	public static Relationship getRelationshipsOf(int from, int to) {
	
		int score = 0;
		String relationship = ""; 
		SQLiteDatabase db = DBFactory.sqldb;

		String sql = "SELECT "+ DBField.COLUMN_SCORE + ", " +DBField.COLUMN_RELATIONSHIP + " FROM "+ DBField.TABLE_IR + 
						" WHERE " + DBField.COLUMN_FROM + " = ? AND " + DBField.COLUMN_TO + " = ?";

		Cursor c = db.rawQuery(sql, new String[] {Integer.toString(from), Integer.toString(to)});
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			
			score = c.getInt(c.getColumnIndex(DBField.COLUMN_SCORE));
			relationship = c.getString(c.getColumnIndex(DBField.COLUMN_RELATIONSHIP));
			c.moveToNext();
		}

//		c.deactivate();
		 c.close();
		
		
		return  new Relationship(getCharacter(to), relationship, score);
	}
	
	
	public static ArrayList<String> getTraits(int charID){
		ArrayList<String> traits = new ArrayList<>();
		
		SQLiteDatabase db = DBFactory.db.getReadableDatabase();

		String sql = "SELECT "+ DBField.COLUMN_TRAIT +" FROM "+ DBField.TABLE_TRAIT + 
						" WHERE " + DBField.COLUMN_CHAR_ID + " = ?";

		Cursor c = db.rawQuery(sql, new String[] {Integer.toString(charID) });
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			
			traits.add( c.getString(c.getColumnIndex(DBField.COLUMN_TRAIT)) );
		
			c.moveToNext();
		}
		 c.close();
		
		return traits;
		
		
	}

	public static ArrayList<String> getIsA(String concept) {
//		commented on 6-22
//		ArrayList<String> propertyList = new ArrayList<String>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT1 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.IS_A, concept});
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			propertyList.add(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1)));
//			c.moveToNext();
//		}
//
//		 c.close();
//
//		return propertyList;
		return null; // added on 6-22
	}
	
	
	public static ArrayList<String> getPropertyOf(String concept1) {
//		commented on 6-22
//		ArrayList<String> propertyList = new ArrayList<String>();
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//					" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//		Cursor	c = db.rawQuery(sql, new String[] { SemanticRelation.HAS_PROPERTY, concept1});
//
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			propertyList.add(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2)));
//			c.moveToNext();
//		}
//
//		 c.close();
//
//		return propertyList;
		return null; // added on 6-22
	}
	
	public static ArrayList<String> getRelatedTime(Background bg) {
//		commented on 6-22
//        ArrayList<String> relatedTimeList = new ArrayList<String>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT1 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.IS_A, "time"});
//		c.moveToFirst();
//
//		while (!c.isAfterLast()) {
//			String time = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1));
//
//			sql = "SELECT * FROM " + DBField.TABLE_SEMANTIC +
//					" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {SemanticRelation.HAS_PROPERTY, bg.getsName(), time});
//			cursor.moveToFirst();
//			while (!cursor.isAfterLast()) {
//				String s = cursor.getString(cursor.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2));
//				relatedTimeList.add(s);
//				cursor.moveToNext();
//			}
//			c.moveToNext();
//		}
//		 c.close();
//		return relatedTimeList;
		return null; // added on 6-22
	}
	
	public static ArrayList<String> getRelatedTimeDesc(String time) {	
		return getPropertyOf(time);
	}
	
	public static ArrayList<String> getRelatedBGDesc(StoryWorldOld storyworld) {
		ArrayList<String> relatedBGDescList = getPropertyOf(storyworld.getBackground().getName());
		ArrayList<String> timeList = getRelatedTime(storyworld.getBackground());
		
		for(String time : timeList) {
			relatedBGDescList.remove(time);
		}
		
		return relatedBGDescList;
	}
	
	public static boolean checkPassiveSemRep(String relation, String concept1, String concept2) {
		
		SQLiteDatabase db = DBFactory.sqldb;
		String sql = "SELECT * FROM "+ DBField.TABLE_SEMANTIC + 
						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
				DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ? AND " +
				DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
		Cursor c = db.rawQuery(sql, new String[] {relation, concept1, concept2});
		
		int count = c.getCount();
		 c.close();
		return count > 0;

	}
	
	public static ArrayList<Event> getPossibleSupportingGoals(Event maingoal, String relationship) {
//        commented on 6-22
//		ArrayList<Event> possibleEvents = new ArrayList<Event>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//		String sql = "SELECT CONCEPT1 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c;
//		if(relationship.equalsIgnoreCase(Relationship.FRIEND)) //FRIEND
//			c = db.rawQuery(sql, new String[] { SemanticRelation.MOTIVATED_BY_GOAL, maingoal.getAction()});
//		else // ENEMEY
//			c = db.rawQuery(sql, new String[] { SemanticRelation.OBSTRUCTED_BY, maingoal.getAction()});
//
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			String action = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1));
//
//			sql = "SELECT * FROM " + DBField.TABLE_EVENT +
//					" WHERE " + DBField.COLUMN_EVENT_NAME + " LIKE ? ORDER BY RANDOM() ";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {"%" + action +"%"});
//			cursor.moveToFirst();
//
//			while (!cursor.isAfterLast()) {
//				Event e = new Event(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_NAME)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_AGENTS))),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_PATIENTS))),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_PRECONDITION)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_POSTCONDITION)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_INTERACTIONSCORE))),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_IRSCORE))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_TYPE)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_DIALOGUE_TYPE)));
//
//				possibleEvents.add(e);
//				cursor.moveToNext();
//			}
//			c.moveToNext();
//		}
//
//		 c.close();
//		return possibleEvents;
		return null; // added on 6-22
	}

	public static ArrayList<String> getRelatedActions(Element element) {
//		commented on 6-22
//		ArrayList<String> actionList = new ArrayList<String>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE (" + DBField.COLUMN_SEMANTIC_RELATION + " = ? OR " +
//									DBField.COLUMN_SEMANTIC_RELATION + " = ? OR " +
//									DBField.COLUMN_SEMANTIC_RELATION + " = ?) AND " +
//									DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.CAPABLE_OF,
//													SemanticRelation.CAPABLE_OF_RECEIVING_ACTION,
//													SemanticRelation.USED_FOR,
//													element.getsName()});
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			actionList.add(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2)));
//			c.moveToNext();
//		}
//
//		 c.close();
//
//		return actionList;
		return null; // added on 6-22
	}
	
	
	
	public static String getCapableOfReceivingAction(String action){
//		commented on 6-22
//        SQLiteDatabase db = DBFactory.sqldb;
//		String sql = "SELECT " + DBField.COLUMN_SEMANTIC_CONCEPT1 +" FROM "+ DBField.TABLE_SEMANTIC + " WHERE " + DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ? AND " + DBField.COLUMN_SEMANTIC_RELATION + "  = ?  ORDER BY RANDOM() LIMIT 1";
//		Cursor c = db.rawQuery(sql, new String[] { action, SemanticRelation.CAPABLE_OF_RECEIVING_ACTION});
//
//		c.moveToFirst();
//		String concept= "";
//		while (!c.isAfterLast()) {
//
//
//			concept = c.getString((c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1)));
//
//			c.moveToNext();
//		 }
//		 c.close();
//
//		 return concept;
		return null; // added on 6-22
	}
	
	public static ArrayList<Event> getSubEvents(Event mainAction) {
//		commented on 6-22
//		ArrayList<Event> possibleEvents = new ArrayList<Event>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT2 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE " + DBField.COLUMN_SEMANTIC_RELATION + " = ? AND " +
//							DBField.COLUMN_SEMANTIC_CONCEPT1 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.HAS_SUBEVENT, mainAction.getAction()});
//		c.moveToFirst();
//
//		while (!c.isAfterLast()) {
//			String action = c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT2));
//
//			sql = "SELECT * FROM " + DBField.TABLE_EVENT +
//					" WHERE " + DBField.COLUMN_EVENT_NAME + " LIKE ? ORDER BY RANDOM() ";
//
//			Cursor cursor = db.rawQuery(sql, new String[] {"%" + action +"%"});
//			cursor.moveToFirst();
//
//			while (!cursor.isAfterLast()) {
//				Event e = new Event(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_NAME)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_AGENTS))),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_MAX_PATIENTS))),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_PRECONDITION)),
//							cursor.getString(cursor.getColumnIndex(DBField.COLUMN_POSTCONDITION)),
//							Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_INTERACTIONSCORE))),
//						 	Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBField.COLUMN_IRSCORE))),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_EVENT_TYPE)),
//						 	cursor.getString(cursor.getColumnIndex(DBField.COLUMN_DIALOGUE_TYPE)));
//				possibleEvents.add(e);
//				cursor.moveToNext();
//			}
//			c.moveToNext();
//		}
//		c.close();
//		return possibleEvents;
		return null;
	}
	
	public static ArrayList<String> getObjectsCapableOfReceiving(String action) {
//		commented on 6-22
//		ArrayList<String> objectList = new ArrayList<String>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//
//		String sql = "SELECT CONCEPT1 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE (" + DBField.COLUMN_SEMANTIC_RELATION + " = ?) AND " +
//									DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.CAPABLE_OF_RECEIVING_ACTION,
//													action});
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			objectList.add(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1)));
//			c.moveToNext();
//		}
//
//		c.close();
//
//		return objectList;
		return null; // added on 6-22
	}
	
	public static ArrayList<String> getObjectsUsedFor(String action) {
//		commented on 6-22
//		ArrayList<String> objectList = new ArrayList<String>();
//
//		SQLiteDatabase db = DBFactory.sqldb;
//		String sql = "SELECT CONCEPT1 FROM "+ DBField.TABLE_SEMANTIC +
//						" WHERE (" + DBField.COLUMN_SEMANTIC_RELATION + " = ?) AND " +
//									DBField.COLUMN_SEMANTIC_CONCEPT2 + " = ?";
//
//		Cursor c = db.rawQuery(sql, new String[] { SemanticRelation.USED_FOR,
//													action});
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			objectList.add(c.getString(c.getColumnIndex(DBField.COLUMN_SEMANTIC_CONCEPT1)));
//			c.moveToNext();
//		}
//
//		c.close();
//
//		return objectList;
		return null; // added on 6-22
	}
	
	
}
