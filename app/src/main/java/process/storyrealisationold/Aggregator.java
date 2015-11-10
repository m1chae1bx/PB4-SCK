package process.storyrealisationold;

import model.storyplanmodel.Agent;
import model.storyplanmodel.StoryPlan;

public class Aggregator {

	
	public static void aggregate(StoryPlan storyPlan){
		
		for(int i = 1; i< storyPlan.getStoryEvents().size(); i++){
			
				//same action of previous event
				if( storyPlan.getStoryEvents().get(i -1).getAction().equals(storyPlan.getStoryEvents().get(i).getAction()) &&
					(	(storyPlan.getStoryEvents().get(i-1).getPatients() == null && storyPlan.getStoryEvents().get(i).getPatients()==null) || 
						(storyPlan.getStoryEvents().get(i-1).getPatients().equals(storyPlan.getStoryEvents().get(i).getPatients()))  
						
					)
				  ){
					
					for(Agent c : storyPlan.getStoryEvents().get(i).getAgents()){
						storyPlan.getStoryEvents().get(i-1).getAgents().add(c);
						
					}
					for(Agent c : storyPlan.getStoryEvents().get(i).getSupportingAgents()){
						storyPlan.getStoryEvents().get(i-1).getSupportingAgents().add(c);
						
					}
					storyPlan.getStoryEvents().remove(i);
					i--;
					
					
				
				}
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
