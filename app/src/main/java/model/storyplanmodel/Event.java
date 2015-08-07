package model.storyplanmodel;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import database.DBField;
import model.ontologymodel.ActiveSemanticRelation;
import model.storyworldmodel.Character;
import model.storyworldmodel.Object;

@Deprecated
public class Event {

	public static final String TYPE_AGENTS_SUPPORT = "agentSupporters";
	public static final String TYPE_PATIENTS_SUPPORT = "patientSupporters";
	public static final String EVENT_DELIMITER = " & ";
	public static final String MANDATORY_PRECONDITION = "*";

	public static int CONFLICT = 1;
	public static int RESOLUTION = 2;

	private String action; 
	private String eventType;
	private String dialogueType;
	private ArrayList<Agent> agents;
	private ArrayList<Patient> patients;
	private int maxAgents;
	private int maxPatients;
	private Object instrument;
	private ArrayList<String> preconditions;
	private ArrayList<String> postconditions;
	private int interactionScore;
	private int relationshipScore;
	private String conflict;
	private boolean isVisited = false;
	private ArrayList<Event> adjacentEvents;

	// CONSTRUCTORS
	public Event() {

	}
	public Event(String event, int maxAgents, int maxPatients, String precondition, String postcondition, int interactionScore, int relationshipScore, String eventType, String dialogueType) {

		action = event;
		this.eventType = eventType;
		this.dialogueType = dialogueType;
		agents = null;
		patients = null;
		this.maxAgents = maxAgents;
		this.maxPatients = maxPatients;
		instrument = null;

		String[] preconditionList = precondition.split(EVENT_DELIMITER);
		preconditions = new ArrayList<>(Arrays.asList(preconditionList));

		String[] postconditionList = postcondition.split(EVENT_DELIMITER);
		postconditions = new ArrayList<>(Arrays.asList(postconditionList));

		this.interactionScore = interactionScore;
		this.relationshipScore = relationshipScore;

	}

	public String getDialogueType() {
		return dialogueType;
	}

	public void setDialogueType(String dialogueType) {
		this.dialogueType = dialogueType;
	}
 
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEventType() {
		return eventType;
	}
	
	// AGENT RELATED METHODS
	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	public void addAgent(Agent agent) {
		if(agents == null) {
			agents = new ArrayList<>();
		}
		agents.add(agent);
	}
	public int getMaxAgents() {
		return maxAgents;
	}
	public Agent getMainAgent() {
		return agents.get(0);
	}
	public ArrayList<Agent> getSupportingAgents() {
//		ArrayList<Agent> supportAgentList = new ArrayList<Agent>();
//		for(int i = 1; i < agents.size(); i++) {
//			supportAgentList.add(agents.get(i));
//		}
//		return supportAgentList;
		return new ArrayList<>(agents.subList(1, agents.size()));
	}
	
	// PATIENT RELATED METHODS
	public ArrayList<Patient> getPatients() {
		return patients;
	}
	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	public void addPatient(Patient patient) {
		if(patients == null) {
			patients = new ArrayList<>();
		}
		patients.add(patient);
	}
	public int getMaxPatients() {
		return maxPatients;
	}
	
	// INSTRUMENT RELATED METHODS
	public Object getInstrument() {
		return instrument;
	}
	public void setInstrument(Object instrument) {
		this.instrument = instrument;
	}
	
	// INTERACTION RELATED METHODS
	public int getInteractionScore() {
		return interactionScore;
	}
	public int getRelationshipScore() {
		return relationshipScore;
	}
	
	// PRECONDITION RELATED METHODS
	public ArrayList<String> getPreconditions() {
		return preconditions;
	}
	public ArrayList<String> getMainAgentRelatedPreCond() {
		ArrayList<String> agentPrecondList = new ArrayList<>();
		
		for(String precondition : preconditions) {
			if(precondition.contains(ActiveSemanticRelation.MAIN_AGENT))
				agentPrecondList.add(precondition);
		}
		if(agentPrecondList.size() == 0)
			return null;
		return agentPrecondList;
	}
	public ArrayList<String> getSupportAgentRelatedPreCond() {
		ArrayList<String> agentPrecondList = new ArrayList<>();
		
		for(String precondition : preconditions) {
			if(precondition.contains(ActiveSemanticRelation.SUPPORT_AGENT) && !precondition.contains(ActiveSemanticRelation.RELATIONSHIP))
				agentPrecondList.add(precondition);
		}
		if(agentPrecondList.size() == 0)
			return null;
		return agentPrecondList;
	}
	public ArrayList<String> getPatientRelatedPreCond() {
		ArrayList<String> patientPrecondList = new ArrayList<>();
		
		for(String precondition : preconditions) {
			if(precondition.contains(ActiveSemanticRelation.PATIENT) && !precondition.contains(ActiveSemanticRelation.RELATIONSHIP))
				patientPrecondList.add(precondition);
		}
		if(patientPrecondList.size() == 0)
			return null;
		return patientPrecondList;
	}
	public ArrayList<String> getOhterPreCond() {
		ArrayList<String> otherPreCondList = new ArrayList<>();
		
		for(String precondition : preconditions) {
			if(!precondition.contains(ActiveSemanticRelation.MAIN_AGENT) && 
				!precondition.contains(ActiveSemanticRelation.SUPPORT_AGENT) && 
				!precondition.contains(ActiveSemanticRelation.PATIENT)) {
				otherPreCondList.add(precondition);
			}
		}
		if(otherPreCondList.size() == 0)
			return null;
		return otherPreCondList;
	}
	
	// POST-CONDITION RELATED METHODS
	public ArrayList<String> getPostconditions() {
		return postconditions;
	}
	
	// ADDITIONAL METHODS
	public boolean isVisited() {
		return isVisited;
	}
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	public void addAdjacentEvent(Event adjacentEvent) {
		if(adjacentEvents == null) {
			adjacentEvents = new ArrayList<Event>();
		}
		adjacentEvents.add(adjacentEvent);
	}
	public ArrayList<Event> getAdjacentEvents() {
		return adjacentEvents;
	}

	public String getConflict() {
		return conflict;
	}

	public void setConflict(String string) {
		conflict = string;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
		Event event = (Event) o;
		
		if(this.getMainAgent() instanceof Character)
			if(!(event.getMainAgent() instanceof Character))
				return false;
		if(this.getMainAgent() instanceof Object)
			if(!(event.getMainAgent() instanceof Object))
				return false;
		if(this.agents.get(0).getID() != event.getAgents().get(0).getID())
			return false;
		if(!this.action.equals(event.getAction()))
			return false;
		if(!(this.interactionScore == event.getInteractionScore()))
			return false;
		if(!(this.relationshipScore == event.getRelationshipScore()))
			return false;
		for(String precondition: preconditions) {
			if(!event.getPreconditions().contains(precondition)) 
				return false;
		}
		for(String postcondition: postconditions) {
			if(!event.getPostconditions().contains(postcondition))
				return false;
		}
		return true;
	}
	
	/**
	 * Adds an event in the database
	 * @param db
	 * @param concept
	 * @param maxPatients
	 * @param preconditions
	 * @param postconditions
	 * @param interactionScore
	 * @param relationshipScore
	 * @param type
	 */
	public void addEvent(SQLiteDatabase db, String concept, int maxAgents, int maxPatients, String preconditions, 
						String postconditions, int interactionScore, int relationshipScore, String type, String dialogueType) {
		
		ContentValues cv = new ContentValues();
		
		cv.put(DBField.COLUMN_EVENT_NAME, concept);
		cv.put(DBField.COLUMN_MAX_AGENTS, maxAgents);
		cv.put(DBField.COLUMN_MAX_PATIENTS, maxPatients);
		cv.put(DBField.COLUMN_PRECONDITION, preconditions);
		cv.put(DBField.COLUMN_POSTCONDITION, postconditions);
		cv.put(DBField.COLUMN_INTERACTIONSCORE, interactionScore);
		cv.put(DBField.COLUMN_IRSCORE, relationshipScore);
		cv.put(DBField.COLUMN_EVENT_TYPE, type);
		cv.put(DBField.COLUMN_DIALOGUE_TYPE, dialogueType);
		
		db.insert(DBField.TABLE_EVENT, null, cv);
	}


}
