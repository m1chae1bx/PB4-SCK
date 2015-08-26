package database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class DBInitializeNew { // todo rename to a noun that is related to DBoperations

    /**
     * Inserts a character in the database
     *
     * @param db         - SQL database
     * @param nConceptId - reference to the corresponding concept in the Concepts table
     * @param sImagePath - file path of the image
     */
    public static long addCharacter(SQLiteDatabase db, int nConceptId, String sImagePath) {

        ContentValues cv = new ContentValues();
        long lTemp;

        cv.put(DBField.COLUMN_CHAR_CONCEPTID, nConceptId);
        cv.put(DBField.COLUMN_CHAR_IMAGEPATH, sImagePath);

        lTemp = db.insert(DBField.TABLE_CHARACTER, null, cv);

        return lTemp;
    }

    /**
     * Inserts a background in the database
     *
     * @param db         - SQL database
     * @param nConceptId - reference to the corresponding concept in the Concepts table
     * @param sImagePath - file path of the image
     */
    public static long addBackground(SQLiteDatabase db, int nConceptId, String sImagePath) {

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_BACKGROUND_CONCEPTID, nConceptId);
        cv.put(DBField.COLUMN_BACKGROUND_IMAGEPATH, sImagePath);

        return db.insert(DBField.TABLE_BACKGROUND, null, cv);
    }

    /**
     * Inserts an object in the database
     *
     * @param db         - SQL database
     * @param nConceptId - reference to the corresponding concept in the Concepts table
     * @param sImagePath - file path of the image
     */
    public static long addObject(SQLiteDatabase db, int nConceptId, String sImagePath) {

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_OBJECT_CONCEPTID, nConceptId);
        cv.put(DBField.COLUMN_OBJECT_IMAGEPATH, sImagePath);

        return db.insert(DBField.TABLE_OBJECT, null, cv);
    }

    /**
     * Inserts an object related to the background in the BGObject table
     *
     * @param db       - SQL database
     * @param bgID     - background ID
     * @param objectID - object ID
     */
    public static long addBackgroundObject(SQLiteDatabase db, int bgID, int objectID) {

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_BACKGROUND_ID, bgID);
        cv.put(DBField.COLUMN_BGOBJECT_OBJECTID, objectID);

        return db.insert(DBField.TABLE_BGOBJECT, null, cv);
    }

    /**
     * Inserts the concept in the Concept Table and in the Noun Table.
     *
     * @param db          - SQL database
     * @param conceptName
     */
    public static long addConcept(SQLiteDatabase db, String conceptName) { // todo create type in the future

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_CONCEPT_NAME, conceptName);
//      commented on 6-20
//      cv.put(DBField.COLUMN_CONCEPT_ONTOLOGY, ontology);
//      cv.put(DBField.COLUMN_CONCEPT_PARTOFSPEECH, partOfSpeech);

        return db.insert(DBField.TABLE_CONCEPT, null, cv);

//        cv.clear();
//        cv.put(DBField.COLUMN_CONCEPT_ID, (int) id);
//
//        if(partOfSpeech.equals(NOUN)) {
//            cv.put(DBField.COLUMN_NOUN_TYPE, type);
//            cv.put(DBField.COLUMN_NOUN_ISSingular, isSingular);
//        }
//
//        db.insert(DBField.TABLE_NOUN, null, cv);
    }

    /**
     * Inserts a semantic relation in the database
     *
     * @param db        - SQL database
     * @param nConcept1 -
     * @param relation  -
     * @param nConcept2 -
     * @param nConcept3 -
     * @param category  -
     */
    public static long addSemRep(SQLiteDatabase db, int nConcept1, String relation, int nConcept2, int nConcept3, String category) {

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_SEMANTIC_CONCEPT1, nConcept1);
        cv.put(DBField.COLUMN_SEMANTIC_RELATION, relation);
        cv.put(DBField.COLUMN_SEMANTIC_CONCEPT2, nConcept2);
        cv.put(DBField.COLUMN_SEMANTIC_CONCEPT3, nConcept3);
        cv.put(DBField.COLUMN_SEMANTIC_CATEGORY, category);

        return db.insert(DBField.TABLE_SEMANTIC, null, cv);
    }

    public static long addGoalTrait(SQLiteDatabase db, int nGoalTrait, int nBg, int nObj) {

        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_GOALTRAIT_TRAIT, nGoalTrait);
        cv.put(DBField.COLUMN_GOALTRAIT_RBACKGROUND, nBg);
        cv.put(DBField.COLUMN_GOALTRAIT_ROBJECT, nObj);

        return db.insert(DBField.TABLE_GOALTRAIT, null, cv);
    }

    public static long addFabulaElement(SQLiteDatabase db, String sLabel, int nConceptId, String sCategory, List<String> sParams,
                                        List<String> sPreconds, List<String> sPostconds, boolean isNegated) {
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_FABULAELEM_CONCEPTID, nConceptId);
        cv.put(DBField.COLUMN_FABULAELEM_LABEL, sLabel);
        cv.put(DBField.COLUMN_FABULAELEM_CATEGORY, sCategory);
        if (sParams != null)
            cv.put(DBField.COLUMN_FABULAELEM_PARAMETERS, sParams.toString().replaceAll("\\s+", ""));
        if (sPreconds != null)
            cv.put(DBField.COLUMN_FABULAELEM_PRECONDITIONS, sPreconds.toString().replaceAll("\\s+", ""));
        if (sPostconds != null) {
            cv.put(DBField.COLUMN_FABULAELEM_POSTCONDITIONS, sPostconds.toString().replaceAll("\\s+", ""));
        }
        cv.put(DBField.COLUMN_FABULAELEM_ISNEGATED, isNegated ? 1 : 0);

        return db.insert(DBField.TABLE_FABULAElEM, null, cv);
    }

    public static long addConflictGoals(SQLiteDatabase db, int nGoalTrait, int nConflict, int nConflictSub,
                                        int nCounter, int nCounterSub) {
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_CONFLICT_GOALTRAITID, nGoalTrait);
        cv.put(DBField.COLUMN_CONFLICT_CONFLICT, nConflict);
        cv.put(DBField.COLUMN_CONFLICT_CONFLICTSUB, nConflictSub);
        cv.put(DBField.COLUMN_CONFLICT_COUNTERACTION, nCounter);
        cv.put(DBField.COLUMN_CONFLICT_COUNTERACTIONSUB, nCounterSub);

        return db.insert(DBField.TABLE_CONFLICT, null, cv);
    }

    public static long addContextGoals(SQLiteDatabase db, int nConflict, int nMain, int nMainSub, int nSupport, int nSupportSub, int nDirection) {
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_CONTEXT_CONFLICTID, nConflict);
        cv.put(DBField.COLUMN_CONTEXT_MAIN, nMain);
        cv.put(DBField.COLUMN_CONTEXT_MAINSUB, nMainSub);
        cv.put(DBField.COLUMN_CONTEXT_SUPPORT, nSupport);
        cv.put(DBField.COLUMN_CONTEXT_SUPPORTSUB, nSupportSub);
        cv.put(DBField.COLUMN_CONTEXT_DIRECTION, nDirection);

        return db.insert(DBField.TABLE_CONTEXT, null, cv);
    }

    public static long addResolution(SQLiteDatabase db, int nConflictId, int nGoal, int nGoalSub) {
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_RESOLUTION_CONFLICTID, nConflictId);
        cv.put(DBField.COLUMN_RESOLUTION_GOAL, nGoal);
        cv.put(DBField.COLUMN_RESOLUTION_GOALSUB, nGoalSub);

        return db.insert(DBField.TABLE_RESOLUTION, null, cv);
    }

    public static long addLink(SQLiteDatabase db, String type, int fb1Id, int nSub1Id, int fb2Id,
                               int nSub2Id, int nPriority, List<String> sParamDependencies,
                               List<String> sPreconditions, List<String> sPostconditions) { // todo add other necessary fields to link
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_LINK_TYPE, type);
        cv.put(DBField.COLUMN_LINK_FABULAELEM1, fb1Id);
        cv.put(DBField.COLUMN_LINK_SUB1ID, nSub1Id);
        cv.put(DBField.COLUMN_LINK_FABULAELEM2, fb2Id);
        cv.put(DBField.COLUMN_LINK_SUB2ID, nSub2Id);
        cv.put(DBField.COLUMN_LINK_PRIORITY, nPriority);
        if (sParamDependencies != null)
            cv.put(DBField.COLUMN_LINK_PARAMS, sParamDependencies.toString().replaceAll("\\s+", ""));
        if (sPreconditions != null)
            cv.put(DBField.COLUMN_LINK_PRECONDITIONS, sPreconditions.toString().replaceAll("\\s+", ""));
        if (sPostconditions != null)
            cv.put(DBField.COLUMN_LINK_POSTCONDITIONS, sPostconditions.toString().replaceAll("\\s+", ""));

        return db.insert(DBField.TABLE_LINK, null, cv);
    }

    public static long addNorm(SQLiteDatabase db, int nFabElemId, List<String> sPreconditions, int nPolarity, String sOrder, List<String> sParameters) {
        ContentValues cv = new ContentValues();

        cv.put(DBField.COLUMN_NORM_FABULAELEMID, nFabElemId);
        cv.put(DBField.COLUMN_NORM_POLARITY, nPolarity);
        cv.put(DBField.COLUMN_NORM_ORDER, sOrder);
        if (sPreconditions != null)
            cv.put(DBField.COLUMN_NORM_PRECONDITIONS, sPreconditions.toString().replaceAll("\\s+", ""));
        if (sParameters != null)
            cv.put(DBField.COLUMN_NORM_PARAMETERS, sParameters.toString().replaceAll("\\s+", ""));

        return db.insert(DBField.TABLE_NORM, null, cv);
    }

    /**
     * Returns an object with its information from the database
     * @param name
     * @return
     */
//    public Object getObject(String name) {
//
//        SQLiteDatabase db = DBFactory.db.getReadableDatabase();
//
//        String sql = "SELECT * FROM " + DBField.TABLE_OBJECT + " WHERE + " + DBField.COLUMN_OBJECT_NAME + " = ?";
//
//        Cursor c = db.rawQuery(sql, new String[] {name});
//        c.moveToFirst();
//
//        Object t = new Object();
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//
//            t.setnLocationId(c.getInt(c.getColumnIndex(DBField.COLUMN_OBJECT_ID)));
//            t.setsName(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_NAME)));
//            t.setsImagePath(c.getString(c.getColumnIndex(DBField.COLUMN_OBJECT_IMAGEPATH)));
//            //t.setTempObject(c.getString(c.getColumnIndex(DBField.COLUMN_RELATED_OBJECT)));
//            c.moveToNext();
//        }
//        return t;
//    }
}
