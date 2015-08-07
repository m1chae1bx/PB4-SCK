package process.storyplanning;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import database.SOMEOBJECT;
import model.charmodel.Relationship;
import model.narratologicalmodel.Theme;
import model.storyplanmodel.Event;
import model.storyplanmodel.StoryPlan;
import model.storyworldmodel.Character;
import model.storyworldmodel.Object;
import model.storyworldmodel.StoryWorldOld;

/**
 * Issues:
 * 		identifyTheme, identifyConflict, and identifyResolution methods
 * 			- what if there is no possible themes, conflicts, or resolution???
 * 			- left unhandled
 * @author Zem
 *
 */
@Deprecated
public class PlotAgent {

	public static final String tag = "PLOT AGENT";
	
//	/**
//	 * IDENTIFYING THE THEME
//	 * Algorithm 1 (POINT SYSTEM)
//	 * 		- The themes in the database are filtered using the background in the story world.
//	 * 			The remaining themes will have a score system using the related trait and
//	 * 			related background. In case of a tie, the first one will be selected.
//	 * @param storyworld
//	 * @return the selected theme for the story
//	 */
	public Theme identifyTheme(StoryWorldOld storyworld) {
		
		ArrayList<Theme> possibleThemes = SOMEOBJECT.getPossibleThemes(storyworld.getBackground());
		
		int score = 0, highscore = 0;
		Theme selectedTheme = possibleThemes.get(0);
		
		for(Theme theme: possibleThemes) {
			if(storyworld.getMainCharacter().getTraits().contains(theme.getRelatedTrait())) {
				score += 1;
			}
			
			for(Object item: storyworld.getObjects()) {
				if(item.getName().equalsIgnoreCase(theme.getTempObject())) {
					score += 1;
					break;
				}
			}

			Log.i(tag, theme.getMoralLesson() + " " +theme.getRelatedTrait() + " " + score + " " + highscore);
			if(score > highscore) {
				highscore = score;
				selectedTheme = theme;
			}
			
			score=0;
		 }
		
		return selectedTheme.getTheme(selectedTheme.getId());
	}
	
	
//	//filter, point...
//	/**
//	 * IDENTIFYING THE CONFLICT
//	 * Algorithm 1 (RANDOM)
//	 * 		- Conflicts are determined by the ConflictOf Semantic Relation and filtered
//	 * 			using the selected theme in the story plan. If many conflicts are returned,
//	 * 			the conflict will be selected randomly.
//	 * @param storyplan
//	 * @return an event which is the conflict / climax of the story
//	 */
	public Event identifyConflict(StoryPlan storyplan) {
		
		ArrayList<Event> possibleConflicts = SOMEOBJECT.getPossibleConflicts(storyplan.getTheme());
		
		Random rand = new Random();
		int selectedConflictIndex = rand.nextInt(possibleConflicts.size());
		
		return possibleConflicts.get(selectedConflictIndex);
	}
	
	/**
	 * IDENTIFYING THE RESOLUTION
	 * Algorithm 1 (RANDOM)
	 * 		- Resolution are determined by the HasResolution Semantic Relation and filtered
	 * 			using the selected conflict in the story plan. If many resolutions are returned,
	 * 			the resolution will be selected randomly.
	 * @param storyplan
	 * @return an event which is the resolution of the story
	 */
	public Event identifyResolution(StoryPlan storyplan) {
		
		ArrayList<Event> possibleResolution = SOMEOBJECT.getPossibleResolutions(storyplan.getConflict());
		
		Random randomGenerator = new Random();
		int selectedResolutionIndex = randomGenerator.nextInt(possibleResolution.size());
		
		return possibleResolution.get(selectedResolutionIndex);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StoryWorldOld initState(StoryWorldOld storyworld) {
		
		Random rand = new Random();
		
		//set time
		ArrayList<String> timeList = SOMEOBJECT.getRelatedTime(storyworld.getBackground());
		storyworld.getBackground().setTime(timeList.get(rand.nextInt(timeList.size())));
//		storyworld.getBackground().setTime("morning");
		
		//set time description
		ArrayList<String> timeDescList = SOMEOBJECT.getRelatedTimeDesc(storyworld.getTime());
		storyworld.getBackground().setTimeDesc(timeDescList.get(rand.nextInt(timeDescList.size())));
//		storyworld.getBackground().setTimeDesc("sunny");
		
		//set background description
		ArrayList<String> bgDescList = SOMEOBJECT.getRelatedBGDesc(storyworld);
		storyworld.getBackground().setBGDesc(bgDescList.get(rand.nextInt(bgDescList.size())));
//		storyworld.getBackground().setBGDesc("scary");
		
		//set states of the characters
		for(Character c : storyworld.getCharacters()) {
			
			c.setLocation(storyworld.getBackground().getName());
			
			c.setEmotion("neutral");
			c.setItem(null);
			
			c.isHungry(false);
			c.isThirsty(false);
			c.isTired(false);
			c.isAsleep(false);
		}
		
		return storyworld;
	}
	
	
	public Event identifySupporterGoal(Character ca, Event maingoal, Character mainCharacter) {

		ArrayList<Event> goalList = new ArrayList<Event>();
		
		if(ca.getRelationshipWith(mainCharacter).getScore() < 5) {
			goalList = SOMEOBJECT.getPossibleSupportingGoals(maingoal, Relationship.ENEMY);
		}
		else {
			goalList = SOMEOBJECT.getPossibleSupportingGoals(maingoal, Relationship.FRIEND);
		}
		return goalList.get(new Random().nextInt(goalList.size()));
	}
	
	/**
	 * Accepts or rejects an action based on the preconditions (instruments and background related preconditions).
	 * @param storyplot
	 * @param character
	 * @param action
	 * @return TRUE if action reaches the given threshold. Otherwise, FALSE.
	 */
	public boolean checkAction(Character character, Event action, int storyplot) {
		
		double threshold = 0.5;
		double score = 0;
		
		
		if(storyplot == Event.CONFLICT) {
			// resolution event can't be chosen if the story plot is in the conflict
			if (action.equals(StoryPlanner.storyplan.getResolution())) {
				Log.i(tag, "A");
				return false;
			}
			// conflict event can't be chosen if supporting characters haven't reached yet their goals
			if (action.equals(StoryPlanner.storyplan.getConflict())) {
				for (Character c : StoryPlanner.storyworld.getSupportingCharacters()) {
					if(!c.hasReachedGoal(storyplot)) {
						Log.i(tag, "AA");
						return false;
					}
				}
			}
		}
		else if(storyplot == Event.RESOLUTION) {
			// conflict event can't be chosen if the story plot  is in the resolution
			if (action.equals(StoryPlanner.storyplan.getConflict())) {
				Log.i(tag, "AAA");
				return false;
			}
			// resolution event can't be chosen if supporting characters haven't reached yet their goals
			if (action.equals(StoryPlanner.storyplan.getResolution())) {
				for (Character c : StoryPlanner.storyworld.getSupportingCharacters()) {
					Log.i(tag, c.getName() + " " + c.getCurrentActionString() + " -- " + c.getResolutionEvent().getAction() +" " + storyplot +"");
					if(!c.hasReachedGoal(storyplot)) {
						Log.i(tag, "AAAA");
						return false;
					}
				}
			}
		}
		
		// supporting characters can't choose the story plot events (conflict and resolution events)
//		if(!action.equals(storyplan.getConflict()) && !action.equals(storyplan.getResolution())) {
//			if(action.getAction().equalsIgnoreCase(storyplan.getConflict().getAction()) || 
//				action.getAction().equalsIgnoreCase(storyplan.getResolution().getAction())) {
//				return false;
//			}
//		}

		if (!action.equals(StoryPlanner.storyplan.getResolution())) {
			if (action.getAction().equalsIgnoreCase(StoryPlanner.storyplan.getResolution().getAction())) {
				Log.i(tag, "AAAAA");
				return false;
			}
		}

		if(action.getOhterPreCond() == null) {
			return true;
		}
		
		for(String precond : action.getOhterPreCond()) {
			
			if(precond.contains("Background")) {
				Log.i(tag, precond + " " + WorldAgent.checkOtherCondition(StoryPlanner.storyworld.getBackground(), precond));
				if (WorldAgent.checkOtherCondition(StoryPlanner.storyworld.getBackground(), precond)) {
					score += 1;
				} else if (!WorldAgent.checkOtherCondition(StoryPlanner.storyworld.getBackground(), precond) && precond.contains(WorldAgent.MANDATORY_CONDITION)) {
					score = 0;
					break;
				}
				
			}
			else if(precond.contains("Instrument")) {
				for (Object possibleItem : StoryPlanner.storyworld.getObjects()) {
						
					if(possibleItem.isCapableOf(action.getAction())) {
						character.setItem(possibleItem);
						action.setInstrument(possibleItem);
						break;
					}
				}
				
				if(character.getItem() == null) {
					ArrayList<String> possibleObjects = SOMEOBJECT.getObjectsUsedFor(action.getAction());
					
					try {
						int index = new Random().nextInt(possibleObjects.size());
						Object newObj = new Object(possibleObjects.get(index));
						StoryPlanner.storyworld.addObject(newObj);
						character.setItem(newObj);
						action.setInstrument(newObj);
					} catch(NullPointerException npe) {
						
					} catch(IllegalArgumentException iae) {
						
					}
				}
					
				if(character.getItem() == null && precond.contains(WorldAgent.MANDATORY_CONDITION)) {
					score = 0;
					break;
				}
				if(character.getItem() != null && WorldAgent.checkOtherCondition(character.getItem(), precond)) {
					score += 1;
				}
				else if(character.getItem() != null && WorldAgent.checkOtherCondition(character.getItem(), precond) &&
						precond.contains(WorldAgent.MANDATORY_CONDITION)) {
					score = 0;
					break;
				}
			}
		}
		score /= action.getOhterPreCond().size();
		
		if(score >= threshold) {
			return true;
		}
		Log.i(tag, "AAAAAA");
		return false;
	}


	public ArrayList<Event> getSubeventsOf(Event mainAction) {
		ArrayList<Event> subeventList = new ArrayList<Event>();
		
		subeventList.add(mainAction);
		
		ArrayList<Event> tempList = SOMEOBJECT.getSubEvents(mainAction);

		try {
			Event e = tempList.get(new Random().nextInt(tempList.size()));
			e.addAgent(mainAction.getMainAgent());
			
			if(e.getEventType().equalsIgnoreCase(Event.TYPE_AGENTS_SUPPORT) || 
					e.getEventType().equalsIgnoreCase(Event.TYPE_PATIENTS_SUPPORT)) {
				e = WorldAgent.selectSupporters(StoryPlanner.storyworld, StoryPlanner.storyworld.getPossibleSupporters((Character) e.getMainAgent()), e);
			}
			
			subeventList.add(e);
		} catch(IllegalArgumentException iae) {
			
		} catch(IndexOutOfBoundsException npe) {
			
		}
		
		return subeventList;
	}
}
