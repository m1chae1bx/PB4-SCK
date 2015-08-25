package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;
import model.ontologymodel.SemanticRelation;
import model.storymodel.Story;
import model.storyworldmodel.CharacterNew;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBField.DB_NAME, null, 1);
    }

//	commented on 6-22
//	public DBHelper(Context context, String name,
//			CursorFactory factory, int version) {
//		super(context, name, factory, version);
//	}

    private void initializeStory(SQLiteDatabase db) {
        Story s = new Story();

        //s.addStory(db, "Bravery", "Kiddo101", "drawable/sample1", "Once upon a time, Piggie was a coward. But because of something Piggie learns to be brave.");
        //s.addStory(db, "Sharing", "Kiddo101", "drawable/sample1", "Once upon a time, Monkie was selfish. But because of something Monkie learns to share.");
        //s.addStory(db, "Honesty", "Kiddo101", "drawable/sample1", "Once upon a time, Rabbie was a liar. But because of something Rabbie learns to be honest.");
    }

//    private void initializeDialogue(SQLiteDatabase db) {
//        Dialogue d = new Dialogue();
//
//        d.addDialogue(db, "<character> said, \"I am <emotion>\"", Dialogue.EMOTION, Dialogue.SINGULAR);
//        d.addDialogue(db, "<character> said, \"I am <emotion> <> I am also <emotion2>\"", Dialogue.EMOTION, Dialogue.SINGULAR);
//        d.addDialogue(db, "<character> said, \"I am <emotion> <> At the same time, I am also <emotion2>\"", Dialogue.EMOTION, Dialogue.SINGULAR);
//        d.addDialogue(db, "<character> said, \"We are <emotion>\"", Dialogue.EMOTION, Dialogue.PLURAL);
//        d.addDialogue(db, "<character> said, \"We are <emotion> <> We are also <emotion2>\"", Dialogue.EMOTION, Dialogue.PLURAL);
//        d.addDialogue(db, "<character> said, \"We are <emotion> <> At the same time, We are <emotion2>\"", Dialogue.EMOTION, Dialogue.PLURAL);
//        d.addDialogue(db, "<character> said, \"We are <emotion> <> Also, We are <emotion2>\"", Dialogue.EMOTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"I want to <action> <object>\"<>"
//                + "<character> said, \"No, Let us try <object>\"<>"
//                + "<character> said, \"I do not like that. I want <object>\"<>"
//                + "<character> said, \"Let us just stick with <object>\"", Dialogue.DELIBERATION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \" I think I <action> <object>\"<>"
//                + "<character> said, \"No, I think it is <object>\"<>"
//                + "<character> said, \"Hmm, I think it is <object>\"<>"
//                + "<character> said, \"You are all wrong. I <action> <object>\"", Dialogue.NEGOTIATION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \" Do you want to <action> together?\"<>"
//                + "<character> said, \"Come on join us <maincharacter>\"<>"
//                + "<maincharacter> said, \"Thank you. But I will have to decline\"<>"
//                + "<character> said, \"Come on, it's more fun doing things together\"<>"
//                + "<maincharacter> said, \"Okay then, let us <action>\"", Dialogue.PERSUASION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"We should try to <action>\"<>"
//                + "<maincharacter> said, \"I don't want to\"<>"
//                + "<character> said, \"Come on, we will do it together\"<>"
//                + "<maincharacter> said, \"Okay, let us <action>\"", Dialogue.PERSUASION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"look , the <location> is so <adjlocation>\"<>"
//                + "<character> said, \"I agree, it is <adjlocation>\"<>"
//                + "<character> said, \"This <time> is <adjtime> too\"", Dialogue.INTRODUCTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"The <location> is <adjlocation>\"<>"
//                + "<character> said, \"You are right. It is <adjlocation>\"<>"
//                + "<character> said, \"This <time> is <adjtime> too\"", Dialogue.INTRODUCTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"You learned a new lesson today <maincharacter>\"<>"
//                + "<character> said, \"Be <theme>\"<>"
//                + "<maincharacter> said, \"Yes, I will be <theme> from now on\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<maincharacter> said, \"I learned a new lesson. It is to be <theme>\"<>"
//                + "<character> said, \"Yes, That is right <maincharacter>\"<>"
//                + "<character> said, \"You should be <theme>\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"You should be <theme> from now on <maincharacter>\"<>"
//                + "<character> said, \"Yes, That is right <maincharacter>\"<>"
//                + "<maincharacter> said, \"Yes, I should be <theme>\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<maincharacter> said, \"I learned a new lesson. It is to be <theme>\"<>"
//                + "<character> said, \"That is nice <maincharacter>\"<>"
//                + "<character> said, \"Yes, be <theme> now <maincharacter>\"<>"
//                + "<character> said, \"Yes, you should be <theme>\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<character> said, \"What did you learn <maincharacter>?\"<>"
//                + "<maincharacter> said, \"I learned to be <theme>\"<>"
//                + "<character> said, \"Yes, that's right. Be <theme> from now on\"<>"
//                + "<character> said, \"Good job <maincharacter>!\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//
//        d.addDialogue(db, "<maincharacter> said, \"I learned a new lesson today. It is to be <theme>\"<>"
//                + "<character> said, \"That is right <maincharacter>\"<>"
//                + "<character> said, \"You should be <theme> now\"", Dialogue.RESOLUTION, Dialogue.PLURAL);
//    }

    // edited starting on 6-20
    private void initializeCharacter(SQLiteDatabase db) { // todo edit characters
        DBInitializeNew.addCharacter(db, 7, "drawable/char_monkey");
        DBInitializeNew.addCharacter(db, 3, "drawable/char_rabbit");
        DBInitializeNew.addCharacter(db, 4, "drawable/char_panda");

//      commented on 6-20
//		c.addCharacter(db, "Jerson", Character.MALE, "drawable/char_monkey", "brave,dishonest,clumsy,independent");
//		c.addCharacter(db, "Joanna", Character.FEMALE, "drawable/char_rabbit", "helpful,shy,independent");
//		c.addCharacter(db, "Bianca", Character.FEMALE, "drawable/char_pig", "coward,untidy");
//		c.addCharacter(db, "Nuju", Character.MALE, "drawable/char_panda", "boastful,selfish");
//		c.addCharacter(db, "Pauline", Character.FEMALE, "drawable/char_turtle", "energetic,clumsy");
//
//		c.addRelationship(db, 1,2, 5, Relationship.ACQUAINTANCE);
//		c.addRelationship(db, 1,3, 7, Relationship.FRIEND);
//		c.addRelationship(db, 1,4, 7, Relationship.FRIEND);
//		c.addRelationship(db, 1,5, 7, Relationship.FRIEND);
//
//		c.addRelationship(db, 2,1, 5, Relationship.ACQUAINTANCE);
//		c.addRelationship(db, 2,3,6, Relationship.FRIEND);
//		c.addRelationship(db, 2,4,6, Relationship.FRIEND);
//		c.addRelationship(db, 2,5,6, Relationship.FRIEND);
//
//		c.addRelationship(db, 3,1, 6, Relationship.MOTHER);
//		c.addRelationship(db, 3,2, 7, Relationship.FRIEND);
//		c.addRelationship(db, 3,4,6, Relationship.FRIEND);
//		c.addRelationship(db, 3,5,6, Relationship.FRIEND);
//
//		c.addRelationship(db, 4,1, 6, Relationship.FRIEND);
//		c.addRelationship(db, 4,2, 7, Relationship.FRIEND);
//		c.addRelationship(db, 4,3,6, Relationship.FRIEND);
//		c.addRelationship(db, 4,5,6, Relationship.FRIEND);
//
//		c.addRelationship(db, 5,1, 6, Relationship.FRIEND);
//		c.addRelationship(db, 5,2, 7, Relationship.FRIEND);
//		c.addRelationship(db, 5,3,6, Relationship.FRIEND);
//		c.addRelationship(db, 5,4,6, Relationship.FRIEND);
    }

    // edited starting on 6-20
    private void initializeBackground(SQLiteDatabase db) {
        DBInitializeNew.addBackground(db, 2, "drawable/bg_beach");

//      commented on 6-20
//		Background b = new Background();
//
//		b.addBackground(db, "bedroom", "drawable/bg_bedroom");
//		b.addBackground(db, "camp", "drawable/bg_camp");
//		b.addBackground(db, "classroom", "drawable/bg_classroom");
//		b.addBackground(db, "dining room", "drawable/bg_diningroom");
//
//		b.addBackground(db, "beach", "drawable/bg_beach");
//		b.addBackground(db, "playground", "drawable/bg_playground");
    }

    // edited starting on 6-20
    private void initializeObject(SQLiteDatabase db) {
        DBInitializeNew.addObject(db, 44, "drawable/obj_bbq"); // todo generate appropriate images

//      commented on 6-20
//      Object o = new Object();
//
//		o.addObject(db, "flashlight", "type", "drawable/obj_flashlight");
//		o.addObject(db, "ball", "type", "drawable/obj_ball");
//		o.addObject(db, "book", "type", "drawable/obj_book");
//
//		o.addObject(db, "apple", "type", "drawable/obj_apple");
//		o.addObject(db, "barbecue", "type", "drawable/obj_bbq");
//		o.addObject(db, "glass of water", "type", "drawable/obj_glassofwater");
//		o.addObject(db, "broom", "type", "drawable/obj_broom");
//		o.addObject(db, "firewood", "type", "drawable/obj_firewood");
//		o.addObject(db, "magnifying glass", "type", "drawable/obj_magnifyingglass");
//		o.addObject(db, "pail and shovel", "type", "drawable/obj_pailandshovel");
//		o.addObject(db, "test", "type", "drawable/obj_paper");
//		o.addObject(db, "plates", "type", "drawable/obj_plates");
    }

    private void initializeBGObject(SQLiteDatabase db) {
        DBInitializeNew.addBackgroundObject(db, 1, 1);

//		Background b = new Background();
//
//		b.addObject(db, 1, 1);
//		b.addObject(db, 1, 2);
//		b.addObject(db, 2, 1);
//		b.addObject(db, 2, 2);
//		b.addObject(db, 3, 3);
//
//		b.addObject(db, 2, 8);
//		b.addObject(db, 2, 5);
//		b.addObject(db, 2, 7);
//		b.addObject(db, 1, 3);
//		b.addObject(db, 1, 6);
//		b.addObject(db, 3, 9);
//		b.addObject(db, 3, 11);
//		b.addObject(db, 4, 6);
//		b.addObject(db, 4, 4);
//		b.addObject(db, 4, 12);
//		b.addObject(db, 6, 10);
//		b.addObject(db, 6, 2);
//		b.addObject(db, 5, 10);
//		b.addObject(db, 5, 2);
//		b.addObject(db, 5, 5);
    }

    private void initializeConcept(SQLiteDatabase db) {
        DBInitializeNew.addConcept(db, "accept"); // 1
        DBInitializeNew.addConcept(db, "beach"); // 2
        DBInitializeNew.addConcept(db, "Bianca"); // 3
        DBInitializeNew.addConcept(db, "Carl"); // 4
        DBInitializeNew.addConcept(db, "character"); // 5
        DBInitializeNew.addConcept(db, "clean"); // 6
        DBInitializeNew.addConcept(db, "Coco"); // 7
        DBInitializeNew.addConcept(db, "dance"); // 8
        DBInitializeNew.addConcept(db, "eat"); // 9
        DBInitializeNew.addConcept(db, "wash"); // 10
        DBInitializeNew.addConcept(db, "female"); // 11
        DBInitializeNew.addConcept(db, "food"); // 12
        DBInitializeNew.addConcept(db, "go to"); // 13
        DBInitializeNew.addConcept(db, "hear"); // 14
        DBInitializeNew.addConcept(db, "hungry"); // 15
        DBInitializeNew.addConcept(db, "invite"); // 16
        DBInitializeNew.addConcept(db, "male"); // 17
        DBInitializeNew.addConcept(db, "negative"); // 18
        DBInitializeNew.addConcept(db, "peer"); // 19
        DBInitializeNew.addConcept(db, "picnic"); // 20
        DBInitializeNew.addConcept(db, "play"); // 21
        DBInitializeNew.addConcept(db, "positive"); // 22
        DBInitializeNew.addConcept(db, "search for"); // 23
        DBInitializeNew.addConcept(db, "see"); // 24
        DBInitializeNew.addConcept(db, "swim"); // 25
        DBInitializeNew.addConcept(db, "persuade"); // 26
        DBInitializeNew.addConcept(db, "tired"); // 27
        DBInitializeNew.addConcept(db, "untidy"); // 28
        DBInitializeNew.addConcept(db, "social activity"); // 29
        DBInitializeNew.addConcept(db, "generous"); // 30
        DBInitializeNew.addConcept(db, "stingy"); // 31
        DBInitializeNew.addConcept(db, "humble"); // 32
        DBInitializeNew.addConcept(db, "boastful"); // 33
        DBInitializeNew.addConcept(db, "shy"); // 34
        DBInitializeNew.addConcept(db, "confident"); // 35
        DBInitializeNew.addConcept(db, "outdoor"); // 36
        DBInitializeNew.addConcept(db, "indoor"); // 37
        DBInitializeNew.addConcept(db, "location"); // 38
        DBInitializeNew.addConcept(db, "hand"); // 39
        DBInitializeNew.addConcept(db, "object"); // 40
        DBInitializeNew.addConcept(db, "edible"); // 41
        DBInitializeNew.addConcept(db, "status"); // 42
        DBInitializeNew.addConcept(db, "clean_2"); // 43
        DBInitializeNew.addConcept(db, "barbecue"); // 44
        DBInitializeNew.addConcept(db, "find"); // 45
        DBInitializeNew.addConcept(db, "join"); // 46
        DBInitializeNew.addConcept(db, "happy"); // 47
        DBInitializeNew.addConcept(db, "fun"); // 48
        DBInitializeNew.addConcept(db, "engage"); // 49
        DBInitializeNew.addConcept(db, "encourage"); // 50
        DBInitializeNew.addConcept(db, "lazy"); // 51
        DBInitializeNew.addConcept(db, "refuse"); // 52
        DBInitializeNew.addConcept(db, "convince"); // 53
        DBInitializeNew.addConcept(db, "explain"); // 54
        DBInitializeNew.addConcept(db, "sorry"); // 55
        DBInitializeNew.addConcept(db, "full"); // 56
        DBInitializeNew.addConcept(db, "throw"); // 57
        DBInitializeNew.addConcept(db, "clear"); // 58
        DBInitializeNew.addConcept(db, "settle down"); // 59
        DBInitializeNew.addConcept(db, "challenged"); // 60



//		Concept c = new Concept();
//
//		//noun
//		c.addConcept(db, "#character","#character", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "ball", "ball",  Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "puzzle","puzzle", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "marshmallow","marshmallow", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "apple","apple", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "hotdog","hotdog", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "buzz","buzz", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "hoot","hoot", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "owl","owl", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "bedroom","bedroom", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "camp","camp", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "toy","toy", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "food","food", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "sound","sound", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "animal", "animal",Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "place","place", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "bed", "bed", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "flashlight","flashlight", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "tooth","tooth", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "toothbrush","toothbrush", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "pail and shovel","pail and shovel", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "classroom","classroom", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "playground","playground", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "beach","beach", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "magnifying glass","magnifying glass", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "pail and shovel","pail and shovel", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "firewood","firewood", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "book","book", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "barbeque","barbeque", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "test","test", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "broom","broom", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "puzzle","puzzle", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "plates","plates", Concept.NOUN, "type", Concept.SINGULAR);
//
//
//
//		//pronoun
//
//		c.addConcept(db, "he", "he", Concept.PRONOUN, "type",  Concept.SINGULAR, Concept.MALE);
//		c.addConcept(db, "she", "she",  Concept.PRONOUN, "type",  Concept.SINGULAR, Concept.FEMALE);
//
//		//verb,adjective,adverb,preposition,conjuction
//
//		c.addConcept(db, "sleep","sleep", Concept.VERB, "tense");
//		c.addConcept(db, "clean","clean", Concept.VERB, "tense");
//		c.addConcept(db, "search","search", Concept.VERB, "tense");
//		c.addConcept(db, "explore", "investigate", Concept.VERB, "tense");
//		c.addConcept(db, "search", "investigate", Concept.VERB, "tense");
//		c.addConcept(db, "investigate", "investigate", Concept.VERB, "tense");
//		c.addConcept(db, "brush","brush", Concept.VERB, "tense");
//		c.addConcept(db, "play","play", Concept.VERB, "tense");
//		c.addConcept(db, "smile","smile", Concept.VERB, "tense");
//		c.addConcept(db, "bring", "bring", Concept.VERB, "tense");
//		c.addConcept(db, "share", "share", Concept.VERB, "tense");
//		c.addConcept(db, "brush","brush",  Concept.VERB, "tense");
//		c.addConcept(db, "fight", "fight",  Concept.VERB, "present");
//		c.addConcept(db, "wake up", "wake up",  Concept.VERB, "present");
//		c.addConcept(db, "hear", "hear",  Concept.VERB, "present");
//
//		c.addConcept(db, "talk", "talk",  Concept.VERB, "present");
//		c.addConcept(db, "walk", "walk",  Concept.VERB, "present");
//		c.addConcept(db, "borrow", "borrow",  Concept.VERB, "present");
//
//		c.addConcept(db, "brave", "brave", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "scared","scared", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "glad", "happy", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "sleepy", "sleepy",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "beautiful","beautiful", Concept.ADJECTIVE, "type");
//
//
//		c.addConcept(db, "dining room","dining room", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "glass of water","glass of water", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "fragile thing", "fragile thing", Concept.NOUN, "type", Concept.SINGULAR);
//		c.addConcept(db, "afternoon", "afternoon", Concept.NOUN, "type", Concept.SINGULAR);
//
//		c.addConcept(db, "break", "break",  Concept.VERB, "tense");
//		c.addConcept(db, "blame", "blame", Concept.VERB, "tense");
//		c.addConcept(db, "cry", "cry", Concept.VERB, "tense");
//		c.addConcept(db, "apologize to","apologize to", Concept.VERB, "tense");
//		c.addConcept(db, "scold","scold", Concept.VERB, "tense");
//		c.addConcept(db, "ask", "ask", Concept.VERB, "tense");
//		c.addConcept(db, "admit to","admit to", Concept.VERB, "tense");
//
//		c.addConcept(db, "guilty", "guilty", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "curious","curious", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "honest","honest", Concept.ADJECTIVE, "tense");
//
//		c.addConcept(db, "breakable", "breakable", Concept.ADJECTIVE, "type");
//
//		/************************/
//
//		c.addConcept(db, "sad", "sad", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "hurt", "hurt", Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "insulted", "insulted", Concept.ADJECTIVE, "tense");
//
//		c.addConcept(db, "eat", "eat",  Concept.VERB, "tense");
//		c.addConcept(db, "invite", "invite",  Concept.VERB, "tense");
//		c.addConcept(db, "decline", "decline",  Concept.VERB, "tense");
//		c.addConcept(db, "accept", "accept",  Concept.VERB, "tense");
//		c.addConcept(db, "boast to", "boast to",  Concept.VERB, "tense");
//		c.addConcept(db, "insult", "insult",  Concept.VERB, "tense");
//		c.addConcept(db, "fail", "fail",  Concept.VERB, "tense");
//		c.addConcept(db, "repent", "repent",  Concept.VERB, "tense");
//		c.addConcept(db, "look", "look",  Concept.VERB, "tense");
//		c.addConcept(db, "find", "find",  Concept.VERB, "tense");
//		c.addConcept(db, "search", "search",  Concept.VERB, "tense");
//		c.addConcept(db, "prepare", "prepare",  Concept.VERB, "tense");
//
//
//		c.addConcept(db, "present", "present",  Concept.VERB, "tense");
//		c.addConcept(db, "praise", "praise",  Concept.VERB, "tense");
//		c.addConcept(db, "practice", "practice",  Concept.VERB, "tense");
//		c.addConcept(db, "help", "help",  Concept.VERB, "tense");
//		c.addConcept(db, "lose", "lose",  Concept.VERB, "tense");
//		c.addConcept(db, "spot", "see",  Concept.VERB, "tense");
//
//		c.addConcept(db, "take", "take",  Concept.VERB, "tense");
//
//		c.addConcept(db, "exercise", "exercise",  Concept.VERB, "tense");
//		c.addConcept(db, "toothache", "toothache",  Concept.VERB, "tense");
//
//		//added for previously added stories
//		c.addConcept(db, "helpful", "helpful",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "worried", "worried",  Concept.ADJECTIVE, "tense");
//
//		//story 5
////		c.addConcept(db, "apologize to","apologize to", Concept.VERB, "tense");
//		c.addConcept(db, "well-behaved", "well-behaved",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "sorry", "sorry",  Concept.ADJECTIVE, "tense");
//
//		//story 6
//		c.addConcept(db, "independent", "independent",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "play alone", "play alone",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "innocent", "innocent",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "lonely", "lonely",  Concept.ADJECTIVE, "tense");
//
//		//story 7
//		c.addConcept(db, "borrow from", "borrow from",  Concept.VERB, "tense");
//		c.addConcept(db, "lend", "lend",  Concept.VERB, "tense");
//		c.addConcept(db, "selfish", "selfish",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "irritated", "irritated",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "hesitant", "hesitant",  Concept.ADJECTIVE, "tense");
//
//		//story 8
//		c.addConcept(db, "watch", "watch",  Concept.VERB, "tense");
//		c.addConcept(db, "shy", "shy",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "friendly", "friendly",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "envious", "envious",  Concept.ADJECTIVE, "tense");
//		c.addConcept(db, "thankful", "thankful",  Concept.ADJECTIVE, "tense");
//
    }

    private void initializeSemRep(SQLiteDatabase db) {
        DBInitializeNew.addSemRep(db, 2, SemanticRelation.IS_A, 38, -1, null);  // beach + location +
        DBInitializeNew.addSemRep(db, 2, SemanticRelation.IS_A, 36, -1, null);  // beach + outdoor +
        DBInitializeNew.addSemRep(db, 12, SemanticRelation.IS_A, 40, -1, null);  // food + object +
        DBInitializeNew.addSemRep(db, 12, SemanticRelation.HAS_PROPERTY, 41, -1, null);  // food + edible +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.INSTANCE_OF, 5, -1, null);  // Coco + character +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.HAS_TRAIT, 28, -1, null);  // Coco + untidy +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.HAS_TRAIT, 32, -1, null);  // Coco + humble +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.HAS_TRAIT, 34, -1, null);  // Coco + shy +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.PREFERS, 25, -1, null);  // Coco + swim +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.PREFERS, 8, -1, null);  // Coco + dance +
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.OCCUPIES_STATUS, 19, 4, null);  // Coco + peer + Carl
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.OCCUPIES_STATUS, 19, 3, null);  // Coco + peer + Bianca
        DBInitializeNew.addSemRep(db, 7, SemanticRelation.GENDER_IS, 17, -1, null);  // Coco + male +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.HAS_TRAIT, 6, -1, null);  // Carl + clean +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.HAS_TRAIT, 30, -1, null);  // Carl + generous +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.GENDER_IS, 17, -1, null);  // Carl + male +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.PREFERS, 9, -1, null);  // Carl + eat +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.PREFERS, 20, -1, null);  // Carl + picnic +
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.OCCUPIES_STATUS, 19, 7, null);  // Carl + peer + Coco
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.OCCUPIES_STATUS, 19, 3, null);  // Carl + peer + Bianca
        DBInitializeNew.addSemRep(db, 4, SemanticRelation.INSTANCE_OF, 5, -1, null);  // Carl + character +
        DBInitializeNew.addSemRep(db, 2, SemanticRelation.INSTANCE_OF, 38, -1, null);  // beach + location +
        DBInitializeNew.addSemRep(db, 44, SemanticRelation.INSTANCE_OF, 40, -1, null);  // barbecue + object +
        DBInitializeNew.addSemRep(db, 10, SemanticRelation.TARGETS, 39, -1, null);  // wash + hand +
        DBInitializeNew.addSemRep(db, 9, SemanticRelation.IS_A, 29, -1, null);  // eat + social activity +
        DBInitializeNew.addSemRep(db, 9, SemanticRelation.TARGETS, 41, -1, null);  // eat + edible +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 9, -1, null);  // character + eat +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 21, -1, null);  // character + play +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 13, -1, null);  // character + go to +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 23, -1, null);  // character + search for +
        DBInitializeNew.addSemRep(db, 19, SemanticRelation.IS_A, 42, -1, null);  // peer + status +
        DBInitializeNew.addSemRep(db, 39, SemanticRelation.PART_OF, 5, -1, null);  // hand + character +
        DBInitializeNew.addSemRep(db, 44, SemanticRelation.IS_A, 12, -1, null);  // barbecue + food +
        DBInitializeNew.addSemRep(db, 36, SemanticRelation.IS_A, 38, -1, null);  // outdoor + location +
        DBInitializeNew.addSemRep(db, 37, SemanticRelation.IS_A, 38, -1, null);  // indoor + location +
        DBInitializeNew.addSemRep(db, 36, SemanticRelation.OPPOSITE_OF, 37, -1, null);  // outdoor + indoor +
        DBInitializeNew.addSemRep(db, 25, SemanticRelation.DONE_AT, 2, -1, null);  // swim + beach +
        DBInitializeNew.addSemRep(db, 20, SemanticRelation.DONE_AT, 2, -1, null);  // picnic + beach +
        DBInitializeNew.addSemRep(db, 8, SemanticRelation.DONE_AT, 2, -1, null);  // dance + beach +
        DBInitializeNew.addSemRep(db, 6, SemanticRelation.POLARITY, 22, -1, null);  // clean + positive +
        DBInitializeNew.addSemRep(db, 28, SemanticRelation.POLARITY, 18, -1, null);  // untidy + negative +
        DBInitializeNew.addSemRep(db, 30, SemanticRelation.POLARITY, 22, -1, null);  // generous + positive +
        DBInitializeNew.addSemRep(db, 31, SemanticRelation.POLARITY, 18, -1, null);  // stingy + negative +
        DBInitializeNew.addSemRep(db, 34, SemanticRelation.POLARITY, 18, -1, null);  // shy + negative +
        DBInitializeNew.addSemRep(db, 35, SemanticRelation.POLARITY, 22, -1, null);  // confident + positive +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 8, -1, null);  // character + dance +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 26, -1, null);  // character + persuade +
        DBInitializeNew.addSemRep(db, 5, SemanticRelation.CAPABLE_OF, 16, -1, null);  // character + invite +
        DBInitializeNew.addSemRep(db, 32, SemanticRelation.POLARITY, 22, -1, null);  // humble + positive +
        DBInitializeNew.addSemRep(db, 6, SemanticRelation.OPPOSITE_OF, 28, -1, null);  // clean + untidy +
        DBInitializeNew.addSemRep(db, 30, SemanticRelation.OPPOSITE_OF, 31, -1, null);  // generous + stingy +
        DBInitializeNew.addSemRep(db, 35, SemanticRelation.OPPOSITE_OF, 34, -1, null);  // confident + shy +
        DBInitializeNew.addSemRep(db, 33, SemanticRelation.POLARITY, 18, -1, null);  // boastful + negative +
        DBInitializeNew.addSemRep(db, 32, SemanticRelation.OPPOSITE_OF, 33, -1, null);  // humble + boastful +


//      SemanticRelation s = new SemanticRelation();
//
//		// IsA
//		s.addSemRep(db, "ball", SemanticRelation.IS_A, "toy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "puzzle", SemanticRelation.IS_A, "toy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "marshmallow", SemanticRelation.IS_A, "food", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "apple", SemanticRelation.IS_A, "food", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hotdog", SemanticRelation.IS_A, "food", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "buzz", SemanticRelation.IS_A, "sound", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hoot", SemanticRelation.IS_A, "sound", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "owl", SemanticRelation.IS_A, "animal", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "camp", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "morning", SemanticRelation.IS_A, "time", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "afternoon", SemanticRelation.IS_A, "time", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "evening", SemanticRelation.IS_A, "time", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "classroom", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.IS_A, "place", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "pail and shovel", SemanticRelation.IS_A, "toy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "barbeque", SemanticRelation.IS_A, "food", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "puzzle", SemanticRelation.IS_A, "toy", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasProperty
//		s.addSemRep(db, "camp", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "camp", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "camp", SemanticRelation.HAS_PROPERTY, "scary", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "camp", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "camp", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "morning", SemanticRelation.HAS_PROPERTY, "sunny", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "morning", SemanticRelation.HAS_PROPERTY, "rainy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "afternoon", SemanticRelation.HAS_PROPERTY, "sunny", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "afternoon", SemanticRelation.HAS_PROPERTY, "cloudy", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "classroom", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "classroom", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "classroom", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "classroom", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//
//		// CapableOf
//		/** A **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "admit to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "apologize", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "ask", SemanticRelation.PASSIVE_CATEGORY);
//		/** B **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "blame", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "boast to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "borrow", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "bring", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "brush", SemanticRelation.PASSIVE_CATEGORY);
//		/** C **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "cry", SemanticRelation.PASSIVE_CATEGORY);
//		/** D **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "decline", SemanticRelation.PASSIVE_CATEGORY);
//		/** E **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "eat", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "exercise", SemanticRelation.PASSIVE_CATEGORY);
//		/** F **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "fail", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "find", SemanticRelation.PASSIVE_CATEGORY);
//		/** G **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "give", SemanticRelation.PASSIVE_CATEGORY);
//		/** H **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "help", SemanticRelation.PASSIVE_CATEGORY);
//		/** I **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "insult", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "investigate", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "invite", SemanticRelation.PASSIVE_CATEGORY);
//		/** K **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "kick", SemanticRelation.PASSIVE_CATEGORY);
//		/** L **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "look", SemanticRelation.PASSIVE_CATEGORY);
//		/** P **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "play", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "praise", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "practice", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "present", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "prepare", SemanticRelation.PASSIVE_CATEGORY);
//		/** R **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "repent", SemanticRelation.PASSIVE_CATEGORY);
//		/** S **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "search", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "scold", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "see", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "share", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "sleep", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "smile", SemanticRelation.PASSIVE_CATEGORY);
//		/** T **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "take", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "talk", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "toothache", SemanticRelation.PASSIVE_CATEGORY);
//		/** W **/
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "walk", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "wake up", SemanticRelation.PASSIVE_CATEGORY);
//
//		// Causes
//		s.addSemRep(db, "happy", SemanticRelation.CAUSES, "play", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "sleepy", SemanticRelation.CAUSES, "sleep", SemanticRelation.PASSIVE_CATEGORY);
//
//		// UsedFor
//		s.addSemRep(db, "bed", SemanticRelation.USED_FOR, "sleep", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "flashlight", SemanticRelation.USED_FOR, "search", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "flashlight", SemanticRelation.USED_FOR, "investigate", SemanticRelation.PASSIVE_CATEGORY);
//
//		// CapableOfReceivingAction
//		s.addSemRep(db, "tooth", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "brush", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "place", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "doll", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "play", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "ball", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "play", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "ball", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "ball", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "borrow", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "barbeque", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "apple", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "marshmallow", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hotdog", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);
//
//		// CapableOfReceivingAction
//		s.addSemRep(db, "glass of water", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "plate", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "toy", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "book", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "book", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "find", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "book", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "look", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "book", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "borrow", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "book", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "prepare", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "paper", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "tooth", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "hurt", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "test", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "fail", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "test", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "take", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "owl", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "see", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "doll", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "play", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "tiger", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "owl", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "snake", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "unicorn", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "car", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "play", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "pail and shovel", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "pail and shovel", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "find", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "pail and shovel", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "look", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "pail and shovel", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "borrow", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "paper", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "prepare", SemanticRelation.PASSIVE_CATEGORY);
//
//
//
//		/*****************************/
//
//		// IsA
//
//		// HasProperty
//
//		// CapableOf
//
//
//		// Causes
//		s.addSemRep(db, "sad", SemanticRelation.CAUSES, "cry", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hurt", SemanticRelation.CAUSES, "cry", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "shy", SemanticRelation.CAUSES, "decline", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "sad", SemanticRelation.CAUSES, "accept", SemanticRelation.PASSIVE_CATEGORY);
//		//s.addSemRep(db, "insulted", SemanticRelation.CAUSES, "scold", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "test", SemanticRelation.CAUSES, "brag", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "presentation", SemanticRelation.CAUSES, "practice", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "perform", SemanticRelation.CAUSES, "praise", SemanticRelation.PASSIVE_CATEGORY);
//
//
//
//		// UsedFor
//		s.addSemRep(db, "toothbrush", SemanticRelation.USED_FOR, "brush", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "paper", SemanticRelation.USED_FOR, "test", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "toothbrush", SemanticRelation.USED_FOR, "brush", SemanticRelation.PASSIVE_CATEGORY);
//
//
//		// ConflictOf
//		s.addSemRep(db, "careful", SemanticRelation.CONFLICT_OF, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "clean", SemanticRelation.CONFLICT_OF, "hurt", SemanticRelation.PASSIVE_CATEGORY);
////		s.addSemRep(db, "friendly", SemanticRelation.CONFLICT_OF, "invite", SemanticRelation.PASSIVE_CATEGORY);
////		s.addSemRep(db, "humble", SemanticRelation.CONFLICT_OF, "scold", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "confident", SemanticRelation.CONFLICT_OF, "practice", SemanticRelation.PASSIVE_CATEGORY);		s.addSemRep(db, "brave", SemanticRelation.CONFLICT_OF, "scared", SemanticRelation.PASSIVE_CATEGORY);
////		s.addSemRep(db, "honest", SemanticRelation.CONFLICT_OF, "guilty", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "humble", SemanticRelation.CONFLICT_OF, "boast to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "neat", SemanticRelation.CONFLICT_OF, "hurt", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasResolution
//		s.addSemRep(db, "lose", SemanticRelation.HAS_RESOLUTION, "find", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hurt", SemanticRelation.HAS_RESOLUTION, "brush", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "invite", SemanticRelation.HAS_RESOLUTION, "make new friends", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "scold", SemanticRelation.HAS_RESOLUTION, "repent", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "help", SemanticRelation.HAS_RESOLUTION, "praise", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "practice", SemanticRelation.HAS_RESOLUTION, "present", SemanticRelation.PASSIVE_CATEGORY);		s.addSemRep(db, "scared", SemanticRelation.HAS_RESOLUTION, "investigate", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "guilty", SemanticRelation.HAS_RESOLUTION, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "boast to", SemanticRelation.HAS_RESOLUTION, "fail", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hurt", SemanticRelation.HAS_RESOLUTION, "brush", SemanticRelation.PASSIVE_CATEGORY);
//
//		// MotivatedByGoal
//
//
//		/************/
//
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "lovely", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_PROPERTY, "big", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "small", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "dining room", SemanticRelation.HAS_PROPERTY, "big", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "small", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "big", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "playground", SemanticRelation.HAS_PROPERTY, "crowded", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "morning", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "afternoon", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "messy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "clean", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "crowded", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "nice", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "beach", SemanticRelation.HAS_PROPERTY, "beautiful", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "morning", SemanticRelation.HAS_PROPERTY, "windy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "afternoon", SemanticRelation.HAS_PROPERTY, "windy", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "morning", SemanticRelation.HAS_PROPERTY, "cold", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "afternoon", SemanticRelation.HAS_PROPERTY, "hot", SemanticRelation.PASSIVE_CATEGORY);
//
//
//		s.addSemRep(db, "boast to", SemanticRelation.HAS_SUBEVENT, "insult", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "fail", SemanticRelation.HAS_SUBEVENT, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//
//		s.addSemRep(db, "toothache", SemanticRelation.HAS_SUBEVENT, "cry", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "investigate", SemanticRelation.HAS_SUBEVENT, "see", SemanticRelation.PASSIVE_CATEGORY);
//		// ObstructedBy
//
//
//
//		// MotivatedByGoal
//		s.addSemRep(db, "sleep", SemanticRelation.MOTIVATED_BY_GOAL, "hear", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "wake up", SemanticRelation.MOTIVATED_BY_GOAL, "investigate", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "hear", SemanticRelation.MOTIVATED_BY_GOAL, "investigate", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "help", SemanticRelation.MOTIVATED_BY_GOAL, "practice", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "praise", SemanticRelation.MOTIVATED_BY_GOAL, "present", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "look", SemanticRelation.MOTIVATED_BY_GOAL, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "scold", SemanticRelation.MOTIVATED_BY_GOAL, "find", SemanticRelation.PASSIVE_CATEGORY);
//
//
//		s.addSemRep(db, "take", SemanticRelation.MOTIVATED_BY_GOAL, "boast to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "scold", SemanticRelation.MOTIVATED_BY_GOAL, "fail", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "exercise", SemanticRelation.MOTIVATED_BY_GOAL, "toothache", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "scold", SemanticRelation.MOTIVATED_BY_GOAL, "brush", SemanticRelation.PASSIVE_CATEGORY);
//
//		// ObstructedBy
//		s.addSemRep(db, "hear", SemanticRelation.OBSTRUCTED_BY, "play", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "investigate", SemanticRelation.OBSTRUCTED_BY, "rest", SemanticRelation.PASSIVE_CATEGORY);
//
//		s.addSemRep(db, "help", SemanticRelation.OBSTRUCTED_BY, "insult", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "borrow", SemanticRelation.OBSTRUCTED_BY, "look", SemanticRelation.PASSIVE_CATEGORY);
//
//
//		s.addSemRep(db, "firewood", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "look", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "firewood", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "find", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "barbecue", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "apple", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "eat", SemanticRelation.PASSIVE_CATEGORY);s.addSemRep(db, "firewood", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "look", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "glass of water", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "plates", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "break", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lose", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "find", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "look", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "borrow", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "magnifying glass", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "prepare", SemanticRelation.PASSIVE_CATEGORY);
//
//		//story 5
//		// IsA
//
//		// HasProperty
//
//		// CapableOf
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "fight", SemanticRelation.PASSIVE_CATEGORY);
//
//		// Causes
//		s.addSemRep(db, "angry", SemanticRelation.CAUSES, "fight", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "sorry", SemanticRelation.CAUSES, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//		// UsedFor
//
//		// CapableOfReceivingAction
//
//		// ConflictOf
//		s.addSemRep(db, "well-behaved", SemanticRelation.CONFLICT_OF, "wake up", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasResolution
//		s.addSemRep(db, "wake up", SemanticRelation.HAS_RESOLUTION, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//		// MotivatedByGoal
//		s.addSemRep(db, "sleep", SemanticRelation.MOTIVATED_BY_GOAL, "wake up", SemanticRelation.PASSIVE_CATEGORY); //****
//		s.addSemRep(db, "fight", SemanticRelation.MOTIVATED_BY_GOAL, "apologize to", SemanticRelation.PASSIVE_CATEGORY); //****
//
//		//story 6
//		// IsA
//
//		// HasProperty
//
//		// CapableOf
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "fight", SemanticRelation.PASSIVE_CATEGORY);
//
//		// Causes
//		s.addSemRep(db, "angry", SemanticRelation.CAUSES, "fight", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "sorry", SemanticRelation.CAUSES, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//		// UsedFor
//
//		// CapableOfReceivingAction
//
//		// ConflictOf
//		s.addSemRep(db, "honest", SemanticRelation.CONFLICT_OF, "blame", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasResolution
//		s.addSemRep(db, "blame", SemanticRelation.HAS_RESOLUTION, "admit to", SemanticRelation.PASSIVE_CATEGORY);
//
//		// MotivatedByGoal
//		s.addSemRep(db, "ask", SemanticRelation.MOTIVATED_BY_GOAL, "blame", SemanticRelation.PASSIVE_CATEGORY); //****
//		s.addSemRep(db, "fight", SemanticRelation.MOTIVATED_BY_GOAL, "admit to", SemanticRelation.PASSIVE_CATEGORY); //****
//
//		//Subevent
//		s.addSemRep(db, "admit to", SemanticRelation.HAS_SUBEVENT, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//		//story 7
//		// IsA
//
//		// HasProperty
//
//		// CapableOf
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "borrow from", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "lend", SemanticRelation.PASSIVE_CATEGORY);
//
//		// Causes
//		s.addSemRep(db, "hesitant", SemanticRelation.CAUSES, "decline", SemanticRelation.PASSIVE_CATEGORY);
//
//		// UsedFor
//
//		// CapableOfReceivingAction
//		s.addSemRep(db, "pail and shovel", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lend", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "ball", SemanticRelation.CAPABLE_OF_RECEIVING_ACTION, "lend", SemanticRelation.PASSIVE_CATEGORY);
//
//		// ConflictOf
//		s.addSemRep(db, "generous", SemanticRelation.CONFLICT_OF, "decline", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasResolution
//		s.addSemRep(db, "decline", SemanticRelation.HAS_RESOLUTION, "lend", SemanticRelation.PASSIVE_CATEGORY);
//
//		// MotivatedByGoal
//		s.addSemRep(db, "cry", SemanticRelation.MOTIVATED_BY_GOAL, "lend", SemanticRelation.PASSIVE_CATEGORY); //****
//		s.addSemRep(db, "borrow", SemanticRelation.MOTIVATED_BY_GOAL, "decline", SemanticRelation.PASSIVE_CATEGORY); //****
//
//		//Subevent
////		s.addSemRep(db, "lend", SemanticRelation.HAS_SUBEVENT, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//
//		//story 8
//		// IsA
//
//		// HasProperty
//
//		// CapableOf
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "watch", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "#character", SemanticRelation.CAPABLE_OF, "accept", SemanticRelation.PASSIVE_CATEGORY);
//
//		// Causes
//		s.addSemRep(db, "shy", SemanticRelation.CAUSES, "accept", SemanticRelation.PASSIVE_CATEGORY);
//
//		// UsedFor
//
//		// CapableOfReceivingAction
//
//		// ConflictOf
//		s.addSemRep(db, "friendly", SemanticRelation.CONFLICT_OF, "watch", SemanticRelation.PASSIVE_CATEGORY);
//
//		// HasResolution
//		s.addSemRep(db, "watch", SemanticRelation.HAS_RESOLUTION, "accept", SemanticRelation.PASSIVE_CATEGORY);
//
//		// MotivatedByGoal
//		s.addSemRep(db, "play", SemanticRelation.MOTIVATED_BY_GOAL, "watch", SemanticRelation.PASSIVE_CATEGORY); //****
//		s.addSemRep(db, "invite", SemanticRelation.MOTIVATED_BY_GOAL, "accept", SemanticRelation.PASSIVE_CATEGORY); //****
//
//		//Subevent
////		s.addSemRep(db, "lend", SemanticRelation.HAS_SUBEVENT, "apologize to", SemanticRelation.PASSIVE_CATEGORY);
//		s.addSemRep(db, "bedroom", SemanticRelation.HAS_A, "bed", SemanticRelation.PASSIVE_CATEGORY); //****
//		s.addSemRep(db, "camp", SemanticRelation.HAS_A, "tent", SemanticRelation.PASSIVE_CATEGORY); //****
//
    }

    private void initializeGoalTrait(SQLiteDatabase db) {
        DBInitializeNew.addGoalTrait(db, 6, 2, 12);  // clean + beach + food
    }

    private void initializeConflict(SQLiteDatabase db) {
        DBInitializeNew.addConflictGoals(db, 1, 18, 0, 19, 0); // A:not_wash + G:persuade
    }

    private void initializeContext(SQLiteDatabase db) {
        DBInitializeNew.addContextGoals(db, 1, 20, 0, 13, 0, 1);  // G:join + G:invite
    }

    private void initializeResolution(SQLiteDatabase db) {
        DBInitializeNew.addResolution(db, 1, 21, 0);  // G:clean
    }

    private void initializeFabula(SQLiteDatabase db) {
        // Fabula Categories:
        //      ACTION, GOAL, EVENT, PERCEPTION, INTERNAL ELEMENT, OUTCOME
        // Fabula Parameters:
        //      AGENT, PATIENT, TARGET, INSTRUMENT?, LOCATION? ... // todo What about the parameters for primitives?

        // todo checkout recommendations later
        // todo how about goal postconditions: are they supposed to be conditions to tell if a goal is achieved?

        DBInitializeNew.addFabulaElement(db, "have_fun", 48, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent"), null, null, false);  // concept = fun
        DBInitializeNew.addFabulaElement(db, "swim", 25, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, Arrays.asList("agent:is_tired:true","agent:feeling:"+CharacterNew.FEELING_HAPPY), false);  // concept = swim
        DBInitializeNew.addFabulaElement(db, "tired", 27, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), Arrays.asList("agent:is_tired:true"), Arrays.asList("agent:is_hungry:true"), false);  // concept = tired
        DBInitializeNew.addFabulaElement(db, "hungry", 15, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), Arrays.asList("agent:is_hungry:true"), null, false);  // concept = hungry
        DBInitializeNew.addFabulaElement(db, "leave_hunger", 15, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent"), Arrays.asList("agent:is_hungry:true"), null, false);  // concept = hungry
        DBInitializeNew.addFabulaElement(db, "accept", 1, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = accept
        DBInitializeNew.addFabulaElement(db, "eat", 9, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent"), null, Arrays.asList("agent:social_opportunity:true"), false);  // concept = eat
        DBInitializeNew.addFabulaElement(db, "go_picnic", 20, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent"), Arrays.asList("agent:location:2"), null, false);  // concept = picnic
        DBInitializeNew.addFabulaElement(db, "hear", 14, FabulaElementNew.CATEGORY_PERCEPT, Arrays.asList("agent","patient"), null, null, false);  // concept = hear
        DBInitializeNew.addFabulaElement(db, "search_for", 23, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, null, false);  // concept = search for
        DBInitializeNew.addFabulaElement(db, "see", 24, FabulaElementNew.CATEGORY_EVENT, Arrays.asList("agent","patient"), null, null, false);  // concept = see
        DBInitializeNew.addFabulaElement(db, "see", 24, FabulaElementNew.CATEGORY_PERCEPT, Arrays.asList("agent","patient"), null, Arrays.asList("agent:has_perception:"+CharacterNew.PERCEPTION_SEE+"=#patient"), false);  // concept = see
        DBInitializeNew.addFabulaElement(db, "invite", 16, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent","patient"), Arrays.asList("agent:social_opportunity:true","agent:has_perception:"+CharacterNew.PERCEPTION_SEE+"=#patient","patient:has_perception:"+CharacterNew.PERCEPTION_SEE+"=#agent"), null, false);  // concept = invite
        DBInitializeNew.addFabulaElement(db, "invite", 16, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), Arrays.asList("agent:current_goal:13"), null, false);  // concept = invite
        DBInitializeNew.addFabulaElement(db, "find", 45, FabulaElementNew.CATEGORY_PERCEPT, Arrays.asList("agent","patient"), null, null, false);  // concept = find
        DBInitializeNew.addFabulaElement(db, "invited", 16, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = invite
        DBInitializeNew.addFabulaElement(db, "dance", 8, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, Arrays.asList("agent:is_tired:true","agent:feeling:"+CharacterNew.FEELING_HAPPY), false);  // concept = dance
        DBInitializeNew.addFabulaElement(db, "not_wash", 10, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), Arrays.asList("agent:has_trait:"+CharacterNew.TRAIT_UNTIDY), null, true);  // concept = wash
        DBInitializeNew.addFabulaElement(db, "persuade", 26, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent","patient"), null, null, false);  // concept = persuade
        DBInitializeNew.addFabulaElement(db, "join", 46, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent","patient"), null, null, false);  // concept = join
        DBInitializeNew.addFabulaElement(db, "clean", 6, FabulaElementNew.CATEGORY_GOAL, Arrays.asList("agent","patient"), null, null, false);  // concept = clean
        DBInitializeNew.addFabulaElement(db, "challenged", 60, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), null, null, false);  // concept = challenged
        DBInitializeNew.addFabulaElement(db, "join", 46, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, Arrays.asList("hidden:has_agent_character:#agent"), false);  // concept = join
        DBInitializeNew.addFabulaElement(db, "happy", 47, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), null, null, false);  // concept = happy
        DBInitializeNew.addFabulaElement(db, "had_fun", 48, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = fun
        DBInitializeNew.addFabulaElement(db, "went_picnic", 20, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = picnic
        DBInitializeNew.addFabulaElement(db, "joined", 46, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = join
        DBInitializeNew.addFabulaElement(db, "engage", 49, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = engage
        DBInitializeNew.addFabulaElement(db, "encourage", 50, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = encourage
        DBInitializeNew.addFabulaElement(db, "lazy", 51, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), null, null, false);  // concept = lazy
        DBInitializeNew.addFabulaElement(db, "refuse", 52, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, null, false);  // concept = refuse
        DBInitializeNew.addFabulaElement(db, "convince", 53, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = convince
        DBInitializeNew.addFabulaElement(db, "clean", 6, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = clean
        DBInitializeNew.addFabulaElement(db, "sorry", 55, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), null, null, false);  // concept = sorry
        DBInitializeNew.addFabulaElement(db, "wash", 10, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = wash
        DBInitializeNew.addFabulaElement(db, "settle_down", 59, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, Arrays.asList("agent:miscellaneous_state:settled_down"), false);  // concept = settle down
        DBInitializeNew.addFabulaElement(db, "persuaded", 26, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = persuade
        DBInitializeNew.addFabulaElement(db, "eat", 9, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent"), null, null, false);  // concept = eat
        DBInitializeNew.addFabulaElement(db, "full", 56, FabulaElementNew.CATEGORY_INTERN, Arrays.asList("agent"), null, null, false);  // concept = full
        DBInitializeNew.addFabulaElement(db, "left_hunger", 15, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = hungry
        DBInitializeNew.addFabulaElement(db, "ate", 9, FabulaElementNew.CATEGORY_OUTCOME, Arrays.asList("agent"), null, null, false);  // concept = eat
        DBInitializeNew.addFabulaElement(db, "throw", 57, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = throw
        DBInitializeNew.addFabulaElement(db, "clear", 58, FabulaElementNew.CATEGORY_ACTION, Arrays.asList("agent","patient"), null, null, false);  // concept = clear

    }

    private void initializeLink(SQLiteDatabase db) {
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 1, 0, 2, 0, 0, Arrays.asList("agent>agent"), null);  // G:have_fun + A:swim
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 2, 0, 3, 0, 0, Arrays.asList("agent>agent"), null);  // A:swim + I:tired
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 3, 0, 4, 0, 0, Arrays.asList("agent>agent"), null);  // I:tired + I:hungry
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 4, 0, 20, 0, 0, Arrays.asList("agent>agent"), null);  // I:hungry + G:join
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 7, 0, 13, 0, 0, Arrays.asList("#this>hidden"), null);  // G:eat + G:invite
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 5, 0, 7, 0, 0, Arrays.asList("agent>agent"), null);  // G:leave_hunger + G:eat
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 7, 0, 8, 0, 1, Arrays.asList("agent>agent"), null);  // G:eat + G:go_picnic
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 9, 0, 20, 0, 0, Arrays.asList("agent>agent","patient>patient","hidden>hidden"), null);  // P:hear + G:join
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 8, 0, 10, 0, 0, Arrays.asList("agent>agent", "%place>patient"), null);  // G:go_picnic + A:search_for
        DBInitializeNew.addLink(db, LinkNew.TYPE_INTERRUPT, 10, 0, 11, 0, 0, Arrays.asList("agent>agent"), null);  // A:search_for + E:see
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 11, 0, 12, 2, 0, Arrays.asList("agent>agent","patient>patient"), null);  // E:see + P:see
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 20, 0, 6, 0, 0, Arrays.asList("agent>agent","hidden>patient","patient>hidden"), null);  // G:join + A:accept
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 12, 2, 13, 0, 0, Arrays.asList("agent>agent","patient>patient"), null);  // P:see + G:invite
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 13, 0, 14, 0, 0, Arrays.asList("agent>agent","patient>patient","hidden>hidden"), null);  // G:invite + A:invite
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 14, 0, 9, 0, 0, Arrays.asList("#this>patient","patient>agent","%invitation>hidden"), null);  // A:invite + P:hear
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 12, 1, 13, 0, 0, Arrays.asList("agent>patient","patient>agent"), null);  // P:see + G:invite
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 10, 0, 15, 0, 0, Arrays.asList("agent>agent","patient>patient"), null);  // A:search_for + P:find
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 1, 0, 17, 0, 0, Arrays.asList("agent>agent"), null);  // G:have_fun + A:dance
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 17, 0, 3, 0, 0, Arrays.asList("agent>agent"), null);  // A:dance + I:tired
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 17, 0, 24, 0, 0, Arrays.asList("agent>agent"), null);  // A:dance + I:happy
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 24, 0, 25, 0, 0, Arrays.asList("agent>agent"), null);  // I:happy + O:had_fun
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 15, 0, 36, 0, 0, Arrays.asList("agent>agent"), null);  // P:find + A:settle_down
        DBInitializeNew.addLink(db, LinkNew.TYPE_SUB, 6, 0, 23, 0, 0, Arrays.asList("agent>agent","hidden.agent>patient","hidden.hidden>hidden"), null);  // A:accept + A:join
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 14, 0, 16, 0, 0, Arrays.asList("agent>agent"), null);  // A:invite + O:invited
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 23, 0, 27, 0, 0, Arrays.asList("agent>agent"), null);  // A:join + O:joined
        DBInitializeNew.addLink(db, LinkNew.TYPE_SUB, 43, 0, 42, 0, 0, Arrays.asList("agent>agent","%thrash>patient"), null);  // A:clear + A:throw
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 42, 0, 33, 0, 0, Arrays.asList("agent>agent"), null);  // A:throw + O:cleaned
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 7, 0, 18, 0, 0, Arrays.asList("agent>agent"), Arrays.asList("agent:miscellaneous_state:settled_down"));  // G:eat + A:not_wash
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 18, 0, 12, 3, 0, Arrays.asList("#this>patient"), null);  // A:not_wash + P:see
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 12, 3, 19, 0, 0, Arrays.asList("agent>agent","%NULL>direction","patient.agent>patient"), null);  // P:see + G:persuade
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 28, 0, 19, 0, 0, Arrays.asList("agent>agent","patient>patient"), null);  // A:engage + G:persuade
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 19, 0, 29, 0, 0, Arrays.asList("agent>agent","patient>patient","direction>direction"), null);  // G:persuade + A:encourage
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 29, 0, 30, 0, 0, Arrays.asList("agent>hidden","patient>agent"), null);  // A:encourage + I:lazy
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 8, 0, 38, 0, 0, Arrays.asList("%picnic food>patient"), null);  // G:go_picnic + A:eat
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 30, 0, 31, 0, 0, Arrays.asList("agent>agent","hidden>hidden"), null);  // I:lazy + A:refuse
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 31, 0, 22, 0, 0, Arrays.asList("hidden>agent","agent>hidden"), null);  // A:refuse + I:challenged
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 22, 0, 32, 0, 0, Arrays.asList("agent>agent","hidden>patient"), null);  // I:challenged + A:convince
        DBInitializeNew.addLink(db, LinkNew.TYPE_SUB, 32, 0, 34, 0, 0, Arrays.asList("agent>hidden","patient>agent"), null);  // A:convince + I:sorry
        DBInitializeNew.addLink(db, LinkNew.TYPE_ENABLE, 7, 0, 12, 3, 0, Arrays.asList("agent>agent"), Arrays.asList("agent:has_trait:"+CharacterNew.TRAIT_CLEAN));  // G:eat + P:see
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 7, 0, 35, 0, 1, Arrays.asList("agent>agent"), Arrays.asList("agent:miscellaneous_state:settled_down"));  // G:eat + A:wash
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 34, 0, 35, 0, 0, Arrays.asList("agent>agent","hidden>hidden"), null);  // I:sorry + A:wash
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 21, 0, 43, 0, 0, Arrays.asList("agent>agent","%the place>patient"), null);  // G:clean + A:clear
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 35, 0, 37, 0, 0, Arrays.asList("hidden>agent"), null);  // A:wash + O:persuaded
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 36, 0, 26, 0, 0, Arrays.asList("agent>agent"), null);  // A:settle_down + O:went_picnic
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 2, 0, 24, 0, 0, Arrays.asList("agent>agent"), null);  // A:swim + I:happy
        DBInitializeNew.addLink(db, LinkNew.TYPE_MOTIV, 7, 0, 38, 0, 0, Arrays.asList("agent>agent"), Arrays.asList("agent:miscellaneous_state:settled_down"));  // G:eat + A:eat
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 38, 0, 39, 0, 0, Arrays.asList("agent>agent"), null);  // A:eat + I:full
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 39, 0, 40, 0, 0, Arrays.asList("agent>agent"), null);  // I:full + O:left_hunger
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 38, 0, 41, 0, 0, Arrays.asList("agent>agent"), null);  // A:eat + O:ate
        DBInitializeNew.addLink(db, LinkNew.TYPE_CAUSES, 41, 0, 21, 0, 0, Arrays.asList("agent>agent"), null);  // O:ate + G:clean
    }

    private void initializeNorm(SQLiteDatabase db) {
        DBInitializeNew.addNorm(db, 35, Arrays.asList("agent:current_goal:7","agent:not_has_current_goal_history:38"), 1, "before:38", null);
        DBInitializeNew.addNorm(db, 18, Arrays.asList("agent:current_goal:7","agent:not_has_current_goal_history:38"), 0, "before:38", null);
    }

//    private void initializeTheme(SQLiteDatabase db) {
//        Theme t = new Theme();
//
//        t.addTheme(db, "brave", "camp", "flashlight", "coward");
////		t.addTheme(db, "brave", "beach", "flashlight", "coward");
//
//        t.addTheme(db, "careful", "beach", "pail and shovel", "clumsy");
//
//        t.addTheme(db, "humble", "classroom", "paper", "boastful");
//
////		t.addTheme(db, "clean", "dining room", "barbeque", "untidy");
////
////		t.addTheme(db, "friendly", "beach", "pail and shovel", "shy");
////		t.addTheme(db, "friendly", "playground", "ball", "shy");
////
////		t.addTheme(db, "honest", "dining room", "glass of water", "dishonest");
////		t.addTheme(db, "honest", "classroom", "magnifying glass", "dishonest");
////		t.addTheme(db, "honest", "camp", "magnifying glass", "dishonest");
////
////		t.addTheme(db, "humble", "classroom", "paper", "boastful");
////
////		t.addTheme(db, "share", "bedroom", "ball", "selfish");
////		t.addTheme(db, "share", "bedroom", "puzzle", "selfish");
//
//
//        t.addTheme(db, "confident", "classroom", "book", "coward");
//
//        t.addTheme(db, "neat", "dining room", "barbeque", "untidy");
//
//
//        //story 5
//        t.addTheme(db, "well-behaved", "bedroom", "ball", "enegertic");
//
//        //story 6
//        t.addTheme(db, "honest", "dining room", "glass of water", "dishonest");
//
//        //story 7
//        t.addTheme(db, "generous", "playground", "pail and shovel", "selfish");
//
//        //story 8
//        t.addTheme(db, "friendly", "playground", "pail and shovel", "shy");
//    }
//
//    private void initializeEvent(SQLiteDatabase db) {
//        Event a = new Event();
//
////		//**** STORY 0 (brave) ****// ******
//        a.addEvent(db, "see",                                            // action
//                1,                                                            // max agents
//                1,                                                            // max patients
//                "*MainAgent:EmotionOf:nono & " +
//                        "*Patient:CapableOfReceivingAction:see & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",            // pre conditions
//                "MainAgent:IsAsleep:false",                        // post conditions
//                0,                                                            // interaction score
//                0,                                                            // relationship score
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "sleep",                                            // action
//                1,                                                            // max agents
//                0,                                                            // max patients
//                "*MainAgent:IsTired:true & " +
//                        "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:NotLocationOf:playground & " +
//                        "*MainAgent:NotLocationOf:dining room & " +
//                        "*MainAgent:NotLocationOf:classroom & " +
//                        "*Background:HasA:tent",        // pre conditions
//                "MainAgent:IsTired:false & MainAgent:IsAsleep:true",        // post conditions
//                0,                                                            // interaction score
//                0,                                                            // relationship score
//                "normal",
//                null);
//
//        a.addEvent(db,
//                //action
//                "play", //everybody play
//                //max agents
//                4,
//                //max patients
//                1,
//                //preconditions
//                "MainAgent:NotTraitOf:independent & *MainAgent:NotTraitOf:shy & *MainAgent:NotTraitOf:energetic & *MainAgent:IsTired:false & " +
//                        "*MainAgent:IsAsleep:false & *MainAgent:NotEmotionOf:hesitant & *MainAgent:NotEmotionOf:hurt & " +
//                        "*MainAgent:NotEmotionOf:scared & " +
//                        "*Patient:CapableOfReceivingAction:play & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                //postconditions
//                "MainAgent:IsHungry:true & " +
//                        "MainAgent:IsTired:true",
//                //interaction score
//                4,
//                //relationship score
//                2,
//                //event type
//                Event.TYPE_PATIENTS_SUPPORT,
//                Dialogue.DELIBERATION + "," + Dialogue.PERSUASION);
//
//
//        a.addEvent(db, "hear",
//                1,
//                0,
//                //preconditions
//                "*MainAgent:IsAsleep:true & " +
//                        "MainAgent:LocationOf:camp & " +
//                        "*MainAgent:NotLocationOf:playground & *MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                //postconditions
//                "MainAgent:EmotionOf:scared & " +
//                        "MainAgent:IsAsleep:false & " +
//                        "MainAgent:IsTired:false",
//                0,
//                0,
//                "normal",
//                Dialogue.NEGOTIATION);
//
//
//        a.addEvent(db, "wake up",
//                1,
//                0,
//                //preconditions
//                "*MainAgent:IsAsleep:true & " +
//                        "*MainAgent:NotLocationOf:playground & *MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                //postconditions
//                "MainAgent:IsAsleep:false & " +
//                        "MainAgent:IsTired:false",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "investigate",
//                4,
//                0,
//                //preconditions
//                "*MainAgent:Holds:Instrument & " +
//                        "*MainAgent:EmotionOf:scared & " +
//                        "*MainAgent:IsAsleep:false & " +
//                        "SupportAgent:IsAsleep:false & " +
//                        "Instrument:UsedFor:investigate & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                //postconditions
//                "MainAgent:EmotionOf:brave",
//                1,
//                2,
//                Event.TYPE_AGENTS_SUPPORT,
//                null);
//
//
////		//**** STORY 1 (careful) ****// **********
//        a.addEvent(db, "borrow",
//                1,
//                1, //patient: book**, character
//                //preconditions
//                "*MainAgent:EmotionOf:neutral & *MainAgent:NotTraitOf:shy & *MainAgent:Holds:null & " +
//                        "*MainAgent:IsAsleep:false & MainAgent:IsTired:false & MainAgent:IsHungry:false & " +
//                        "*Patient:CapableOfReceivingAction:borrow & " +
//                        "*MainAgent:NotLocationOf:dining room",
//                //post conditions
//                "MainAgent:EmotionOf:responsible & MainAgent:Holds:something",
//                4,
//                1,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "look",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & *MainAgent:Holds:something & " +
//                        "*Patient:CapableOfReceivingAction:look & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                "MainAgent:IsAsleep:false",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "lose",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:TraitOf:clumsy & *MainAgent:EmotionOf:responsible & " +
//                        "*Patient:CapableOfReceivingAction:lose & *MainAgent:NotLocationOf:dining room",
//                "MainAgent:IsHungry:false & MainAgent:IsTired:false & MainAgent:EmotionOf:guilty & MainAgent:Holds:null",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "scold",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:EmotionOf:guilty & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                "Patient:EmotionOf:ashamed",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "search",
//                4,
//                0,
//                "*MainAgent:IsAsleep:false & " +
//                        "SupportAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:ashamed & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//                "MainAgent:EmotionOf:worried",
//                1,
//                1,
//                Event.TYPE_AGENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "find",
//                1,
//                1, // patient: book
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:worried & " +
//                        "*Patient:CapableOfReceivingAction:find & " +
//                        "*MainAgent:NotLocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "MainAgent:TraitOf:careful",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//
////		//**** STORY 2 (confident) ****//
//        a.addEvent(db, "prepare",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:TraitOf:coward & " +
//                        "*Patient:CapableOfReceivingAction:prepare & *MainAgent:LocationOf:classroom",
//                "MainAgent:EmotionOf:nervous",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT, null);
//
//        a.addEvent(db, "help",
//                1,
//                1, //help main char
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:TraitOf:helpful & " +
//                        "*Patient:IsAsleep:false & " +
//                        "*Patient:EmotionOf:nervous & *MainAgent:LocationOf:classroom",
//                "MainAgent:EmotionOf:helpful",
//                2,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT, null);
//
//        a.addEvent(db, "practice",
//                1,
//                0, //practice what??? (kaya ba ung "practice for __")
//                "*MainAgent:EmotionOf:nervous & " +
//                        "*MainAgent:IsAsleep:false & *MainAgent:LocationOf:classroom",
//                "MainAgent:IsAsleep:false",
//                0,
//                3,
//                "normal", Dialogue.PERSUASION);
//
//        a.addEvent(db, "praise",
//                1,
//                1, //praise main char
//                "*MainAgent:EmotionOf:helpful " +
//                        "& *Patient:EmotionOf:nervous " +
//                        "& *MainAgent:IsAsleep:false " +
//                        "& *Patient:IsAsleep:false & *MainAgent:LocationOf:classroom",
//                "Patient:EmotionOf:relaxed & Patient:TraitOf:confident",//*Patient:TraitOf:confident",
//                2,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT, null);
//
//        a.addEvent(db, "present",
//                1,
//                0,
//                "*MainAgent:LocationOf:classroom & " +
//                        "*MainAgent:EmotionOf:relaxed & *MainAgent:TraitOf:confident & " +
//                        "*MainAgent:IsAsleep:false",
//                "MainAgent:IsAsleep:false",
//                0,
//                0,
//                "normal", null);
//
//
////		//** STORY 3 (humble) **// ***********
//        a.addEvent(db, "take",
//                1,
//                1, //test (via CapableOfReceivingAction)
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:TraitOf:boastful & " +
//                        "*Patient:CapableOfReceivingAction:take & " +
//                        "*MainAgent:LocationOf:classroom",
//
//                "MainAgent:EmotionOf:confident",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "take",
//                1,
//                1, //test (via CapableOfReceivingAction)
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:CapableOfReceivingAction:take & " +
//                        "*MainAgent:LocationOf:classroom & " +
//                        "*MainAgent:NotTraitOf:boastful",
//
//                "MainAgent:LocationOf:classroom",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "boast to",
//                1,
//                3, //boast to other characters
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:confident & *MainAgent:TraitOf:boastful",
//                "MainAgent:IsAsleep:false",
//                3,
//                -3,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "insult",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:confident",
//
//                "Patient:EmotionOf:angry",
//                3,
//                -3,
//
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        //SCOLD already in data
//        a.addEvent(db, "scold",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:EmotionOf:guilty & *MainAgent:NotLocationOf:dining room",
//
//                "Patient:EmotionOf:ashamed",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "scold",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & *Patient:EmotionOf:confident & " +
//                        "*MainAgent:EmotionOf:angry & *MainAgent:NotLocationOf:dining room",
//
//                "Patient:EmotionOf:confident",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "fail",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:confident & " +
//                        "*Patient:CapableOfReceivingAction:fail",
//
//                "MainAgent:EmotionOf:repentant",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//
////		//** STORY 4 (neat) **// ***********
//        a.addEvent(db, "exercise",
//                1,
//                0,
//                "*MainAgent:IsTired:false & *MainAgent:IsHungry:false & " +
//                        "*MainAgent:IsAsleep:false & *MainAgent:LocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "MainAgent:IsTired:true & MainAgent:IsThirsty:true & MainAgent:IsHungry:true",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "eat",
//                1,
//                1, //food (via CapableOfReceivingAction)
//                "*MainAgent:TraitOf:untidy & *MainAgent:LocationOf:dining room & " +
//                        "*MainAgent:IsHungry:true & " +
//                        "*MainAgent:IsAsleep:false & " +
//                        "*Patient:CapableOfReceivingAction:eat & *MainAgent:LocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "MainAgent:EmotionOf:uneasy & " +
//                        "MainAgent:IsHungry:false",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT, null);
//        //Dialogue.PERSUASION);
//
//        a.addEvent(db, "toothache", //via EmotionOf:hurt
//                1,
//                1, //tooth (via CapableOfReceivingAction)
//                "*MainAgent:TraitOf:untidy & *MainAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:uneasy & " +
//                        "MainAgent:IsHungry:false & " +
//                        "*MainAgent:LocationOf:dining room & *MainAgent:LocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "MainAgent:IsAsleep:false & " +
//                        "MainAgent:EmotionOf:hurt",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "cry", //via subevent (toothache)
//                1,
//                0,
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:hurt & *MainAgent:LocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "MainAgent:EmotionOf:ashamed",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "scold",
//                1,
//                1,
//                "*MainAgent:IsAsleep:false & " +
//                        "*Patient:EmotionOf:ashamed & *MainAgent:LocationOf:dining room & *MainAgent:NotLocationOf:classroom",
//
//                "Patient:EmotionOf:ashamed",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//
//        a.addEvent(db, "brush", //resolution
//                1, //mainchar
//                1, //tooth
//                "*MainAgent:IsAsleep:false & " +
//                        "*MainAgent:EmotionOf:ashamed & " +
//                        "*Instrument:UsedFor:brush & *MainAgent:LocationOf:dining room",
//
//                "*MainAgent:EmotionOf:repentant",
//                0,
//                0,
//                "normal",
//                null);
//
//
////		//** STORY 5 (well-behaved) **// ***********
//        a.addEvent(db,
//                //action
//                "play", //everybody play but main won't get tired easily
//                //max agents
//                4,
//                //max patients
//                1,
//                //preconditions
//                "*MainAgent:IsTired:false & " +
//                        "*MainAgent:IsAsleep:false & " +
//                        "SupportAgent:IsTired:false & " +
//                        "*SupportAgent:IsAsleep:false & " +
//                        "*MainAgent:NotEmotionOf:hesitant & *MainAgent:NotEmotionOf:hurt & " +
//                        "*MainAgent:NotTraitOf:independent & *MainAgent:TraitOf:energetic",
//                //postconditions
//                "MainAgent:EmotionOf:happy & " +
//                        "SupportAgent:IsTired:true & SupportAgent:IsHungry:true",
//                //interaction score
//                4,
//                //relationship score
//                2,
//                //event type
//                Event.TYPE_AGENTS_SUPPORT,
//                Dialogue.DELIBERATION + "," + Dialogue.PERSUASION);
//
//        a.addEvent(db, "wake up",
//                1,
//                3,
//                "*MainAgent:IsAsleep:false & *MainAgent:IsTired:false & *MainAgent:EmotionOf:happy & *Patient:IsAsleep:true",
//                "MainAgent:EmotionOf:nervous & Patient:EmotionOf:angry & Patient:IsAsleep:false",
//                3,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "fight",
//                3,
//                1,
//                "*MainAgent:EmotionOf:angry & *Patient:EmotionOf:nervous",
//                "Patient:EmotionOf:repentant",
//                4,
//                -4,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "apologize to",
//                1,
//                3,
//                "*MainAgent:EmotionOf:repentant & *Patient:EmotionOf:angry",
//                "MainAgent:EmotionOf:sorry",
//                6,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
////		//** STORY 6 (honest) **// ***********
//        a.addEvent(db, "play alone", //play alone
//                1,
//                0,
//                "*MainAgent:EmotionOf:neutral & *MainAgent:IsAsleep:false & *MainAgent:IsTired:false & *MainAgent:TraitOf:independent",
//                "MainAgent:EmotionOf:lonely",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "break",
//                1,
//                1,
//                "*MainAgent:EmotionOf:lonely & *MainAgent:TraitOf:clumsy & *Patient:CapableOfReceivingAction:break",
//                "MainAgent:EmotionOf:nervous & *MainAgent:IsHungry:false",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "ask",
//                3,
//                1,
//                "*MainAgent:EmotionOf:neutral & *MainAgent:NotTraitOf:dishonest & *Patient:EmotionOf:nervous",
//                "MainAgent:EmotionOf:curious",
//                2,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "blame",
//                1,
//                3,
//                "*MainAgent:EmotionOf:nervous & *Patient:EmotionOf:curious",
//                "Patient:EmotionOf:innocent & Patient:EmotionOf:angry",
//                3,
//                -2,
//                Event.TYPE_PATIENTS_SUPPORT,
//                Dialogue.EMOTION + "," + Dialogue.PLURAL);
//
//        a.addEvent(db, "admit to",
//                1,
//                3,
//                "*MainAgent:EmotionOf:repentant & *MainAgent:TraitOf:dishonest & *Patient:EmotionOf:angry",
//                "MainAgent:EmotionOf:guilty",
//                6,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
////		//** STORY 7 (Generous) **// ***********
//        a.addEvent(db, "borrow from",
//                3,
//                1,
//                "*MainAgent:EmotionOf:neutral & *MainAgent:NotTraitOf:shy & *MainAgent:IsAsleep:false & *MainAgent:IsTired:false & *MainAgent:NotTraitOf:selfish & " +
//                        "Patient:EmotionOf:happy & *Patient:TraitOf:selfish",
//                "MainAgent:EmotionOf:responsible & " +
//                        "Patient:EmotionOf:hesitant",
//                4,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "decline",
//                1,
//                3,
//                "*MainAgent:TraitOf:selfish & *MainAgent:EmotionOf:hesitant & " +
//                        "*Patient:EmotionOf:responsible",
//                "MainAgent:EmotionOf:irritated & Patient:EmotionOf:hurt",
//                2,
//                -2,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "cry",
//                3,
//                0,
//                "*MainAgent:EmotionOf:hurt",
//                "MainAgent:EmotionOf:sad",
//                0,
//                0,
//                "normal",
//                null);
//
//        a.addEvent(db, "lend",
//                1,
//                3,
//                "*MainAgent:EmotionOf:irritated & *MainAgent:TraitOf:selfish & " +
//                        "*Patient:CapableOfReceivingAction:lend",
//                "MainAgent:EmotionOf:repentant & MainAgent:EmotionOf:sorry",
//                6,
//                3,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
////		//** STORY 8 (Friendly) **// ***********
//        a.addEvent(db, "watch",
//                1,
//                3,
//                "*MainAgent:EmotionOf:lonely & *MainAgent:TraitOf:shy & " +
//                        "*Patient:IsHungry:true & *Patient:IsTired:true",
//                "MainAgent:EmotionOf:envious & MainAgentt:EmotionOf:shy",
//                0,
//                0,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "invite",
//                3,
//                1,
//                "*MainAgent:IsHungry:true & *MainAgent:IsTired:true & " +
//                        "*Patient:EmotionOf:shy",
//                "MainAgent:EmotionOf:friendly",
//                2,
//                -2,
//                Event.TYPE_PATIENTS_SUPPORT,
//                null);
//
//        a.addEvent(db, "accept",
//                3,
//                0,
//                "*MainAgent:EmotionOf:shy & " +
//                        "*Patient:EmotionOf:friendly",
//                "MainAgent:EmotionOf:thankful & MainAgent:EmotionOf:happy",
//                0,
//                0,
//                "normal",
//                null);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBField.CREATE_STORY_TABLE);
        db.execSQL(DBField.CREATE_CONCEPT_TABLE);
        db.execSQL(DBField.CREATE_SEMANTIC_TABLE);
        db.execSQL(DBField.CREATE_CHARACTER_TABLE);
        db.execSQL(DBField.CREATE_BACKGROUND_TABLE);
        db.execSQL(DBField.CREATE_OBJECT_TABLE);
        db.execSQL(DBField.CREATE_BGOBJECT_TABLE);
        db.execSQL(DBField.CREATE_GOALTRAIT_TABLE);
        db.execSQL(DBField.CREATE_FABULAELEM_TABLE);
        db.execSQL(DBField.CREATE_CONFLICT_TABLE);
        db.execSQL(DBField.CREATE_CONTEXT_TABLE);
        db.execSQL(DBField.CREATE_RESOLUTION_TABLE);
        db.execSQL(DBField.CREATE_LINK_TABLE);
        db.execSQL(DBField.CREATE_NORM_TABLE);

//        db.execSQL(DBField.CREATE_THEME_TABLE);
//        db.execSQL(DBField.CREATE_TRAIT_TABLE);
//        db.execSQL(DBField.CREATE_IR_TABLE);
//        db.execSQL(DBField.CREATE_EVENT_TABLE);
//        db.execSQL(DBField.CREATE_NOUN_TABLE);
//        db.execSQL(DBField.CREATE_PRONOUN_TABLE);
//        db.execSQL(DBField.CREATE_VERB_TABLE);
//        db.execSQL(DBField.CREATE_ADJECTIVE_TABLE);
//        db.execSQL(DBField.CREATE_ADVERB_TABLE);
//        db.execSQL(DBField.CREATE_PREPOSITION_TABLE);
//        db.execSQL(DBField.CREATE_CONJUNCTION_TABLE);
//        db.execSQL(DBField.CREATE_DIALOGUE_TABLE);

        initializeStory(db);
        initializeConcept(db);
        initializeSemRep(db);
        initializeBackground(db);
        initializeCharacter(db);
        initializeObject(db);
        initializeBGObject(db);
        initializeGoalTrait(db);
        initializeFabula(db);
        initializeConflict(db);
        initializeContext(db);
        initializeResolution(db);
        initializeLink(db);
        initializeNorm(db);

//        initializeEvent(db);
//        initializeTheme(db);
//        initializeDialogue(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // todo what's the version for? how should this be used
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_STORY);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_NORM);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_LINK);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_CONTEXT);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_CONFLICT);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_GOALTRAIT);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_FABULAElEM);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_BGOBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_OBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_BACKGROUND);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_CHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_SEMANTIC);
        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_CONCEPT);

//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_DIALOGUE);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_NOUN);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_PRONOUN);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_VERB);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_ADJECTIVE);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_ADVERB);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_PREPOSITION);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_CONJUNCTION);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_EVENT);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_THEME);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_IR);
//        db.execSQL("DROP TABLE IF EXISTS " + DBField.TABLE_TRAIT);

        onCreate(db);
    }
}