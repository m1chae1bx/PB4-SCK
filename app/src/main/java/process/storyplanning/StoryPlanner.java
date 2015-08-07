package process.storyplanning;

import android.util.Log;

import java.util.ArrayList;

import model.narratologicalmodel.Theme;
import model.storyplanmodel.Agent;
import model.storyplanmodel.Event;
import model.storyplanmodel.Patient;
import model.storyplanmodel.StoryPlan;
import model.storyworldmodel.Character;
import model.storyworldmodel.StoryWorldOld;

//import process.storyplanningagents.CharacterAgent;
///
@Deprecated
public class StoryPlanner { // TODO rename
	
	public static final String tag = "STORY PLANNER";
	
	public static StoryWorldOld storyworld;
	public static StoryPlan storyplan;
	public static int LOOP_LIMIT = 20;
	private PlotAgent plotAgent;

	public StoryPlanner(StoryWorldOld storyworld) {

		StoryPlanner.storyworld = storyworld;
		storyplan = new StoryPlan();
		
		plotAgent = new PlotAgent();
	}
	
	/**
	 * @param caList insert desc here
	 * @return true if all characters have reached their goals; otherwise, false
	 */
	private boolean checkGoals(ArrayList<Character> caList, int storyplot) {
		
		for(Character ca : caList) {
			if(!ca.hasReachedGoal(storyplot))
				return false;
		}
		return true;
	}
	
	public StoryPlan createStory() {
		
		
		// IDENTIFYING THE THEME
		Theme selectedTheme = plotAgent.identifyTheme(storyworld);
		storyplan.setTheme(selectedTheme);
		Log.i(tag, "THEME: " + storyplan.getTheme().getMoralLesson());
		
		// IDENTIFYING THE CONFLICT
		Event selectedConflict = plotAgent.identifyConflict(storyplan);
		selectedConflict.addAgent(storyworld.getMainCharacter());
		storyplan.setConflict(selectedConflict);
		Log.i(tag, "CONFLICT: " + storyplan.getConflict().getAction());
		
		// IDENTIFYING THE RESOLUTION
		Event selectedResolution = plotAgent.identifyResolution(storyplan);
		selectedResolution.addAgent(storyworld.getMainCharacter());
		storyplan.setResolution(selectedResolution);
		Log.i(tag, "RESOLUTION: " + storyplan.getResolution().getAction());
		
		// INITIALIZING THE GOALS AND STATES OF THE CHARACTERS
		Event goal;
		for(int i = 0; i < storyworld.getCharacters().size(); i++) {
			if(i == 0) { // if main character
				storyworld.getCharacterByOrder(i).setConflictGoal(selectedConflict);
				storyworld.getCharacterByOrder(i).setResolutionGoal(selectedResolution);
			}
			else { 
				goal = plotAgent.identifySupporterGoal(storyworld.getCharacterByOrder(i), storyworld.getMainCharacter().getConflictGoal(), storyworld.getMainCharacter());
				goal.addAgent(storyworld.getCharacterByOrder(i));
				storyworld.getCharacterByOrder(i).setConflictGoal(goal);
				
				goal = plotAgent.identifySupporterGoal(storyworld.getCharacterByOrder(i), storyworld.getMainCharacter().getResolutionEvent(), storyworld.getMainCharacter());
				goal.addAgent(storyworld.getCharacterByOrder(i));
				storyworld.getCharacterByOrder(i).setResolutionGoal(goal);
			}
			
			Log.i(tag, storyworld.getCharacterByOrder(i).getName());
			Log.i(tag, "Conflict Goal: " + storyworld.getCharacterByOrder(i).getConflictGoal().getAction());
			Log.i(tag, "Resolution Goal: " + storyworld.getCharacterByOrder(i).getResolutionEvent().getAction());
			
		}
		storyworld = plotAgent.initState(storyworld);
		storyplan.setInitialStoryWorld(storyworld);
		
		Log.i(tag, "---INITIAL STORY WORLD---");
		Log.i(tag, storyworld.toString());

		// SELECTING THE EVENTS / ACTIONS OF THE CHARACTERS
		int rrTurn, ctr;
		int ctrStoryPlot = 1, nStoryPlot = 2;
		while(ctrStoryPlot <= nStoryPlot) {
			
			Log.i(tag, "____________________________________________");
			
			rrTurn = 0;
			ctr = 0;
			do {
				
				Log.i(tag, ctr + "");
				
				if(!storyworld.getCharacterByOrder(rrTurn).hasReachedGoal(ctrStoryPlot)) { 
	
					Log.i(tag, storyworld.getCharacterByOrder(rrTurn).getName());
					
					// CHARACTER PROPOSES AN ACTION
					Event proposedAction = storyworld.getCharacterByOrder(rrTurn).identifyAction();
					
					if(proposedAction != null) {
						
						Log.i(tag, "Proposed Action: " + proposedAction.getAction());
						
						//world agent selects the patients of the proposed action
						if(proposedAction.getEventType().equalsIgnoreCase(Event.TYPE_AGENTS_SUPPORT) || 
						   proposedAction.getEventType().equalsIgnoreCase(Event.TYPE_PATIENTS_SUPPORT)) {
							proposedAction = WorldAgent.selectSupporters(storyworld, storyworld.getPossibleSupporters((Character) proposedAction.getMainAgent()), proposedAction);
						}
						
						if(proposedAction != null) {
							//plot agent either accepts the action or rejects it
							if(plotAgent.checkAction(storyworld.getCharacterByOrder(rrTurn), proposedAction, ctrStoryPlot)) {
								Log.i(tag, "Plot Agent accpets " + proposedAction.getAction());
								ArrayList<Event> proposedActionList = plotAgent.getSubeventsOf(proposedAction);
								
								for(Event pAction : proposedActionList) {
									Log.i(tag, "****"+pAction.getAction());
									storyplan.addEvent(pAction);
									storyworld.getCharacterByOrder(rrTurn).setCurrentAction(pAction);
									WorldAgent.updateStoryWorld2(storyworld, pAction);
									
//									for(Agent c : pAction.getSupportingAgents())
//										if(c instanceof Character)
//											((Character) c).setCurrentAction(pAction);
//									for(Patient c : pAction.getPatients())
//										if(c instanceof Character)
//											((Character) c).setCurrentAction(pAction);
								
									////////////////////////////////////////////
									Log.i(tag, "---Event---");
									Log.i(tag, pAction.getMainAgent().getName() + " " + pAction.getAction());
									if(pAction.getEventType().equalsIgnoreCase(Event.TYPE_AGENTS_SUPPORT)) {
										Log.i(tag, "Supporting Agents");
										for(Agent a : pAction.getSupportingAgents()) {
											Log.i(tag, a.getName());
										}
									}
									else if(pAction.getEventType().equalsIgnoreCase(Event.TYPE_PATIENTS_SUPPORT)) {
										Log.i(tag, "Patients");
										try {
											for(Patient p : pAction.getPatients()) {
												Log.i(tag, p.getName());
											}
										} catch(NullPointerException npe) {
											Log.i(tag, "No PATIENT");
										}
									}
									Log.i(tag, "----------");
									Log.i(tag, storyworld.toString());
									
								} // end brace: for loop, proposedActionList
							}
							else {
								Log.i(tag, "Plot Agent rejects " + proposedAction.getAction());
							}
						}
					}
				} // end brace: if
				rrTurn++;
				if(rrTurn == storyworld.getCharacters().size())
					rrTurn = 0;
	
				ctr++;
				
				if(ctr >= LOOP_LIMIT) { // no stories
					Log.i(tag, "NO STORIES CAN BE GENERATED");
					storyplan = null;
					return null;
				}
				
			} while(!checkGoals(storyworld.getCharacters(), ctrStoryPlot)); // end brace: do-while
			
			ctrStoryPlot++;
			
			for(int i = 0; i < storyworld.getCharacters().size(); i++) {
				storyworld.getCharacters().get(i).setCurrentAction(null);
				storyworld.getCharacters().get(i).resetGoalFlag();
			}
			
			
		} // end brace: while
		
		
		for(Event e : storyplan.getStoryEvents()) {
			Log.i("FINAL STORY PLAN", e.getMainAgent().getName() + " " + e.getAction());
		}
		
		return storyplan;
	}

}
