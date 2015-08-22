package database;

// edited starting 6-20
public final class DBField {
    public static final String DB_NAME = "PB4";

    /*
    SOCIOCULTURAL KNOWLEDGE
     */

    // added on Aug-11
    // NORM TABLE
    public static final String TABLE_NORM = "Norm";
    public static final String COLUMN_NORM_ID = "normId";
    public static final String COLUMN_NORM_FABULAELEMID = "fabulaElemId";
    public static final String COLUMN_NORM_PRECONDITIONS = "preconditions";
    public static final String COLUMN_NORM_POLARITY = "polarity";
    public static final String COLUMN_NORM_ORDER = "fabulaOrder";
    public static final String COLUMN_NORM_PARAMETERS = "parameters";

    /*
    STICKER REPOSITORY
    */

    // edited starting 6-20
    // CHARACTER TABLE
    public static final String TABLE_CHARACTER = "Character";
    public static final String COLUMN_CHAR_ID = "charId";
    public static final String COLUMN_CHAR_CONCEPTID = "conceptId"; // added 6-20
    public static final String COLUMN_CHAR_IMAGEPATH = "imagePath";

    // edited starting 6-20
    // BACKGROUND TABLE
    public static final String TABLE_BACKGROUND = "Background";
    public static final String COLUMN_BACKGROUND_ID = "bgId";
    public static final String COLUMN_BACKGROUND_CONCEPTID = "conceptId"; // added 6-20
    public static final String COLUMN_BACKGROUND_IMAGEPATH = "imagePath";

    // edited starting 6-20
    // OBJECT TABLE
    public static final String TABLE_OBJECT = "Object";
    public static final String COLUMN_OBJECT_ID = "objectId";
    public static final String COLUMN_OBJECT_CONCEPTID = "conceptId"; // added 6-20
    public static final String COLUMN_OBJECT_IMAGEPATH = "imagePath";

    // BGOBJECT TABLE
    public static final String TABLE_BGOBJECT = "BGObject";
    public static final String COLUMN_BGOBJECT_OBJECTID = "objId";
    public static final String COLUMN_BGOBJECT_BGID = "bgId";
    // -------------------------

    // -------------------------
    // STORY WORLD KNOWLEDGE
    // -------------------------

    //edited starting 6-21
    //SEMANTIC RELATIONS TABLE
    public static final String TABLE_SEMANTIC = "SemanticRelation";
    public static final String COLUMN_SEMANTIC_ID = "srId";
    public static final String COLUMN_SEMANTIC_CONCEPT1 = "concept1";
    public static final String COLUMN_SEMANTIC_RELATION = "srLabel";
    public static final String COLUMN_SEMANTIC_CONCEPT2 = "concept2";
    public static final String COLUMN_SEMANTIC_CONCEPT3 = "concept3"; // added 6-21
    public static final String COLUMN_SEMANTIC_CATEGORY = "srCategory";

    //edited starting 6-20
    //CONCEPT TABLE
    public static final String TABLE_CONCEPT = "Concept";
    public static final String COLUMN_CONCEPT_NAME = "conceptName";
    public static final String COLUMN_CONCEPT_ID = "conceptId";
//  public static final String COLUMN_CONCEPT_PARTOFSPEECH = "partOfSpeech";
//	public static final String COLUMN_CONCEPT_ONTOLOGY = "ontology";
//	public static final String COLUMN_ONTOLOGY_TYPE = "ontologyType";

    // -------------------------

    // -------------------------
    // NARRATOLOGICAL CONCEPTS
    // -------------------------
    // added 6-23
    // GOAL TRAIT TABLE
    public static final String TABLE_GOALTRAIT = "GoalTrait";
    public static final String COLUMN_GOALTRAIT_ID = "gTraitId";
    public static final String COLUMN_GOALTRAIT_TRAIT = "moralLesson";
    public static final String COLUMN_GOALTRAIT_RBACKGROUND = "relatedBG";
    public static final String COLUMN_GOALTRAIT_ROBJECT = "relatedObj";
    // added 6-23
    // CONFLICT GOALS TABLE
    public static final String TABLE_CONFLICT = "ConflictGoals";
    public static final String COLUMN_CONFLICT_ID = "conflictId";
    public static final String COLUMN_CONFLICT_GOALTRAITID = "goalTraitId";
    public static final String COLUMN_CONFLICT_CONFLICT = "conflict";
    public static final String COLUMN_CONFLICT_CONFLICTSUB = "conflictSub";
    public static final String COLUMN_CONFLICT_COUNTERACTION = "counterAction";
    public static final String COLUMN_CONFLICT_COUNTERACTIONSUB = "counterActionSub";
    // added 7-5
    // CONTEXT MAIN GOALS TABLE
    public static final String TABLE_CONTEXT = "ContextGoals";
    public static final String COLUMN_CONTEXT_ID = "contextId";
    public static final String COLUMN_CONTEXT_CONFLICTID = "conflictId";
    public static final String COLUMN_CONTEXT_MAIN = "mainGoal";
    public static final String COLUMN_CONTEXT_MAINSUB = "mainGoalSub";
    public static final String COLUMN_CONTEXT_SUPPORT = "supportGoal";
    public static final String COLUMN_CONTEXT_SUPPORTSUB = "supportGoalSub";
    public static final String COLUMN_CONTEXT_DIRECTION = "searchDirection";
    // added 7-25
    // CONTEXT SUPPORT GOALS TABLE
    public static final String TABLE_RESOLUTION = "ResolutionGoals";
    public static final String COLUMN_RESOLUTION_ID = "resolutionId";
    public static final String COLUMN_RESOLUTION_CONFLICTID = "conflictId";
    public static final String COLUMN_RESOLUTION_GOAL = "goal";
    public static final String COLUMN_RESOLUTION_GOALSUB = "goalSub";

    // -------------------------

    // -------------------------
    // PLOT STRUCTURES
    // -------------------------
    // added 6-24
    // FABULA ELEMENT TABLE
    public static final String TABLE_FABULAElEM = "FabulaElement";
    public static final String COLUMN_FABULAElEM_ID = "fabulaId";
    public static final String COLUMN_FABULAELEM_CONCEPTID = "conceptId";
    public static final String COLUMN_FABULAELEM_LABEL = "label";
    public static final String COLUMN_FABULAELEM_CATEGORY = "category";
    public static final String COLUMN_FABULAELEM_PARAMETERS = "parameters";
    public static final String COLUMN_FABULAELEM_PRECONDITIONS = "preconditions";
    public static final String COLUMN_FABULAELEM_POSTCONDITIONS = "postconditions";
    public static final String COLUMN_FABULAELEM_RECOMMENDATIONS = "recommendations";
    public static final String COLUMN_FABULAELEM_ISNEGATED = "isNegated";
    // added 6-25
    // LINK ELEMENT TABLE
    public static final String TABLE_LINK = "Link";
    public static final String COLUMN_LINK_ID = "linkId";
    public static final String COLUMN_LINK_TYPE = "type";
    public static final String COLUMN_LINK_FABULAELEM1 = "fb1Id";
    public static final String COLUMN_LINK_SUB1ID = "sub1Id";
    public static final String COLUMN_LINK_FABULAELEM2 = "fb2Id";
    public static final String COLUMN_LINK_SUB2ID = "sub2Id";
    public static final String COLUMN_LINK_PRIORITY = "priority";
    public static final String COLUMN_LINK_PARAMS = "paramDependency";
    public static final String COLUMN_LINK_PRECONDITIONS = "precond";

    // -------------------------
    // TODO other fields of link table that is necessary
    //STORY TABLE
    public static final String TABLE_STORY = "Story";
    public static final String COLUMN_STORY_ID = "storyID";
    public static final String COLUMN_STORY_TITLE = "title";
    public static final String COLUMN_STORY_AUTHOR = "author";
    public static final String COLUMN_STORY_IMAGEPATH = "imagePath";
    public static final String COLUMN_STORY_STORYTEXT = "storyText";
    //THEME TABLE
    public static final String TABLE_THEME = "Theme";
    public static final String COLUMN_THEME_ID = "themeID";
    public static final String COLUMN_MORAL_LESSON = "moralLesson";
    public static final String COLUMN_RELATED_BACKGROUND = "relatedBackground";
    public static final String COLUMN_RELATED_OBJECT = "relatedObject";
    public static final String COLUMN_RELATED_TRAIT = "relatedTrait";
    //EVENT TABLE
    public static final String TABLE_EVENT = "EVENT";
    public static final String COLUMN_EVENT_ID = "eventID";
    public static final String COLUMN_EVENT_NAME = "event";
    public static final String COLUMN_MAX_AGENTS = "maxAgents";
    public static final String COLUMN_MAX_PATIENTS = "maxPatients";
    public static final String COLUMN_PRECONDITION = "precondition";
    public static final String COLUMN_POSTCONDITION = "postcondition";
    public static final String COLUMN_INTERACTIONSCORE = "interactionScore";
    public static final String COLUMN_IRSCORE = "irScore";
    public static final String COLUMN_EVENT_TYPE = "type";
    //TRAIT TABLE
    public static final String TABLE_TRAIT = "Trait";
    public static final String COLUMN_TRAIT = "trait";
    //INTERPERSONAL RELATIONSHIP TABLE
    public static final String TABLE_IR = "IR";
    public static final String COLUMN_FROM = "fromchar";
    public static final String COLUMN_TO = "tochar";
    public static final String COLUMN_RELATIONSHIP = "relationship";
    public static final String COLUMN_SCORE = "score";
    //NOUN TABLE
    public static final String TABLE_NOUN = "Noun";
    public static final String COLUMN_NOUN_TYPE = "type";
    public static final String COLUMN_NOUN_ISSingular = "isSingular";
    //PRONOUN TABLE
    public static final String TABLE_PRONOUN = "Pronoun";
    public static final String COLUMN_PRONOUN_TYPE = "type";
    public static final String COLUMN_PRONOUN_ISSingular = "isSingular";
    public static final String COLUMN_PRONOUN_GENDER = "gender";
    //VERB TABLE
    public static final String TABLE_VERB = "Verb";
    public static final String COLUMN_VERB_TENSE = "tense";
    //ADJECTIVE TABLE
    public static final String TABLE_ADJECTIVE = "ADJECTIVE";
    public static final String COLUMN_ADJECTIVE_TYPE = "type";
    //ADVERB TABLE
    public static final String TABLE_ADVERB = "Adverb";
    public static final String COLUMN_ADVERB_TYPE = "type";
    //PREPOSITION TABLE
    public static final String TABLE_PREPOSITION = "Preposition";
    public static final String COLUMN_PREPOSITION_TYPE = "type";
    //CONJUCTION TABLE
    public static final String TABLE_CONJUNCTION = "Conjunction";
    public static final String COLUMN_CONJUNCTION_TYPE = "type";
    //DIALOGUE GENERATOR
    public static final String TABLE_DIALOGUE = "Dialogue";
    public static final String COLUMN_DIALOGUE_ID = "dialogueID";
    public static final String COLUMN_DIALOGUE = "dialogue";
    public static final String COLUMN_DIALOGUE_TYPE = "dialogueType";
    public static final String COLUMN_DIALOGUE_ISSINGULAR = "issingular";

    /*
    Create Tables
     */

    // NORM TABLE
    public static final String CREATE_NORM_TABLE =
            "CREATE TABLE " + TABLE_NORM + " (" +
                    COLUMN_NORM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NORM_FABULAELEMID + " INTEGER NOT NULL, " +
                    COLUMN_NORM_PRECONDITIONS + " TEXT, " +
                    COLUMN_NORM_POLARITY + " INTEGER NOT NULL, " +
                    COLUMN_NORM_ORDER + " TEXT, " +
                    COLUMN_NORM_PARAMETERS + " TEXT," +
                    "FOREIGN KEY (" + COLUMN_NORM_FABULAELEMID + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + ")" +
                    ")";

    //STORY TABLE
    public static final String CREATE_STORY_TABLE =
            "CREATE TABLE " + TABLE_STORY + " (" +
                    COLUMN_STORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_STORY_TITLE + " TEXT NOT NULL, " +
                    COLUMN_STORY_AUTHOR + " TEXT NOT NULL, " +
                    COLUMN_STORY_IMAGEPATH + " TEXT NOT NULL, " +
                    COLUMN_STORY_STORYTEXT + " TEXT NOT NULL " +
                    ")";
    //CHARACTER TABLE // edited on 6-20
    public static final String CREATE_CHARACTER_TABLE =
            "CREATE TABLE " + TABLE_CHARACTER + " (" +
                    COLUMN_CHAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_CHAR_CONCEPTID + " INTEGER NOT NULL, " + // added 6-20
                    COLUMN_CHAR_IMAGEPATH + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_CHAR_CONCEPTID + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ")" + // added 6-20
                    ")";
    //BACKGROUND TABLE // edited on 6-20
    public static final String CREATE_BACKGROUND_TABLE =
            "CREATE TABLE " + TABLE_BACKGROUND + " (" +
                    COLUMN_BACKGROUND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_BACKGROUND_CONCEPTID + " INTEGER NOT NULL, " + // added 6-20
                    COLUMN_BACKGROUND_IMAGEPATH + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_BACKGROUND_CONCEPTID + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ")" + // added 6-20
                    ")";
    //OBJECT TABLE // edited on 6-20
    public static final String CREATE_OBJECT_TABLE =
            "CREATE TABLE " + TABLE_OBJECT + " (" +
                    COLUMN_OBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_OBJECT_CONCEPTID + " INTEGER NOT NULL, " + // added 6-20
                    COLUMN_OBJECT_IMAGEPATH + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_OBJECT_CONCEPTID + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ")" + // added 6-20
                    ")";
    //SEMANTIC TABLE
    public static final String CREATE_SEMANTIC_TABLE =
            "CREATE TABLE " + TABLE_SEMANTIC + " (" +
                    COLUMN_SEMANTIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_SEMANTIC_CONCEPT1 + " INTEGER NOT NULL, " +
                    COLUMN_SEMANTIC_RELATION + " TEXT, " +
                    COLUMN_SEMANTIC_CONCEPT2 + " INTEGER NOT NULL, " +
                    COLUMN_SEMANTIC_CONCEPT3 + " INTEGER, " +
                    COLUMN_SEMANTIC_CATEGORY + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_SEMANTIC_CONCEPT1 + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_SEMANTIC_CONCEPT2 + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_SEMANTIC_CONCEPT3 + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ") " +
                    ")";
    //CONCEPT TABLE
    public static final String CREATE_CONCEPT_TABLE =
            "CREATE TABLE " + TABLE_CONCEPT + " (" +
                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_CONCEPT_NAME + " TEXT NOT NULL" +
                    ")";
    //GOAL TRAIT TABLE // added 6-23
    public static final String CREATE_GOALTRAIT_TABLE =
            "CREATE TABLE " + TABLE_GOALTRAIT + " (" +
                    COLUMN_GOALTRAIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_GOALTRAIT_TRAIT + " INTEGER, " +
                    COLUMN_GOALTRAIT_RBACKGROUND + " INTEGER, " +
                    COLUMN_GOALTRAIT_ROBJECT + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_GOALTRAIT_RBACKGROUND + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_GOALTRAIT_ROBJECT + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ") " +
                    ")";

    //                  commented on 6-20
//					COLUMN_CONCEPT_ONTOLOGY + " TEXT, " +
//					COLUMN_CONCEPT_PARTOFSPEECH + " TEXT " +
    // CONFLICT GOALS TABLE // added 6-23
    public static final String CREATE_CONFLICT_TABLE =
            "CREATE TABLE " + TABLE_CONFLICT + " (" +
                    COLUMN_CONFLICT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_CONFLICT_GOALTRAITID + " INTEGER, " +
                    COLUMN_CONFLICT_CONFLICT + " INTEGER, " +
                    COLUMN_CONFLICT_CONFLICTSUB + " INTEGER, " +
                    COLUMN_CONFLICT_COUNTERACTION + " INTEGER, " +
                    COLUMN_CONFLICT_COUNTERACTIONSUB + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_CONFLICT_GOALTRAITID + ") REFERENCES " + TABLE_GOALTRAIT + "(" + COLUMN_GOALTRAIT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CONFLICT_CONFLICT + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CONFLICT_COUNTERACTION + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + ") " +
                    ")";
    // CONTEXT MAIN GOALS TABLE // added 7-5 edited 7-24
    public static final String CREATE_CONTEXT_TABLE =
            "CREATE TABLE " + TABLE_CONTEXT + " (" +
                    COLUMN_CONTEXT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_CONTEXT_CONFLICTID + " INTEGER, " +
                    COLUMN_CONTEXT_MAIN + " INTEGER, " +
                    COLUMN_CONTEXT_MAINSUB + " INTEGER, " +
                    COLUMN_CONTEXT_SUPPORT + " INTEGER, " +
                    COLUMN_CONTEXT_SUPPORTSUB + " INTEGER, " +
                    COLUMN_CONTEXT_DIRECTION + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_CONTEXT_CONFLICTID + ") REFERENCES " + TABLE_CONFLICT + "(" + COLUMN_CONFLICT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_CONTEXT_MAIN + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + ") " +
                    ")";
    // RESOLUTION GOALS TABLE // added 7-25
    public static final String CREATE_RESOLUTION_TABLE =
            "CREATE TABLE " + TABLE_RESOLUTION + " (" +
                    COLUMN_RESOLUTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_RESOLUTION_CONFLICTID + " INTEGER, " +
                    COLUMN_RESOLUTION_GOAL + " INTEGER, " +
                    COLUMN_RESOLUTION_GOALSUB + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_RESOLUTION_CONFLICTID + ") REFERENCES " + TABLE_CONFLICT + "(" + COLUMN_CONFLICT_ID+ "), " +
                    "FOREIGN KEY (" + COLUMN_RESOLUTION_GOAL + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + ") " +
                    ")";

    //FABULA ELEMENT TABLE
    public static final String CREATE_FABULAELEM_TABLE =
            "CREATE TABLE " + TABLE_FABULAElEM + " (" +
                    COLUMN_FABULAElEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_FABULAELEM_CONCEPTID + " INTEGER, " +
                    COLUMN_FABULAELEM_LABEL + " TEXT, " +
                    COLUMN_FABULAELEM_CATEGORY + " TEXT, " +
                    COLUMN_FABULAELEM_PARAMETERS + " TEXT, " +
                    COLUMN_FABULAELEM_PRECONDITIONS + " TEXT, " +
                    COLUMN_FABULAELEM_POSTCONDITIONS + " TEXT, " +
                    COLUMN_FABULAELEM_RECOMMENDATIONS + " TEXT, " +
                    COLUMN_FABULAELEM_ISNEGATED + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_FABULAELEM_CONCEPTID + ") REFERENCES " + TABLE_CONCEPT + "(" + COLUMN_CONCEPT_ID + ") " +
                    ")";
    //LINK TABLE
    public static final String CREATE_LINK_TABLE =
            "CREATE TABLE " + TABLE_LINK + " (" +
                    COLUMN_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_LINK_TYPE + " TEXT, " +
                    COLUMN_LINK_FABULAELEM1 + " INTEGER, " +
                    COLUMN_LINK_SUB1ID + " INTEGER, " +
                    COLUMN_LINK_FABULAELEM2 + " INTEGER, " +
                    COLUMN_LINK_SUB2ID + " INTEGER, " +
                    COLUMN_LINK_PRIORITY + " INTEGER, " +
                    COLUMN_LINK_PARAMS + " TEXT, " +
                    COLUMN_LINK_PRECONDITIONS + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_LINK_FABULAELEM1 + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_LINK_FABULAELEM2 + ") REFERENCES " + TABLE_FABULAElEM + "(" + COLUMN_FABULAElEM_ID + ") " +
                    ")";

    //BGOBJECT TABLE
    public static final String CREATE_BGOBJECT_TABLE =
            "CREATE TABLE " + TABLE_BGOBJECT + " (" +
                    COLUMN_BGOBJECT_BGID + " INTEGER NOT NULL, " +
                    COLUMN_BGOBJECT_OBJECTID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_BGOBJECT_BGID + ") REFERENCES " + TABLE_BACKGROUND + "(" + COLUMN_BACKGROUND_ID + ")," +
                    "FOREIGN KEY (" + COLUMN_BGOBJECT_OBJECTID + ") REFERENCES " + TABLE_OBJECT + "(" + COLUMN_OBJECT_ID + ")" +
                    ")";

//    //THEME TABLE
//    public static final String CREATE_THEME_TABLE =
//            "CREATE TABLE " + TABLE_THEME + " (" +
//                    COLUMN_THEME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    COLUMN_MORAL_LESSON + " TEXT, " +
//                    COLUMN_RELATED_BACKGROUND + " TEXT, " +
//                    COLUMN_RELATED_OBJECT + " TEXT, " +
//                    COLUMN_RELATED_TRAIT + " TEXT " +
//                    ")";
//    //EVENT TABLE
//    public static final String CREATE_EVENT_TABLE =
//            "CREATE TABLE " + TABLE_EVENT + " (" +
//                    COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    COLUMN_EVENT_NAME + " TEXT, " +
//                    COLUMN_MAX_AGENTS + " INTEGER, " +
//                    COLUMN_MAX_PATIENTS + " INTEGER, " +
//                    COLUMN_PRECONDITION + " TEXT, " +
//                    COLUMN_POSTCONDITION + " TEXT, " +
//                    COLUMN_INTERACTIONSCORE + " INTEGER, " +
//                    COLUMN_IRSCORE + " INTEGER, " +
//                    COLUMN_EVENT_TYPE + " TEXT, " +
//                    COLUMN_DIALOGUE_TYPE + " TEXT " +
//                    ")";
//
//    //TRAIT TABLE
//    public static final String CREATE_TRAIT_TABLE =
//            "CREATE TABLE " + TABLE_TRAIT + " (" +
//                    COLUMN_CHAR_ID + " INTEGER, " +
//                    COLUMN_TRAIT + " TEXT NOT NULL, " +
//                    "FOREIGN KEY (charID) REFERENCES Character(charID)" +
//                    ")";
//
//    //IR TABLE
//    public static final String CREATE_IR_TABLE =
//            "CREATE TABLE " + TABLE_IR + " (" +
//                    COLUMN_FROM + " INTEGER NOT NULL, " +
//                    COLUMN_TO + " INTEGER NOT NULL, " +
//                    COLUMN_RELATIONSHIP + " TEXT, " +
//                    COLUMN_SCORE + " INTEGER, " +
//                    "FOREIGN KEY (fromchar) REFERENCES Character(charID), " +
//                    "FOREIGN KEY (tochar) REFERENCES Character(charID) " +
//                    ")";
//
//
//    //NOUN TABLE
//    public static final String CREATE_NOUN_TABLE =
//            "CREATE TABLE " + TABLE_NOUN + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_NOUN_TYPE + " TEXT, " +
//                    COLUMN_NOUN_ISSingular + " INTEGER, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //PRONOUN TABLE
//    public static final String CREATE_PRONOUN_TABLE =
//            "CREATE TABLE " + TABLE_PRONOUN + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_PRONOUN_TYPE + " TEXT, " +
//                    COLUMN_PRONOUN_ISSingular + " INTEGER, " +
//                    COLUMN_PRONOUN_GENDER + " INTEGER, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //VERB TABLE
//    public static final String CREATE_VERB_TABLE =
//            "CREATE TABLE " + TABLE_VERB + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_VERB_TENSE + " TEXT, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //ADJECTIVE TABLE
//    public static final String CREATE_ADJECTIVE_TABLE =
//            "CREATE TABLE " + TABLE_ADJECTIVE + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_ADJECTIVE_TYPE + " TEXT, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //ADVERB TABLE
//    public static final String CREATE_ADVERB_TABLE =
//            "CREATE TABLE " + TABLE_ADVERB + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_ADVERB_TYPE + " TEXT, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //PREPOSITION TABLE
//    public static final String CREATE_PREPOSITION_TABLE =
//            "CREATE TABLE " + TABLE_PREPOSITION + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_PREPOSITION_TYPE + " TEXT, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //CONJUNCTION TABLE
//    public static final String CREATE_CONJUNCTION_TABLE =
//            "CREATE TABLE " + TABLE_CONJUNCTION + " (" +
//                    COLUMN_CONCEPT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
//                    COLUMN_CONJUNCTION_TYPE + " TEXT, " +
//                    "FOREIGN KEY (conceptID) REFERENCES Concept(conceptID)" +
//                    ")";
//
//    //DIALOGUE TABLE
//    public static final String CREATE_DIALOGUE_TABLE =
//            "CREATE TABLE " + TABLE_DIALOGUE + " (" +
//                    COLUMN_DIALOGUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    COLUMN_DIALOGUE + " TEXT, " +
//                    COLUMN_DIALOGUE_TYPE + " TEXT, " +
//                    COLUMN_DIALOGUE_ISSINGULAR + " INTEGER " +
//                    ")";

}
