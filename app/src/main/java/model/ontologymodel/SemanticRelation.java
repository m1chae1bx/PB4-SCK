package model.ontologymodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import database.DBField;

public class SemanticRelation extends ISemanticRep {

	public static final String PASSIVE_CATEGORY = "Passive";

	// PB4-SCA Relations used are here
	public static final String PREFERS = "prefers";
	public static final String HAS_TRAIT = "has_trait";
	public static final String OCCUPIES_STATUS = "occupies_status";
	public static final String GENDER_IS = "gender_is";
	public static final String POLARITY = "polarity";
	public static final String OPPOSITE_OF = "opposite_of";
	public static final String IS_A = "is_a";
	public static final String DONE_AT = "done_at";
	public static final String CAPABLE_OF = "capable_of";
	public static final String PART_OF = "part_of";
	public static final String HAS_PROPERTY = "has_property";
	public static final String INSTANCE_OF = "instance_of";
	public static final String TARGETS = "targets";
	public static final String DONE_TO = "done_to";



	// CONCEPTNET 5 Relations
//	public static final String IS_A = "IsA";
//	public static final String HAS_PROPERTY = "HasProperty";
//	public static final String PART_OF = "PartOf";
//	public static final String RELATED_TO = "RelatedTo";
//	public static final String CAPABLE_OF = "CapableOf";
//	public static final String USED_FOR = "UsedFor";
//	public static final String CAUSES = "Causes";
//	public static final String AT_LOCATION = "AtLocation";
//	public static final String HAS_PREREQUISITE = "HasPrerequsite";
//	public static final String HAS_FIRST_SUBEVENT = "HasFirstSubevent";
//	public static final String HAS_SUBEVENT = "HasSubevent";
//	public static final String HAS_LAST_SUBEVENT = "HasLastSubevent";
//	public static final String MEMBER_OF = "MemberOf";
//	public static final String HAS_A = "HasA";
//	public static final String MOTIVATED_BY_GOAL = "MotivatedByGoal";
//	public static final String OBSTRUCTED_BY = "ObsructedBy";
//	public static final String CREATED_BY = "CreatedBy";
//	public static final String SYNONYM = "Synonym";
//	public static final String ANTONYM = "Antonym";
//	public static final String DERIVED_FROM = "DerivedFrom";
//	public static final String TRANSLATION_OF = "TranslationOf";
//	public static final String DEFINED_AS = "DefinedAs";
//
//	// Additional Relations
//	public static final String CAPABLE_OF_RECEIVING_ACTION = "CapableOfReceivingAction";
//	public static final String CONFLICT_OF = "ConflictOf";
//	public static final String HAS_RESOLUTION = "ResolutionOf";
	
	// CONCEPTNET 2 Relations
//	public static final String THEMATIC_K_LINE = "ThematicKLine";
//	public static final String SUPER_THEMATIC_K_LINE = "SuperThematicKLine";
//	public static final String MADE_OF = "MadeOf";
//	public static final String DEFINED_AS = "DefinedAs";
//	public static final String DESIROUS_EFFECT_OF = "DesirousEffectOf";
//	public static final String MOTIVATION_OF = "MotivationOf";
//	public static final String DESIRE_OF = "DesireOf";
	
	
	private Concept concept1;
	private Concept concept2;
	private String relation;
	private String category;

	public SemanticRelation() {
		
	}

	public SemanticRelation(Concept concept1, String relation, Concept concept2, String category) {
		this.concept1 = concept1;
		this.concept2 = concept2;
		this.relation = relation;
		this.category = category;
	}
	
	public Concept getConcept1() {
		return concept1;
	}
	
	public Concept getConcept2() {
		return concept2;
	}
	
	public String getRelation() {
		return relation;
	}
	
	public String getCategory() {
		return category;
	}

	/**
	 * Inserts a semantic relation in the database
	 * @param db 		- SQL database
	 * @param concept1 	-
	 * @param relation 	-
	 * @param concept2 	-
	 * @param category 	-
	 */
	public void addSemRep(SQLiteDatabase db, String concept1, String relation, String concept2, String category){

		ContentValues cv = new ContentValues();

		cv.put(DBField.COLUMN_SEMANTIC_CONCEPT1, concept1);
		cv.put(DBField.COLUMN_SEMANTIC_RELATION, relation);
		cv.put(DBField.COLUMN_SEMANTIC_CONCEPT2, concept2);
		cv.put(DBField.COLUMN_SEMANTIC_CATEGORY, category);
//		cv.put(DBField.COLUMN_ONTOLOGY_TYPE, type);

		db.insert(DBField.TABLE_SEMANTIC, null, cv);
	}
}
