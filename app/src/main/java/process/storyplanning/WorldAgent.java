package process.storyplanning;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import database.SOMEOBJECT;
import model.charmodel.Relationship;
import model.ontologymodel.ActiveSemanticRelation;
import model.ontologymodel.ISemanticRep;
import model.storyplanmodel.Agent;
import model.storyplanmodel.Event;
import model.storyplanmodel.Patient;
import model.storyworldmodel.Background;
import model.storyworldmodel.Character;
import model.storyworldmodel.Element;
import model.storyworldmodel.Object;
import model.storyworldmodel.StoryWorldOld;

@Deprecated
public class WorldAgent {
	
	public static final String tag = "WORLD AGENT";
	
	public static final String CONDITION_DELIMITER = ":";
	public static final String MANDATORY_CONDITION = "*";
	
	/**
	 * Identifies the supporters(character or objects) of the action; 
	 * returns null if a mandatory precondition is false
	 * @param storyworld insert desc here
	 * @param caList insert desc here
	 * @param action insert desc here
	 * @return an Event with the selected patietns
	 */
	public static Event selectSupporters(StoryWorldOld storyworld, ArrayList<Character> caList, Event action) {
		
		double threshold = 0.5;
		
		Log.i(tag, "--- SELECT SUPPORTERS ---");
		
		int maxSupporters;
		ArrayList<String> precondList;
		if(action.getEventType().equalsIgnoreCase(Event.TYPE_AGENTS_SUPPORT)) {
			maxSupporters = action.getMaxAgents() - 1;
			precondList = action.getSupportAgentRelatedPreCond();
		}
		else { // EVENT TYPE IS PATIENT SUPPORT
			maxSupporters = action.getMaxPatients();
			precondList = action.getPatientRelatedPreCond();
		}
		
		// initialize score map
		ArrayList<Element> supporterList = new ArrayList();
		for(Character ca : caList) {
			supporterList.add(ca);
		}
		for(Object item : storyworld.getObjects()) {
			supporterList.add(item);
		}
		
		// scores each supporters
		HashMap<Element, Double> scoreMap = new HashMap();
		double score;
		boolean flag = false;
		for(Element element : supporterList) {
			score = 0;
			for(String precond : precondList) {
				Log.i(tag, precond + " " + checkElementCondition(element, precond));
				if(checkElementCondition(element, precond)) {
					score += 1;
				}
				else if(!checkElementCondition(element, precond) && precond.contains(MANDATORY_CONDITION)) {
					score = 0;
					flag = true;
					break;
				}
			}
			score /= precondList.size();
			if(score >= threshold) {			
				scoreMap.put(element, score);
			}
			
			Log.i(tag, element.getName() + " " + score);
		}
		
		Log.i(tag, "--- CANDIDATE SUPPORTERS ---");
		for(HashMap.Entry<Element, Double> entry : scoreMap.entrySet()) {
			Log.i(tag, entry.getKey().getName());
		}
		Log.i(tag, "--------------");
		
		if(flag && scoreMap.isEmpty()) {
			
			// find in the common sense knowledge
			if(action.getEventType().equalsIgnoreCase(Event.TYPE_PATIENTS_SUPPORT)) {
				ArrayList<String> possibleObjects = SOMEOBJECT.getObjectsCapableOfReceiving(action.getAction());
				try {
					int index = new Random().nextInt(possibleObjects.size());
					Object newObj = new Object(possibleObjects.get(index));
					StoryPlanner.storyworld.addObject(newObj);
					action.addPatient(newObj);
					return action;
				} catch(NullPointerException npe) {
					return null;
				} catch(IllegalArgumentException iae) {
					return null;
				}
			}
			else {
				return null;
			}
		}
		
		// select supporters with the highest score and add it in the event/action
		int nChosenSupporters = 0;
		double highestScore;
		Element selectedElement;
		if(scoreMap.size() < maxSupporters) {
			maxSupporters = scoreMap.size();
		}
		while(nChosenSupporters < maxSupporters || !scoreMap.isEmpty()) {
			
			highestScore = 0;
			selectedElement = null;
			
			for(HashMap.Entry<Element, Double> entry : scoreMap.entrySet()) {
				if(entry.getValue() > highestScore) {	// if both have equal score, the first one will be chosen
					highestScore = entry.getValue();
					selectedElement = entry.getKey();
				}
			}
			
			if(action.getEventType().equalsIgnoreCase(Event.TYPE_AGENTS_SUPPORT)) {
				action.addAgent((Agent) selectedElement);
				if (selectedElement != null) {
					((Character) selectedElement).setCurrentAction(action);
				}
			}
			else { // EVENT TYPE IS PATIENT SUPPORT
				action.addPatient((Patient) selectedElement);
				if(selectedElement instanceof Character)
					((Character) selectedElement).setCurrentAction(action);
			}
			scoreMap.remove(selectedElement);
			nChosenSupporters++;
		}
		return action;
	}
	
	public static boolean checkElementCondition(Element element, String precond) {
		
		//check if condition is Active or Passive
		if(ISemanticRep.check(precond)) { // Active 
			
			String[] str = precond.split(CONDITION_DELIMITER);
			
			if(element instanceof Character) {
				return SOMEOBJECT.checkPassiveSemRep(str[1], "#character", str[2]);
			}
			else {
				return SOMEOBJECT.checkPassiveSemRep(str[1], element.getName(), str[2]);
			}
			
		}
		else { // Passive
			String[] str = precond.split(CONDITION_DELIMITER);
			if(precond.contains(ActiveSemanticRelation.IS_ASLEEP)) {
				
				if(element instanceof Character) {
					if(((Character) element).isAsleep() == Boolean.parseBoolean(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.IS_HUNGRY)) {
				
				if(element instanceof Character) {
					if(((Character) element).isHungry() == Boolean.parseBoolean(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.IS_THIRSTY)) {
				
				if(element instanceof Character) {
					if(((Character) element).isThirsty() == Boolean.parseBoolean(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.IS_TIRED)) {
				
				if(element instanceof Character) {
					if(((Character) element).isTired() == Boolean.parseBoolean(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.HOLDS)) {

				try {
					if((((Character) element).getItem() == null && str[str.length-1].equalsIgnoreCase("null")))
						return true;
					else
						return (((Character) element).getItem() != null && !str[str.length - 1].equalsIgnoreCase("null"));
				} catch(Exception e) {
					return false;
				}

			}
			else if(precond.contains(ActiveSemanticRelation.NOT_EMOTION_OF)) {
				
				if(element instanceof Character) {
					if(((Character) element).getEmotion().equalsIgnoreCase(str[str.length-1])) {
						return false;
					}
				}
				return true;
				
			}
			else if(precond.contains(ActiveSemanticRelation.EMOTION_OF)) {
				
				if(element instanceof Character) {
					if(((Character) element).getEmotion().equalsIgnoreCase(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.GENDER_OF)) {
				
				if(element instanceof Character) {
					if(((Character) element).getGender() == Integer.parseInt(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.NOT_LOCATION_OF)) {
				
				if(element instanceof Character) {
					if(((Character) element).getLocation().equalsIgnoreCase(str[str.length-1])) {
						return false;
					}
				}
				return true;
				
			}
			else if(precond.contains(ActiveSemanticRelation.LOCATION_OF)) {
				
				if(element instanceof Character) {
					if(((Character) element).getLocation().equalsIgnoreCase(str[str.length-1])) {
						return true;
					}
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.NOT_TRAIT_OF)) {
	
				if(element instanceof Character) {
					return !((Character) element).checkTrait(str[str.length-1]);
				}
				return true;
				
			}
			else if(precond.contains(ActiveSemanticRelation.TRAIT_OF)) {
				
				if(element instanceof Character) {
					return ((Character) element).checkTrait(str[str.length-1]);
				}
				return false;
				
			}
			else if(precond.contains(ActiveSemanticRelation.RELATIONSHIP)) {
				
				
			}
			else if(precond.contains(ActiveSemanticRelation.TIME)) {
				
				
			}
			
		}
		
		return false;
	}
	
	public static boolean checkOtherCondition(Element element, String precond) {
		if(ISemanticRep.check(precond)) {
			
			String[] str = precond.split(CONDITION_DELIMITER);
			return SOMEOBJECT.checkPassiveSemRep(str[1], element.getName(), str[2]);
			
		}
		else {
			String[] str = precond.split(CONDITION_DELIMITER);
			
			if(precond.contains(ActiveSemanticRelation.BACKGROUND)) {
				if(precond.contains(ActiveSemanticRelation.TIME)) {

					return ((Background) element).getTime().equalsIgnoreCase(str[str.length - 1]);
				}
				else if(precond.contains(ActiveSemanticRelation.BACKGROUND_LOCATION)) {

					return element.getName().equalsIgnoreCase(str[str.length - 1]);
				}
			}
		}
		return false;
	}

	public static StoryWorldOld updateStoryWorld2(StoryWorldOld storyworld, Event event) {

		String[] str;
		
		ArrayList<String> conditions = event.getPostconditions();
		
		//update relationship scores
		try {
			for(int i = 0; i < event.getAgents().size(); i++) {
				for(int j = 0; j < event.getPatients().size(); j++) {
		
					try {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).getRelationshipWith((Character)event.getPatients().get(j)).updateScore(event.getRelationshipScore());
					} catch(NullPointerException npe2) {
						Relationship newRelationship; 
						if(event.getRelationshipScore() > 5) {
							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.FRIEND, event.getRelationshipScore());
						}
						else if(event.getRelationshipScore() < 5) {
							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.ENEMY, event.getRelationshipScore());
						}
						else { 
							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.ACQUAINTANCE, event.getRelationshipScore());
						}
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).addRelationship(newRelationship);
					} catch (ClassCastException cce) {
						cce.printStackTrace();
					}
				}
			}
		} catch(NullPointerException npe1) {
			
		}
		
		// apply post conditions
		for(String condition: conditions) {
			str = condition.split(CONDITION_DELIMITER);
			
			if(condition.contains(ActiveSemanticRelation.MAIN_AGENT)) {
				if(condition.contains(ActiveSemanticRelation.IS_ASLEEP)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).isAsleep(Boolean.parseBoolean(str[str.length-1]));
				}
				else if(condition.contains(ActiveSemanticRelation.IS_HUNGRY)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).isHungry(Boolean.parseBoolean(str[str.length-1]));
				}
				else if(condition.contains(ActiveSemanticRelation.IS_THIRSTY)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).isThirsty(Boolean.parseBoolean(str[str.length-1]));
				}
				else if(condition.contains(ActiveSemanticRelation.IS_TIRED)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).isTired(Boolean.parseBoolean(str[str.length-1]));
				}
				else if(condition.contains(ActiveSemanticRelation.EMOTION_OF)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).setEmotion(str[str.length-1]);
				}
				else if(condition.contains(ActiveSemanticRelation.TRAIT_OF)) {
					
					//must remove conflicting trait
					
					storyworld.getCharacterByID(event.getMainAgent().getID()).addTrait(str[str.length-1]);
				}
				else if(condition.contains(ActiveSemanticRelation.LOCATION_OF)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).setLocation(str[str.length-1]);
				}
				else if(condition.contains(ActiveSemanticRelation.HOLDS)) {
					storyworld.getCharacterByID(event.getMainAgent().getID()).setItem(event.getInstrument());
				}
			}
			else if(condition.contains(ActiveSemanticRelation.SUPPORT_AGENT)) {
				if(condition.contains(ActiveSemanticRelation.IS_ASLEEP)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).isAsleep(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_HUNGRY)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).isHungry(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_THIRSTY)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).isThirsty(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_TIRED)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).isTired(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.EMOTION_OF)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).setEmotion(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.TRAIT_OF)) {
					
					//must remove conflicting trait
					
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).addTrait(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.LOCATION_OF)) {
					for(int i = 1; i < event.getAgents().size(); i++) {
						storyworld.getCharacterByID(event.getAgents().get(i).getID()).setLocation(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.HOLDS)) {
					Random rand = new Random();
					int i = rand.nextInt(event.getAgents().size()-1) + 1;
					storyworld.getCharacterByID(event.getAgents().get(i).getID()).setItem(event.getInstrument());
				}
			}
			else if(condition.contains(ActiveSemanticRelation.PATIENT)) {
				if(condition.contains(ActiveSemanticRelation.IS_ASLEEP)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).isAsleep(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_HUNGRY)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).isHungry(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_THIRSTY)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).isThirsty(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.IS_TIRED)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).isTired(Boolean.parseBoolean(str[str.length-1]));
					}
				}
				else if(condition.contains(ActiveSemanticRelation.EMOTION_OF)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).setEmotion(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.TRAIT_OF)) {
					
					//must remove conflicting trait
					
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).addTrait(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.LOCATION_OF)) {
					for(int i = 0; i < event.getPatients().size(); i++) {
						storyworld.getCharacterByID(event.getPatients().get(i).getID()).setLocation(str[str.length-1]);
					}
				}
				else if(condition.contains(ActiveSemanticRelation.HOLDS)) {
					Random rand = new Random();
					int i = rand.nextInt(event.getPatients().size());
					storyworld.getCharacterByID(event.getPatients().get(i).getID()).setItem(event.getInstrument());
				}
			}
			else if(condition.contains(ActiveSemanticRelation.BACKGROUND)) {
				
			}
			else if(condition.contains(ActiveSemanticRelation.INSTRUMENT)) {
				
			}
			
			
			//RELATIONSHIPS
			//postcondition for relationships
			//relationship:agent:patient:3
			//relationship:agent:patient:-3
			else if(condition.contains(ActiveSemanticRelation.RELATIONSHIP)) {
				ArrayList<Agent> agents = event.getAgents();
				ArrayList<Patient> patients = event.getPatients();
				int points = Integer.parseInt(str[str.length-1]);
				for(Agent agent : agents) {
					for(Patient patient : patients) {
						storyworld.getCharacterByID(agent.getID()).getRelationshipWith((Character) patient).updateScore(points);
					}
				}
			}
			//TIME
			else if(condition.contains(ActiveSemanticRelation.TIME)) {
				// if stories can have multiple time frames,
				// insert code here
				// can be disregarded first
				
			}
		}
		
		return storyworld;
	}
	
//	
//	public static StoryWorldOld updateStoryWorld(StoryWorldOld storyworld, Event event) {
//
//		String[] str;
//		
//		ArrayList<String> conditions = event.getPostconditions();
//		
//		//update relationship scores
//		try {
//			for(int i = 0; i < event.getAgents().size(); i++) {
//				for(int j = 0; j < event.getPatients().size(); j++) {
//		
//					try {
//						storyworld.getCharacterByID(event.getAgents().get(i).getID()).getRelationshipWith((Character)event.getPatients().get(j)).updateScore(event.getRelationshipScore());
//					} catch(NullPointerException npe2) {
//						Relationship newRelationship; 
//						if(event.getRelationshipScore() > 5) {
//							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.FRIEND, event.getRelationshipScore());
//						}
//						else if(event.getRelationshipScore() < 5) {
//							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.ENEMY, event.getRelationshipScore());
//						}
//						else { 
//							newRelationship = new Relationship((Character)event.getPatients().get(j), Relationship.ACQUAINTANCE, event.getRelationshipScore());
//						}
//						storyworld.getCharacterByID(event.getAgents().get(i).getID()).addRelationship(newRelationship);
//					} catch (ClassCastException cce) {
//
//					}
//				}
//			}
//		} catch(NullPointerException npe1) {
//			
//		}
//		
//		// apply post conditions
//		for(String condition: conditions) {
//			str = condition.split(CONDITION_DELIMITER);
//			//AGENTS
//			if(condition.contains(ActiveSemanticRelation.AGENT_IS_TIRED)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).isTired(Boolean.parseBoolean(str[str.length-1]));
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_ASLEEP)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).isAsleep(Boolean.parseBoolean(str[str.length-1]));
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_HUNGRY)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).isHungry(Boolean.parseBoolean(str[str.length-1]));
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_THIRSTY)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).isThirsty(Boolean.parseBoolean(str[str.length-1]));
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.EMOTION_OF_AGENT)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).setEmotion(str[str.length-1]);
//				}
//				
//			}
//			else if(condition.contains(ActiveSemanticRelation.TRAIT_OF_AGENT)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//				// need to change this
//				// mali pa to
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).addTrait(str[str.length-1]);
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_HOLDS)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).setItem(event.getInstrument());
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.LOCATION_OF_AGENT)) {
//				for(int i = 0; i < event.getAgents().size(); i++) {
//					storyworld.getCharacterByID(event.getAgents().get(i).getID()).setLocation(str[str.length-1]);
//				}
//			}
//			
//			//PATIENTS
//			// all patients will be updated by the postcondition except for the Item.
//			//	for the HOLDS active semantic relation, only the first patient will be updated
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_TIRED)) {
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).isTired(Boolean.parseBoolean(str[str.length-1]));
//					}
//				} catch(NullPointerException npe1) {
//					
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_ASLEEP)) {
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).isAsleep(Boolean.parseBoolean(str[str.length-1]));
//					}
//				} catch(NullPointerException npe1) {
//					
//				}
//				
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_HUNGRY)) {
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).isHungry(Boolean.parseBoolean(str[str.length-1]));
//					}
//				} catch(NullPointerException npe1) {
//					
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_THIRSTY)) {
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).isThirsty(Boolean.parseBoolean(str[str.length-1]));
//					}
//				}
//				catch(NullPointerException npe1) {
//					
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.EMOTION_OF_PATIENT)) {
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).setEmotion(str[str.length-1]);
//					}
//				} catch (NullPointerException npe1) {
//					
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.TRAIT_OF_PATIENT)) {
//				
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_HOLDS)) {
//				storyworld.getCharacterByID(event.getPatients().get(0).getID()).setItem(event.getInstrument());
//			}
//			else if(condition.contains(ActiveSemanticRelation.LOCATION_OF_PATIENT)) {
//				
//				ArrayList<Patient> patients = event.getPatients();
//				try {
//					for(Patient patient: patients) {
//						storyworld.getCharacterByID(patient.getID()).setLocation(str[str.length-1]);
//					}
//				} catch(NullPointerException npe1) {
//					
//				}
//			}
//			
//			//RELATIONSHIPS
//			//postcondition for relationships
//			//relationship:agent:patient:3
//			//relationship:agent:patient:-3
//			else if(condition.contains(ActiveSemanticRelation.RELATIONSHIP)) {
//				ArrayList<Agent> agents = event.getAgents();
//				ArrayList<Patient> patients = event.getPatients();
//				int points = Integer.parseInt(str[str.length-1]);
//				for(Agent agent : agents) {
//					for(Patient patient : patients) {
//						storyworld.getCharacterByID(agent.getID()).getRelationshipWith((Character) patient).updateScore(points);
//					}
//				}
//			}
//			//TIME
//			else if(condition.contains(ActiveSemanticRelation.TIME)) {
//				// if stories can have multiple time frames,
//				// insert code here
//				// can be disregarded first
//				
//			}
//		}
//		
//		return storyworld;
//	}
//
//	
//	@Deprecated
//	public boolean checkCondition(StoryWorldOld storyWorld, String condition, ArrayList<Agent> agents, ArrayList<Patient> patients) {
//		
//		//check condition if it is an Active or Passive Semrep
//		if(ISemanticRep.check(condition)) {
//			//check if condition is in the database
//		}
//		else { //condition is a story world state
//			String[] str;
//			str = condition.split(CONDITION_DELIMITER);
//			
//			//AGENTS
//			if(condition.contains(ActiveSemanticRelation.AGENT_IS_TIRED)) {
//				if(((Character) agents.get(0)).isTired() == Boolean.parseBoolean(str[str.length-1])) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_ASLEEP)) {
//				if(((Character) agents.get(0)).isAsleep() == Boolean.parseBoolean(str[str.length-1])) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_HUNGRY)) {
//				if(((Character) agents.get(0)).isHungry() == Boolean.parseBoolean(str[str.length-1])) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_IS_THIRSTY)) {
//				if(((Character) agents.get(0)).isThirsty() == Boolean.parseBoolean(str[str.length-1])) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.EMOTION_OF_AGENT)) {
//				if(((Character) agents.get(0)).getEmotion().equalsIgnoreCase(str[str.length-1])) {
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.GENDER_OF_AGENT)) {
//				if(((Character) agents.get(0)).getGender()== Integer.parseInt((str[str.length-1]))) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.TRAIT_OF_AGENT)) {
//				for(int i = 0; i < ((Character) agents.get(0)).getTraits().size(); i++) {
//					//Log.i("AA", agents.get(0).getTraits().get(i) + " " + str[str.length-1]);
//					if(((Character) agents.get(0)).getTraits().get(i).equalsIgnoreCase(str[str.length-1])) {
//						return true;
//					}
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.AGENT_HOLDS)) {
//				try {
//					if(((Character) agents.get(0)).getItem().getsName().equalsIgnoreCase(str[str.length-1])) {
//						return true;
//					}
//					else {
//						return false;
//					}
//				} catch(Exception e) {
//					return false;
//				}
//			}
//			else if(condition.contains(ActiveSemanticRelation.LOCATION_OF_AGENT)) {
//
//				if(storyWorld.getBackground().getsName().equalsIgnoreCase(str[str.length - 1])) {
//					return true;
//				}
//				return false;
////				if(agents.get(0).getLocation().equalsIgnoreCase(str[str.length-1])) {
////					return true;
////				}
////				else {
////					return false;
////				}
//			}
//			// PATIENTS
//			// 	- all patients should satisfy the precondition to be true, otherwise, it will return false: 
//			//		except for the Item, if an item is held even by just one patient, then it returns true
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_TIRED)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).isTired() != Boolean.parseBoolean(str[str.length-1])) {
//							return false;
//						}
//					}
//					return true;
//				}
//				
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_ASLEEP)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).isAsleep() != Boolean.parseBoolean(str[str.length-1])) {
//							return false;
//						}
//					}
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_HUNGRY)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).isHungry() != Boolean.parseBoolean(str[str.length-1])) {
//							return false;
//						}
//					}
//					return true;
//				}
//				return false;	
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_IS_THIRSTY)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).isThirsty() != Boolean.parseBoolean(str[str.length-1])) {
//							return false;
//						}
//					}
//					
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.EMOTION_OF_PATIENT)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(!((Character) patients.get(i)).getEmotion().equalsIgnoreCase(str[str.length-1])) {
//							return false;
//						}
//					}
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.GENDER_OF_PATIENT)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).getGender() !=  Integer.parseInt(str[str.length-1])) {
//							return false;
//						}
//					}
//					
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.TRAIT_OF_PATIENT)) {
//				if(patients != null) {
//					for(int i = 0; i < patients.size(); i++) {
//						if(!((Character) patients.get(i)).getTraits().contains(str[str.length-1])) {
//							return false;
//						}
//					}
//					return true;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.PATIENT_HOLDS)) {
//				try {
//					for(int i = 0; i < patients.size(); i++) {
//						if(((Character) patients.get(i)).getItem().getsName().equalsIgnoreCase(str[str.length-1])) {
//							return true;
//						}
//					}
//				} catch (Exception e) {
//					return false;
//				}
//				return false;
//			}
//			else if(condition.contains(ActiveSemanticRelation.LOCATION_OF_PATIENT)) {
//				
//				if(storyWorld.getBackground().getsName().equalsIgnoreCase(str[str.length - 1])) {
//					return true;
//				}
//				return false;
////				if(patients != null) {
////					for(int i = 0; i < patients.size(); i++) {
////						if(!patients.get(i).getLocation().equalsIgnoreCase(str[str.length - 1])) {
////							return false;
////						}
////					}
////					return true;
////				}
////				return false;
//			}
//			
//			//RELATIONSHIPS
//			else if(condition.contains(ActiveSemanticRelation.RELATIONSHIP)) {
//				for(int i = 0; i < patients.size(); i++) {
//					if(!((Character) patients.get(i)).
//							getRelationshipWith(
//									(Character)agents.get(0)).
//									getRelationship().
//									equalsIgnoreCase(str[str.length-1])) {
//						return false;
//					}
//				}
//				return true;
//			}
//			
//			//TIME
//			else if(condition.contains(ActiveSemanticRelation.TIME)) {
//				str = condition.split(CONDITION_DELIMITER);
//				if(storyWorld.getBackground().getTime().equalsIgnoreCase(str[str.length-1])) {
//					return true;
//				}
//				else {
//					return false;
//				}
//			}
//			
//		}
//		
//		return false;
//	}

}
