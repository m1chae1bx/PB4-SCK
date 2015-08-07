package process.storyplanning;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.Random;
//import java.util.Stack;
//
//import database.SOMEOBJECT;
//
//import model.ontologymodel.SemanticRelation;
//
//import model.storyplanmodel.Event;

//import storyworldmodel.Character;
//import storyworldmodel.Object;
//import storyworldmodel.Relationship;
//import storyworldmodel.StoryWorldOld;

@Deprecated
public class CharacterAgent { //implements Agent, Patient
//
//	public static int EVENT_LIMIT = 5;
//	public static int CONFLICT = 1;
//	public static int RESOLUTION = 2;
//	
//	private Character character;
//	
//	private Event conflictGoal;
//	private Event resolutionGoal;
//	
//	private Event currentAction;
//	private Queue<Event> actionQueue;
//	
//	public CharacterAgent(Character character) {
//		this.character = character;
//		actionQueue = new LinkedList<Event>();
//	}
//	
//	//not yet finished
//	public Event identifyAction() {
//
//		double threshold = 0.5;
//		
//		ArrayList<Event> possibleEventList = SOMEOBJECT.getPossibleEvents(character.getRelatedActions());
//		
//		HashMap<Event, Double> scoreMap = new HashMap<Event, Double>();
//		
//		double score;
//		for(Event possibleEvent : possibleEventList) {
//			score = 0;
//			for(String precond : possibleEvent.getAgentRelatedPreCond()) {
//				if(WorldAgent.checkAgentCondition(this, precond)) {
//					score += 1;
//				}
//				else {
//					score = 0;
//					break;
//				}		
//			}
//			score /= possibleEvent.getAgentRelatedPreCond().size();
//			if(score >= threshold)
//				scoreMap.put(possibleEvent, score);
//		}
//		
//		double highestScore = 0;
//		Event selectedAction = null;
//		for(HashMap.Entry<Event, Double> entry : scoreMap.entrySet()) {
//			if(entry.getValue() > highestScore) { // if the score is equal, the first one will be chosen
//				highestScore = entry.getValue();
//				selectedAction = entry.getKey();
//			}
//		}
//		
//		return selectedAction;
//	}
//	
//	public boolean hasReachedGoal(int storyplot) {
//		if(storyplot == CONFLICT)
//			return conflictGoal.equals(currentAction);
//		else // storyplot == RESOLUTION
//			return resolutionGoal.equals(currentAction);
//	}
//	
//	public void setConflictGoal(Event goal) {
//		this.conflictGoal = goal;
//	}
//	public void setResolutionGoal(Event goal) {
//		this.resolutionGoal = goal;
//	}
//	
//	public void setCurrentAction(Event currentAction) {
//		this.currentAction = currentAction;
//		actionQueue.add(currentAction);
//		if(actionQueue.size() >= EVENT_LIMIT) {
//			actionQueue.remove();
//		}
//	}
//	public int getActionQueueSize() {
//		return actionQueue.size();
//	}
//
//
//	
//	@Override
//	public int getID() {
//		return character.getID();
//	}
//
//	@Override
//	public String getsName() {
//		return character.getsName();
//	}
//
//	@Override
//	public String getsImagePath() {
//		return character.getsImagePath();
//	}
//	
//	public String getGender() {
//		return character.getGender();
//	}
//	
//	public ArrayList<Relationship> getRelationships() {
//		return character.getRelationships();
//	}
//	public Relationship getRelationship(Character toCharacter) {
//		return character.getRelationshipWith(toCharacter);
//	}
//	public void updateRelationship(Character toCharacter, int points) {
//		character.updateRelationshipWith(toCharacter, points);
//	}
//	
//	public ArrayList<SemanticRelation> getRelatedActions() {
//		return character.getRelatedActions();
//	}
//	
//	public ArrayList<String> getTraits() {
//		return character.getTraits();
//	}
//	public boolean checkTrait(String trait) {
//		for(String temp : getTraits()) {
//			if(temp.equalsIgnoreCase(trait))
//				return true;
//		}
//		return false;
//	}
//		
//	public String getEmotion() {
//		return character.getEmotion();
//	}
//	public void setEmotion(String emotion) {
//		character.setEmotion(emotion);
//	}
//	
//	public Object getItem() {
//		return character.getItem();
//	}
//	public void setItem(Object item) {
//		character.setItem(item);
//	}
//
//	public String getLocation() {
//		return character.getLocation();
//	}
//	public void setLocation(String location) {
//		character.setLocation(location);
//	}
//	
//	public boolean isHungry() {
//		return character.isHungry();
//	}
//	public void isHungry(boolean value) {
//		character.isHungry(value);
//	}
//	
//	public boolean isThirsty() {
//		return character.isThirsty();
//	}
//	public void isThirsty(boolean value) {
//		character.isThirsty(value);
//	}
//
//	public boolean isTired() {
//		return character.isTired();
//	}
//	public void isTired(boolean value) {
//		character.isTired(value);
//	}
//	
//	public boolean isAsleep() {
//		return character.isAsleep();
//	}
//	public void isAsleep(boolean value) {
//		character.isAsleep(value);
//	}
//
//
}
