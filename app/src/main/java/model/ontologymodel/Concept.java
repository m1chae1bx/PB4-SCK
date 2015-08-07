package model.ontologymodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import database.DBField;

@Deprecated
public class Concept {

	public static final String NOUN = DBField.TABLE_NOUN;
	public static final String PRONOUN = DBField.TABLE_PRONOUN;
	public static final String VERB = DBField.TABLE_VERB;
	public static final String ADJECTIVE = DBField.TABLE_ADJECTIVE;
	public static final String ADVERB = DBField.TABLE_ADVERB;
	public static final String PREPOSITION = DBField.TABLE_PREPOSITION;
	public static final String CONJUNCTION = DBField.TABLE_CONJUNCTION;
	
	public static final int MALE = 0;
	public  static final int FEMALE = 1;
	
	public static final int SINGULAR = 1;
	public static final int NOT_SINGULAR = 0;
	
	private int id;
	private String name;
	
	public Concept() {
		
	}
	
	public Concept(int id, String name) {
		this.id = id;
		this.name = name;
	}
	

	/**
	 * Inserts a concept in the database. 
	 * Inserts it in the Concept table and Verb, Adjective, Adverb, Preposition, or Conjunction Table.
	 * @param db			- SQL database
	 * @param conceptName
	 * @param partOfSpeech
	 * @param typeOrTense
	 */
	public void addConcept(SQLiteDatabase db,String conceptName, String ontology, String partOfSpeech, String typeOrTense){

		ContentValues cv = new ContentValues();
		
		cv.put(DBField.COLUMN_CONCEPT_NAME, conceptName);
//		cv.put(DBField.COLUMN_CONCEPT_ONTOLOGY, ontology);
//		cv.put(DBField.COLUMN_CONCEPT_PARTOFSPEECH, partOfSpeech);

		long id = db.insert(DBField.TABLE_CONCEPT, null, cv);
		
		cv.clear();
		cv.put(DBField.COLUMN_CONCEPT_ID, (int) id);
		
		String table ="";
		
		if(partOfSpeech.equals(VERB)){
			table = DBField.TABLE_VERB;
			cv.put(DBField.COLUMN_VERB_TENSE, typeOrTense);
		}
		else if(partOfSpeech.equals(ADJECTIVE)){
			table = DBField.TABLE_ADJECTIVE;
			cv.put(DBField.COLUMN_ADJECTIVE_TYPE, typeOrTense);
		}
		else if(partOfSpeech.equals(ADVERB)){
			table = DBField.TABLE_ADVERB;
			cv.put(DBField.COLUMN_ADVERB_TYPE, typeOrTense);
		}
		else if(partOfSpeech.equals(PREPOSITION)){
			table = DBField.TABLE_PREPOSITION;
			cv.put(DBField.COLUMN_PREPOSITION_TYPE, typeOrTense);
		}
		else if(partOfSpeech.equals(CONJUNCTION)){
			table = DBField.TABLE_CONJUNCTION;
			cv.put(DBField.COLUMN_CONJUNCTION_TYPE, typeOrTense);
		}
		
		db.insert(table, null, cv);
		
	}
	
	/**
	 * Inserts the concept in the Concept Table and in the Noun Table.
	 * @param db			- SQL database
	 * @param conceptName
	 * @param partOfSpeech
	 * @param type - WHAT IS THIS TYPE???
	 * @param isSingular
	 */
	public void addConcept(SQLiteDatabase db,String conceptName, String ontology, String partOfSpeech, String type, int isSingular){
		
		ContentValues cv = new ContentValues();
		
		cv.put(DBField.COLUMN_CONCEPT_NAME, conceptName);
//		cv.put(DBField.COLUMN_CONCEPT_ONTOLOGY, ontology);
//		cv.put(DBField.COLUMN_CONCEPT_PARTOFSPEECH, partOfSpeech);

		long id = db.insert(DBField.TABLE_CONCEPT, null, cv);

		cv.clear();
		cv.put(DBField.COLUMN_CONCEPT_ID, (int) id);
		
		if(partOfSpeech.equals(NOUN)) {
			cv.put(DBField.COLUMN_NOUN_TYPE, type);
			cv.put(DBField.COLUMN_NOUN_ISSingular, isSingular);
		}
		
		db.insert(DBField.TABLE_NOUN, null, cv);
			
	}

	/**
	 * Inserts the concept in the Concept Table and in the Pronoun Table
	 * @param db			- SQL database
	 * @param conceptName
	 * @param partOfSpeech
	 * @param type
	 * @param isSingular
	 * @param gender
	 */
	public void addConcept(SQLiteDatabase db,String conceptName, String ontology, String partOfSpeech, String type, int isSingular, int gender){

		ContentValues cv = new ContentValues();

		cv.put(DBField.COLUMN_CONCEPT_NAME, conceptName);
//		cv.put(DBField.COLUMN_CONCEPT_ONTOLOGY, ontology);
//		cv.put(DBField.COLUMN_CONCEPT_PARTOFSPEECH, partOfSpeech);

		long id = db.insert(DBField.TABLE_CONCEPT, null, cv);

		cv.clear();
		cv.put(DBField.COLUMN_CONCEPT_ID, (int) id);

		if(partOfSpeech.equals(PRONOUN)){
			cv.put(DBField.COLUMN_PRONOUN_TYPE, type);
			cv.put(DBField.COLUMN_PRONOUN_ISSingular, isSingular);
			cv.put(DBField.COLUMN_PRONOUN_GENDER, gender);
		}

		db.insert(DBField.TABLE_PRONOUN, null, cv);

	}

}
