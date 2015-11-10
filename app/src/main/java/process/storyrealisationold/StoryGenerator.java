package process.storyrealisationold;

import model.storyplanmodel.StoryPlan;

public class StoryGenerator {
	
	private StoryPlan storyPlan;
	


	public StoryGenerator(StoryPlan storyPlan){
		this.storyPlan = storyPlan;
	}
	
	public String generateTitle() {
		return SurfaceRealiser.getStoryTitle();
	}
	
	public String generateStory(){
		Lexicalizer.lexicalize(storyPlan);
		Aggregator.aggregate(storyPlan);
		
		return SurfaceRealiser.getOutputStory(storyPlan);
	}

	
	
	
	public StoryPlan getStoryPlan() {
		return storyPlan;
	}

	public void setStoryPlan(StoryPlan storyPlan) {
		this.storyPlan = storyPlan;
	}
}
