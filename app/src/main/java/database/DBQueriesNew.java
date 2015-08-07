package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.narratologicalmodel.ConflictGoals;
import model.narratologicalmodel.ContextGoals;
import model.storyplanmodel.FabulaElementNew;
import model.narratologicalmodel.GoalTraitNew;
import model.storyplanmodel.LinkNew;
import model.narratologicalmodel.ResolutionGoal;
import model.ontologymodel.ConceptNew;
import process.exceptions.MalformedDataException;

import static database.DBField.COLUMN_CONCEPT_ID;
import static database.DBField.COLUMN_CONCEPT_NAME;
import static database.DBField.COLUMN_CONFLICT_CONFLICT;
import static database.DBField.COLUMN_CONFLICT_COUNTERACTION;
import static database.DBField.COLUMN_CONFLICT_GOALTRAITID;
import static database.DBField.COLUMN_CONFLICT_ID;
import static database.DBField.COLUMN_CONTEXT_ID;
import static database.DBField.COLUMN_CONTEXT_CONFLICTID;
import static database.DBField.COLUMN_CONTEXT_MAIN;
import static database.DBField.COLUMN_CONTEXT_DIRECTION;
import static database.DBField.COLUMN_CONTEXT_SUPPORT;
import static database.DBField.COLUMN_FABULAELEM_CATEGORY;
import static database.DBField.COLUMN_FABULAELEM_CONCEPTID;
import static database.DBField.COLUMN_FABULAELEM_ISNEGATED;
import static database.DBField.COLUMN_FABULAELEM_LABEL;
import static database.DBField.COLUMN_FABULAELEM_PARAMETERS;
import static database.DBField.COLUMN_FABULAELEM_POSTCONDITIONS;
import static database.DBField.COLUMN_FABULAELEM_PRECONDITIONS;
import static database.DBField.COLUMN_FABULAElEM_ID;
import static database.DBField.COLUMN_GOALTRAIT_ID;
import static database.DBField.COLUMN_GOALTRAIT_RBACKGROUND;
import static database.DBField.COLUMN_GOALTRAIT_ROBJECT;
import static database.DBField.COLUMN_GOALTRAIT_TRAIT;
import static database.DBField.COLUMN_LINK_FABULAELEM1;
import static database.DBField.COLUMN_LINK_FABULAELEM2;
import static database.DBField.COLUMN_LINK_ID;
import static database.DBField.COLUMN_LINK_PARAMS;
import static database.DBField.COLUMN_LINK_PRIORITY;
import static database.DBField.COLUMN_LINK_TYPE;
import static database.DBField.COLUMN_RESOLUTION_CONFLICTID;
import static database.DBField.COLUMN_RESOLUTION_GOAL;
import static database.DBField.COLUMN_RESOLUTION_ID;
import static database.DBField.COLUMN_SEMANTIC_CONCEPT1;
import static database.DBField.COLUMN_SEMANTIC_CONCEPT2;
import static database.DBField.COLUMN_SEMANTIC_CONCEPT3;
import static database.DBField.COLUMN_SEMANTIC_ID;
import static database.DBField.COLUMN_SEMANTIC_RELATION;
import static database.DBField.TABLE_CONCEPT;
import static database.DBField.TABLE_CONFLICT;
import static database.DBField.TABLE_CONTEXT;
import static database.DBField.TABLE_FABULAElEM;
import static database.DBField.TABLE_GOALTRAIT;
import static database.DBField.TABLE_LINK;
import static database.DBField.TABLE_RESOLUTION;
import static database.DBField.TABLE_SEMANTIC;

/**
 * Created by M. Bonon on 6/21/2015.
 */
public class DBQueriesNew { // todo merge with Data Retriever and SOMEOBJECT, decide on which to retain

    /**
     * Returns all the related concepts of a given concept and a given semantic relation.
     * This only returns the right-hand side concepts of non-bidirectional relations, thus, the
     * input concept must be a left-hand side concept. // todo For example, ...
     * Ternary semantic relations are also not supported by this method.
     *
     * @param nConcept - // todo javadoc
     * @param relation - // todo javadoc
     * @return // todo javadoc
     */
    public static List<Integer> getRelatedRightHandConcepts(int nConcept, String relation) {

        List<Integer> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT2 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_RELATION + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT1 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{relation, Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT2)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns all the related concepts of a given concept regardless of what semantic relation
     * exists between the two. This only returns the right-hand side concepts of non-bidirectional
     * relations, thus, the input concept must be a left-hand side concept. // todo For example, ...
     * Ternary semantic relations are also not supported by this method.
     *
     * @param nConcept - // todo javadoc
     * @return // todo javadoc
     */
    public static List<Integer> getRelatedRightHandConcepts(int nConcept) {

        List<Integer> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT2 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_CONCEPT1 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT2)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns all the related concepts of a given concept and a given semantic relation.
     * This returns the right-hand side and ternary concepts of non-bidirectional relations, thus, the
     * input concept must be a left-hand side concept. // todo For example, ...
     *
     * @param nConcept - // todo javadoc
     * @param relation - // todo javadoc
     * @return // todo javadoc
     */
    public static List<List<Integer>> getRelatedRightHandTernaryConcepts(int nConcept, String relation) {

        List<List<Integer>> propertyList = new ArrayList<>();
        List<Integer> innerList;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT2 + ", " + COLUMN_SEMANTIC_CONCEPT3 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_RELATION + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT1 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{relation, Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            innerList = new ArrayList<>();
            innerList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT2)));
            innerList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT3)));
            propertyList.add(innerList);
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns all the related concepts of a given concept and a given semantic relation.
     * This only returns the left-hand side concepts of non-bidirectional relations, thus, the
     * input concept must be a right-hand side concept. // todo For example, ...
     * Ternary semantic relations are also not supported by this method.
     *
     * @param nConcept - // todo javadoc
     * @param relation - // todo javadoc
     * @return // todo javadoc
     */
    public static List<Integer> getRelatedLeftHandConcepts(int nConcept, String relation) {

        List<Integer> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT1 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_RELATION + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT2 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{relation, Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT1)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns all the related concepts of a given concept regardless of what semantic relation
     * exists between the two. This only returns the left-hand side concepts of non-bidirectional
     * relations, thus, the input concept must be a right-hand side concept. // todo For example, ...
     * Ternary semantic relations are also not supported by this method.
     *
     * @param nConcept - // todo javadoc
     * @return // todo javadoc
     */
    public static List<Integer> getRelatedLeftHandConcepts(int nConcept) {

        List<Integer> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT1 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_CONCEPT2 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT1)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns the labels of all semantic relations that exist between the two given concepts.
     * This method also applies to bidirectional relations. For non-bidirectional relations, please
     * take note of the order in which the two given concepts are used in the method.
     *
     * @param nConcept  - // todo javadoc
     * @param sRelation - // todo javadoc
     * @return // todo javadoc
     */

    public static List<Integer> getRelatedConcepts(int nConcept, String sRelation) {
        List<Integer> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;
        Cursor c;

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT2 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_RELATION + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT1 + " = ?";

        c = db.rawQuery(sql, new String[]{sRelation, Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT2)));
            c.moveToNext();
        }

        c.close();

        sql = "SELECT " + COLUMN_SEMANTIC_CONCEPT1 + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_RELATION + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT2 + " = ?";

        c = db.rawQuery(sql, new String[]{sRelation, Integer.toString(nConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getInt(c.getColumnIndex(COLUMN_SEMANTIC_CONCEPT1)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    public static List<String> getRelation(int nLeftHandConcept, int nRightHandConcept) {

        List<String> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_RELATION + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_CONCEPT1 + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT2 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nLeftHandConcept),
                Integer.toString(nRightHandConcept)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getString(c.getColumnIndex(COLUMN_SEMANTIC_RELATION)));
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * Returns the labels of all semantic relations that exist between the three given concepts.
     *
     * @param nLeftHandConcept  - // todo javadoc
     * @param nRightHandConcept - // todo javadoc
     * @param nTernaryConcept   - // todo javadoc
     * @return // todo javadoc
     */
    public static List<String> getRelation(int nLeftHandConcept, int nRightHandConcept,
                                           int nTernaryConcept) { // todo sample statement split into multiple lines

        List<String> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_RELATION + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_CONCEPT1 + " = ? AND " +
                COLUMN_SEMANTIC_CONCEPT2 + " = ? AND " + COLUMN_SEMANTIC_CONCEPT3 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nLeftHandConcept),
                Integer.toString(nRightHandConcept), Integer.toString(nTernaryConcept)}); // todo sample statement split into multiple lines
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(c.getString(c.getColumnIndex(
                    COLUMN_SEMANTIC_RELATION))); // todo sample statement split into multiple lines
            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    /**
     * @return
     */
    public static List<GoalTraitNew> getGoalTraits(int nBgConceptId) {
        List<GoalTraitNew> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_GOALTRAIT +
                " WHERE " + COLUMN_GOALTRAIT_RBACKGROUND + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nBgConceptId)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(new GoalTraitNew(c.getInt(c.getColumnIndex(COLUMN_GOALTRAIT_ID)),
                    c.getInt(c.getColumnIndex(COLUMN_GOALTRAIT_TRAIT)),
                    c.getInt(c.getColumnIndex(COLUMN_GOALTRAIT_RBACKGROUND)),
                    c.getInt(c.getColumnIndex(COLUMN_GOALTRAIT_ROBJECT))));

            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    public static String getConceptName(int j) {

        String property;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_CONCEPT_NAME + " FROM " + TABLE_CONCEPT +
                " WHERE " + COLUMN_CONCEPT_ID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(j)});
        c.moveToFirst();
        property = c.getString(c.getColumnIndex(COLUMN_CONCEPT_NAME));

        c.close();

        return property;
    }

    /**
     * @param sName
     * @return
     */
    public static Integer getConceptId(String sName) {

        int nProperty;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_CONCEPT_ID + " FROM " + TABLE_CONCEPT +
                " WHERE " + COLUMN_CONCEPT_NAME + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{sName});
        c.moveToFirst();
        nProperty = c.getInt(c.getColumnIndex(COLUMN_CONCEPT_ID));

        c.close();

        return nProperty;
    }

    public static List<ConflictGoals> getConflictGoals(int nGoalTrait) {
        List<ConflictGoals> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_CONFLICT +
                " WHERE " + COLUMN_CONFLICT_GOALTRAITID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nGoalTrait)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(new ConflictGoals(c.getInt(c.getColumnIndex(COLUMN_CONFLICT_ID)),
                    c.getInt(c.getColumnIndex(COLUMN_CONFLICT_GOALTRAITID)),
                    c.getInt(c.getColumnIndex(COLUMN_CONFLICT_CONFLICT)),
                    c.getInt(c.getColumnIndex(COLUMN_CONFLICT_COUNTERACTION))));

            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    public static List<ResolutionGoal> getResolutionGoals(int nConflictId) {
        List<ResolutionGoal> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_RESOLUTION +
                " WHERE " + COLUMN_RESOLUTION_CONFLICTID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nConflictId)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(new ResolutionGoal(c.getInt(c.getColumnIndex(COLUMN_RESOLUTION_ID)),
                    c.getInt(c.getColumnIndex(COLUMN_RESOLUTION_CONFLICTID)),
                    c.getInt(c.getColumnIndex(COLUMN_RESOLUTION_GOAL))));

            c.moveToNext();
        }

        c.close();

        return propertyList;
    }

    public static List<ContextGoals> getContextGoals(int nConflictGoalsId) {
        List<ContextGoals> propertyList = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_CONTEXT +
                " WHERE " + COLUMN_CONTEXT_CONFLICTID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nConflictGoalsId)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            propertyList.add(new ContextGoals(c.getInt(c.getColumnIndex(COLUMN_CONTEXT_ID)),
                    c.getInt(c.getColumnIndex(COLUMN_CONTEXT_CONFLICTID)),
                    c.getInt(c.getColumnIndex(COLUMN_CONTEXT_MAIN)),
                    c.getInt(c.getColumnIndex(COLUMN_CONTEXT_SUPPORT)),
                    c.getInt(c.getColumnIndex(COLUMN_CONTEXT_DIRECTION))));

            c.moveToNext();
        }
        c.close();

        return propertyList;
    }

    public static FabulaElementNew getFabulaElementByConcept(int nConceptId) {
        FabulaElementNew fabula;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_FABULAElEM +
                " WHERE " + COLUMN_FABULAELEM_CONCEPTID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nConceptId)});
        c.moveToFirst();
        fabula = new FabulaElementNew(c.getInt(c.getColumnIndex(COLUMN_FABULAElEM_ID)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_LABEL)),
                c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_CONCEPTID)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_CATEGORY)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PARAMETERS)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PRECONDITIONS)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_POSTCONDITIONS)),
                c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_ISNEGATED)));
        c.close();

        return fabula;
    }

    public static FabulaElementNew getFabulaElementById(int nId) {
        FabulaElementNew fabula;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_FABULAElEM +
                " WHERE " + COLUMN_FABULAElEM_ID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nId)});
        c.moveToFirst();
        fabula = new FabulaElementNew(c.getInt(c.getColumnIndex(COLUMN_FABULAElEM_ID)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_LABEL)),
                c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_CONCEPTID)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_CATEGORY)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PARAMETERS)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PRECONDITIONS)),
                c.getString(c.getColumnIndex(COLUMN_FABULAELEM_POSTCONDITIONS)),
                c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_ISNEGATED)));
        c.close();

        return fabula;
    }

    public static List<LinkNew> getLinksOfDestinationFabElem(int nDestinationFabulaId) throws MalformedDataException {
        List<LinkNew> links = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_LINK +
                " WHERE " + COLUMN_LINK_FABULAELEM2 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nDestinationFabulaId)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            links.add(new LinkNew(c.getInt(c.getColumnIndex(COLUMN_LINK_ID)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_TYPE)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM1)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM2)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_PRIORITY)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_PARAMS))));
            c.moveToNext();
        }
        c.close();

        return links;
    }

    public static List<LinkNew> getLinksOfSourceFabElem(int nSourceFabulaId) throws MalformedDataException {
        List<LinkNew> links = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_LINK +
                " WHERE " + COLUMN_LINK_FABULAELEM1 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(nSourceFabulaId), Integer.toString(nSourceFabulaId)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            links.add(new LinkNew(c.getInt(c.getColumnIndex(COLUMN_LINK_ID)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_TYPE)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM1)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM2)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_PRIORITY)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_PARAMS))));
            c.moveToNext();
        }
        c.close();

        return links;
    }

    public static boolean isExisting(int i, String relation, int i1) {

        boolean property = false;
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT " + COLUMN_SEMANTIC_ID + " FROM " + TABLE_SEMANTIC +
                " WHERE " + COLUMN_SEMANTIC_CONCEPT1 + " = ?" + COLUMN_SEMANTIC_RELATION + " = ?" +
                COLUMN_SEMANTIC_CONCEPT2 + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{Integer.toString(i), relation, Integer.toString(i1)});
        if (c.moveToFirst())
            property = true;

        c.close();

        return property;

    }

    public static boolean isExisting(int i, String relation, String sTemp) {
        // todo
        return false;
    }

    public static List<FabulaElementNew> getFabulaElements() {
        List<FabulaElementNew> fabEls = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_FABULAElEM;

        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            fabEls.add(new FabulaElementNew(c.getInt(c.getColumnIndex(COLUMN_FABULAElEM_ID)),
                    c.getString(c.getColumnIndex(COLUMN_FABULAELEM_LABEL)),
                    c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_CONCEPTID)),
                    c.getString(c.getColumnIndex(COLUMN_FABULAELEM_CATEGORY)),
                    c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PARAMETERS)),
                    c.getString(c.getColumnIndex(COLUMN_FABULAELEM_PRECONDITIONS)),
                    c.getString(c.getColumnIndex(COLUMN_FABULAELEM_POSTCONDITIONS)),
                    c.getInt(c.getColumnIndex(COLUMN_FABULAELEM_ISNEGATED))));
            c.moveToNext();
        }
        c.close();

        return fabEls;
    }

    public static List<LinkNew> getLinks() throws MalformedDataException {
        List<LinkNew> links = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;

        sql = "SELECT * FROM " + TABLE_LINK;

        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            links.add(new LinkNew(c.getInt(c.getColumnIndex(COLUMN_LINK_ID)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_TYPE)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM1)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_FABULAELEM2)),
                    c.getInt(c.getColumnIndex(COLUMN_LINK_PRIORITY)),
                    c.getString(c.getColumnIndex(COLUMN_LINK_PARAMS))));
            c.moveToNext();
        }
        c.close();

        return links;
    }

    public static List<ConceptNew> transformToConcepts(List<FabulaElementNew> fabEls) {
        List<ConceptNew> concepts = new ArrayList<>();
        SQLiteDatabase db = DBFactory.sqldb;
        String sql;
        Cursor c;

        sql = "SELECT * FROM " + TABLE_CONCEPT + " WHERE " + COLUMN_CONCEPT_ID + " = ?";

        for (FabulaElementNew fabTemp : fabEls) {
            c = db.rawQuery(sql, new String[]{Integer.toString(fabTemp.getnConceptId())});
            c.moveToFirst();
            concepts.add(new ConceptNew(c.getInt(c.getColumnIndex(COLUMN_CONCEPT_ID)),
                    c.getString(c.getColumnIndex(COLUMN_CONCEPT_NAME))));
            c.close();
        }

        return concepts;
    }
}
