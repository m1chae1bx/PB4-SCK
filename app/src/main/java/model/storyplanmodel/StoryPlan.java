package model.storyplanmodel;

import java.util.ArrayList;

import model.narratologicalmodel.Theme;
import model.storyworldmodel.StoryWorldOld;

public class StoryPlan {

	private StoryWorldOld initialStoryWorld;
	private ArrayList<Event> storyevents;
	
	private Theme theme;
	private Event conflictEvent;
	private Event resolutionEvent;
	
	public void addEvent(Event event) {
//		if(storyevents == null) {
//			storyevents = new ArrayList();
//		}
//		storyevents.add(event);
	}
	public ArrayList<Event> getStoryEvents() {
		return storyevents;
	}
	
	public StoryWorldOld getInitialStoryWorld() {
		return initialStoryWorld;
	}
	public void setInitialStoryWorld(StoryWorldOld initialStoryWorld) {
		this.initialStoryWorld = initialStoryWorld;
	}
	
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	public Event getConflict() {
		return conflictEvent;
	}
	public void setConflict(Event conflictEvent) {
		this.conflictEvent = conflictEvent;
	}
	
	public Event getResolution() {
		return resolutionEvent;
	}
	public void setResolution(Event resolutionEvent) {
		this.resolutionEvent = resolutionEvent;
	}

}
