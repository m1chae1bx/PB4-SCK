package model.storyworldmodel;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import database.SOMEOBJECT;
import model.charmodel.Relationship;
import model.ontologymodel.ActiveSemanticRelation;
import model.storyplanmodel.Agent;
import model.storyplanmodel.Event;
import model.storyplanmodel.Patient;
import process.storyplanning.StoryPlanner;
import process.storyplanning.WorldAgent;
@Deprecated
public class Character implements Agent, Patient, Element {
	
	public static final String tag = "CHARACTER";
	
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static int EVENT_LIMIT = 20;
	public static int PROPOSED_EVENT_LIMIT = 5;
	public static double THRESHOLD = 0.6;
	private int id;
	private String name;
	private String imagePath;
	private int gender;
	private ArrayList<Relationship> relationships;
	private ArrayList<String> relatedActions; // possible actions done to or done by
	private ArrayList<String> traits;
	private boolean isHungry;
	private boolean isThirsty;
	private boolean isTired;
	private boolean isAsleep;
	private Object item;
	private String emotion;
	private String location;
	private boolean goalFlag;
	
	private Event conflictGoal;
	private Event resolutionGoal;
	
	private Event currentAction;
	private Queue<String> actionQueue;
	private Queue<String> proposedActionQueue;

	// CONSTRUCTORS
	public Character() {

	}
	

	public Character(int id, String name, int gender, String imagePath) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.imagePath = imagePath;

		//retrieve relationships from db
		//*retrieval of relationships is done in the CreatorActivity class

		//retrieve actions from db
		relatedActions = new ArrayList<String>();

		//retrieve  traits from db
		traits = SOMEOBJECT.getTraits(id);

		actionQueue = new LinkedList<String>();
		proposedActionQueue = new LinkedList<String>();
	}

	public void getAnItem(StoryWorldOld storyworld, Event event, String neededItem) {

		for (Object availableItem : storyworld.getObjects()) {
			if (availableItem.getName().equalsIgnoreCase(neededItem)) {
				event.setInstrument(availableItem);
				item = availableItem;
				break;
			}
		}
	}
	
	// BASIC INFO RELATED METHODS
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getGender() {
		return gender;
	}
	public String getImagePath() {
		return imagePath;
	}
	
	public ArrayList<String> getTraits() {
		return traits;
	}
	public void setTraits(ArrayList<String> traits) {
		this.traits = traits;
	}
	public void addTrait(String trait) {
		if(traits == null)
			traits = new ArrayList<String>();
		traits.add(trait);
	}
	public boolean checkTrait(String trait) {
		for(String temp : traits) {
			if(trait.equalsIgnoreCase(temp))
				return true;
		}
		return false;
//		return traits.contains(trait); 
	}
	
	// CHARACTER STATE RELATED METHODS
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	
	public Object getItem() {
		return item;
	}
	public void setItem(Object item) {
		this.item = item;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean isHungry() {
		return isHungry;
	}
	public void isHungry(boolean value) {
		this.isHungry = value;
	}
	
	public boolean isThirsty() {
		return isThirsty;
	}
	public void isThirsty(boolean value) {
		this.isThirsty = value;
	}

	public boolean isTired() {
		return isTired;
	}
	public void isTired(boolean value) {
		this.isTired = value;
	}
	
	public boolean isAsleep() {
		return isAsleep;
	}
	public void isAsleep(boolean value) {
		this.isAsleep = value;
	}

	// RELATIONSHIP RELATED METHODS
	public ArrayList<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(ArrayList<Relationship> relationships) {
		this.relationships = relationships;
	}
	public void addRelationship(Relationship relationship) {
		if(relationships == null)
			relationships = new ArrayList<Relationship>();
		relationships.add(relationship);
	}
	public Relationship getRelationshipWith(Character character) {
		for(Relationship r : relationships) {
			if(r.getRelationshipTo().getID() == character.getID()) {
				return r;
			}
		}
		return null;
	}
	public void updateRelationshipWith(Character character, int points) {
		this.getRelationshipWith(character).updateScore(points);
	}
	
	// ACTION RELATED METHODS
	public ArrayList<String> getRelatedActions() {
		return relatedActions;
	}
	public void setRelatedActions(ArrayList<String> relatedActions) {
		this.relatedActions = relatedActions;
	}
	public void addRelatedAction(String relatedAction) {
		if(relatedActions == null)
			relatedActions = new ArrayList<String>();
		relatedActions.add(relatedAction);
	}
	public String getCurrentActionString() {
		try {
			return currentAction.getAction();
		} catch(NullPointerException npe) {
			return null;
		}
	}
	public Event identifyAction() {

		double threshold = THRESHOLD;
		ArrayList<Event> possibleEventList = SOMEOBJECT.getPossibleEvents(getRelatedActions());
		HashMap<Event, Double> scoreMap = new HashMap<Event, Double>();


		Log.i(tag, "----- POSSIBLE EVENTS -----");
		Log.i(tag, "----- THRESHOLD "+ threshold + "-----");
		
		double score;
		for(Event possibleEvent : possibleEventList) {
			score = 0;
			possibleEvent.addAgent(this);
			
			if((actionQueue.contains(possibleEvent.getAction())) ||
					(!possibleEvent.equals(StoryPlanner.storyplan.getResolution()) &&
							possibleEvent.getAction().equalsIgnoreCase(StoryPlanner.storyplan.getResolution().getAction())) ||
						(proposedActionQueue != null && proposedActionQueue.contains(possibleEvent.getAction()))) {
				continue;
			}
			
			int precondCount = 0;
			for(String precond : possibleEvent.getMainAgentRelatedPreCond()) {

				Log.i(tag, precond + " " + WorldAgent.checkElementCondition(this, precond));
				
				if(precond.contains(ActiveSemanticRelation.HOLDS) && item == null) {
					continue;
				}
				
				if(WorldAgent.checkElementCondition(this, precond)) {
					score += 1;
				}
				else if(!WorldAgent.checkElementCondition(this, precond) && precond.contains(WorldAgent.MANDATORY_CONDITION)) {
					score = 0;
					break;
				}
				
				precondCount++;
			}
			score /= precondCount;

			Log.i(tag, possibleEvent.getAction() + " " + score);
			
			if(score >= threshold)
				scoreMap.put(possibleEvent, score);
		}
		
		Log.i(tag, "---- CANDIDATE EVENTS -----");
		
		// HIGHEST SCORE IN PRECONDITIONS
//		double highestScore = 0;
//		Event selectedAction = null;
//		for(HashMap.Entry<Event, Double> entry : scoreMap.entrySet()) {
//			if(entry.getValue() > highestScore) { // if the score is equal, the first one will be chosen
//				highestScore = entry.getValue();
//				selectedAction = entry.getKey();
//			}
//		}
		
		// HIGHEST INTERACTION SCORE 
		double highestScore = -1;
		Event selectedAction = null;
		Random rand = new Random();
		for(HashMap.Entry<Event, Double> entry : scoreMap.entrySet()) {
			
			Log.i(tag, entry.getKey().getAction() + " - " +entry.getKey().getInteractionScore());
			
			if(entry.getKey().getInteractionScore() > highestScore) { 
				highestScore = entry.getKey().getInteractionScore();
				selectedAction = entry.getKey();
			}
			else if(entry.getKey().getInteractionScore() == highestScore) {
				
				// if entry is equal to conflict or resolution, set entry as selected action; else, randomize.
				if(this.getConflictGoal().getAction().equals(entry.getKey().getAction()) ||
					this.getResolutionEvent().getAction().equals(entry.getKey().getAction())) {
					highestScore = entry.getKey().getInteractionScore();
					selectedAction = entry.getKey();
				}
				else if(rand.nextInt(2) == 0 &&
						(!this.getConflictGoal().getAction().equals(selectedAction.getAction()) && 
						!this.getResolutionEvent().getAction().equals(selectedAction.getAction()))) {
					highestScore = entry.getKey().getInteractionScore();
					selectedAction = entry.getKey();
				}
				
//				Log.i(tag, entry.getKey().getAction() + " - " + getConflictGoal().getAction() + " " + this.getConflictGoal().getAction().equals(entry.getKey().getAction()));
//				Log.i(tag, entry.getKey().getAction() + " - " + getResolutionEvent().getAction() + " " + this.getResolutionEvent().getAction().equals(entry.getKey().getAction()));
//				Log.i(tag, "Selected: " + selectedAction.getAction());
			}
		}
		Log.i(tag, "--------------");
		if(selectedAction != null) {
			if(!selectedAction.getAction().equalsIgnoreCase(this.getConflictGoal().getAction()) && 
					!selectedAction.getAction().equalsIgnoreCase(this.getResolutionEvent().getAction())) {
				
				this.addProposedAction(selectedAction);
				Log.i(tag, proposedActionQueue.peek());
			}
		}
		return selectedAction;
	}

	public void setCurrentAction(Event currentAction) {
		this.currentAction = currentAction;
		if(currentAction != null) {
			actionQueue.add(currentAction.getAction());
			if(actionQueue.size() >= EVENT_LIMIT) {
				actionQueue.remove();
			}
		}
	}
	public int getActionQueueSize() {
		return actionQueue.size();
	}
	public void addProposedAction(Event propEventAction) {
		proposedActionQueue.add(propEventAction.getAction());
		if(proposedActionQueue.size() >= PROPOSED_EVENT_LIMIT) {
			proposedActionQueue.remove();
		}
	}

	// GOAL RELATED METHODS
	public boolean hasReachedGoal(int storyplot) {
		if(goalFlag) {
			return true;
		}
		else {
			if(currentAction == null)
				return false;
			if(storyplot == Event.CONFLICT) {
				if(conflictGoal.getAction().equalsIgnoreCase(currentAction.getAction())) {
					goalFlag = true;
				}
				return conflictGoal.getAction().equalsIgnoreCase(currentAction.getAction());
			}
			else {// storyplot == RESOLUTION
				if(resolutionGoal.getAction().equalsIgnoreCase(currentAction.getAction())) {
					goalFlag = true;
				}
				
				return resolutionGoal.getAction().equalsIgnoreCase(currentAction.getAction());
			}
		}
	}
	public void resetGoalFlag() {
		goalFlag = false;
	}
	
	public Event getConflictGoal() {
		return conflictGoal;
	}
	public void setConflictGoal(Event goal) {
		this.conflictGoal = goal;
	}
	
	public Event getResolutionEvent() {
		return resolutionGoal;
	}
	public void setResolutionGoal(Event goal) {
		this.resolutionGoal = goal;
	}

//	/**
//	 * Inserts a character in the database
//	 * @param db 		- SQL database
//	 * @param name 		- name of the character
//	 * @param gender 	- gender of the character
//	 * @param imagePath - file path of the character
//	 * @param traits 	- traits of the character.
//	 * 				 	  represented as "trait1,trait2,trait3"
//	 */
//	public void addCharacter(SQLiteDatabase db, String name, int gender, String imagePath, String traits){
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(DBField.COLUMN_CHAR_NAME, name);
//		cv.put(DBField.COLUMN_CHAR_GENDER, gender);
//		cv.put(DBField.COLUMN_CHAR_IMAGEPATH, imagePath);
//
//		long id = db.insert(DBField.TABLE_CHARACTER, null, cv);
//
//		StringTokenizer trait = new StringTokenizer(traits, ",");
//
//
//		while (trait.hasMoreTokens()) {
//			cv.clear();
//			cv.put(DBField.COLUMN_CHAR_ID, (int)id);
//			cv.put(DBField.COLUMN_TRAIT, trait.nextToken());
//			db.insert(DBField.TABLE_TRAIT, null, cv);
//		}
//	}

//	/**
//	 * Inserts a relationship in the database
//	 * @param db		- SQL database
//	 * @param from		- Character id
//	 * @param to		- Character id
//	 * @param score		-
//	 * @param relation	-
//	 */
//	public void addRelationship(SQLiteDatabase db, int from, int to, int score, String relation){
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(DBField.COLUMN_FROM, from);
//		cv.put(DBField.COLUMN_TO, to);
//		cv.put(DBField.COLUMN_SCORE, score);
//		cv.put(DBField.COLUMN_RELATIONSHIP, relation);
//
//		db.insert(DBField.TABLE_IR, null, cv);
//
//	}

	@Override
	public boolean equals(java.lang.Object o) {
		Character c = (Character) o;
		return this.getID() == c.getID() || this.getName().equals(c.getName());

	}
	@Override
	public String toString() {
		String str = "";
		
		str += "ID: " + id + "\n";
		str += "NAME: " + name + "\n";
		
		str += "EMOTION: " + emotion + "\n";
		str += "LOCATION: " + location + "\n";
		
		if(item != null)
			str += "ITEM: " + item.toString() + "\n";
		else str += "ITEM: " + "null" + "\n";
		
		str += "IS_ASLEEP: " + isAsleep + "\n";
		str += "IS_THIRSTY: " + isThirsty + "\n";
		str += "IS_TIRED: " + isTired + "\n";
		str += "IS_HUNGTY: " + isHungry + "\n";
		
		str += "RELATIONSHIPS:\n";
		for(Relationship rel : relationships) {
			str += rel.toString() + "\n";
		}
		
		str += "\n";
		
		if(currentAction != null) {
			str += "Current Action: " + currentAction.getAction() + "\n";
			str += "\n";
			str += "Reached Conflict Goal? " + conflictGoal.getAction() + " - "+ hasReachedGoal(Event.CONFLICT) + "\n";
			str += "\n";
			str += "Reached Resolution Goal? " + resolutionGoal.getAction() + " - "+ hasReachedGoal(Event.RESOLUTION) + "\n";
			str += "\n";
 		}
		
		return str;
	}
	
}
