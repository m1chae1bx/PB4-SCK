package process.storyrealisation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import database.SOMEOBJECT;
import model.storyplanmodel.Agent;
import model.storyplanmodel.Event;
import model.storyplanmodel.Patient;
import model.storyplanmodel.StoryPlan;
import model.storyworldmodel.Character;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class DialogueGenerator {
	
	public static void generateDialogue(NLGFactory nlgFactory, Realiser realiser, DocumentElement paragraph,  StoryPlan storyPlan,String dialogueType){
		String dialogue;
		Random random = new Random();
		

		dialogue = Dialogue.getDialogue(dialogueType);
		
		String[] sentence =  dialogue.split("<>");
		ArrayList<Integer> usedChar = new ArrayList<>();
		boolean hasMainCharacter=false;
		int temp;
		int availableChar;
		
		for(String s :sentence){
			if(s.startsWith("<maincharacter>")){
				hasMainCharacter = true;
				break;
			}
			
		}
		if(hasMainCharacter){
			availableChar = storyPlan.getInitialStoryWorld().getSupportingCharacters().size();
		}
		else{	
			availableChar = storyPlan.getInitialStoryWorld().getCharacters().size();
		}
		for(int i =0; i<sentence.length; i++){
		
			
			if(hasMainCharacter){
				temp = random.nextInt(storyPlan.getInitialStoryWorld().getSupportingCharacters().size());
			}
			else{
				temp = random.nextInt(storyPlan.getInitialStoryWorld().getCharacters().size());
			}
			
			//randomize character  
			while(true){
			
				if(!usedChar.contains(temp)){
					
					usedChar.add(temp);
			
					break;
				}
				else if(availableChar <= 0){
					
					break;
				}
				
				else{
					
					if(hasMainCharacter){
						temp = random.nextInt(storyPlan.getInitialStoryWorld().getSupportingCharacters().size());
					}
					else{
						temp = random.nextInt(storyPlan.getInitialStoryWorld().getCharacters().size());
					}
					
				}
				
			}
			
				if(hasMainCharacter){
					if(availableChar > 0){
						sentence[i] = sentence[i].replace("<character>", storyPlan.getInitialStoryWorld().getSupportingCharacters().get(usedChar.get(usedChar.size()-1)).getName());
						
					}
					if(!sentence[i].startsWith("<maincharacter>")){
						availableChar--;
					}else{
						usedChar.remove(new Integer(temp));
						
					}
					
					sentence[i] = sentence[i].replace("<maincharacter>", storyPlan.getInitialStoryWorld().getMainCharacter().getName());
					 
					
					
					
				}
				else{
					 if(availableChar > 0){
						 sentence[i] = sentence[i].replace("<character>", storyPlan.getInitialStoryWorld().getCharacters().get(usedChar.get(usedChar.size()-1)).getName());
						 availableChar--;
					 }
				
				}
				
				
				sentence[i] = sentence[i].replace("<adjtime>", storyPlan.getInitialStoryWorld().getBackground().getTimeDesc());		
				sentence[i] = sentence[i].replace("<time>", storyPlan.getInitialStoryWorld().getBackground().getTime());
				
				sentence[i] = sentence[i].replace("<adjlocation>", storyPlan.getInitialStoryWorld().getBackground().getBGDesc());	
				sentence[i] = sentence[i].replace("<location>", storyPlan.getInitialStoryWorld().getBackground().getName());
				
				sentence[i] = sentence[i].replace("<theme>", storyPlan.getTheme().getMoralLesson());
					
			
			if(!sentence[i].contains("<character>") && !sentence[i].contains("<maincharacter>")){
				paragraph.addComponent(nlgFactory.createSentence(sentence[i]));
			}
			
		}
		
		
		

	}
	
	
	public static void generateDialogue(NLGFactory nlgFactory, Realiser realiser, DocumentElement paragraph,  Event event, StoryPlan storyPlan, String dType){

	
	String dialogue;
		
		dialogue = Dialogue.getDialogue(dType);
		String[] sentence =  dialogue.split("<>");
		 int agent=0, supportingAgent=0, patient=0;
		 
		 boolean hasMainCharacter=false;
			for(String s :sentence){
				if(s.startsWith("<maincharacter>")){
					hasMainCharacter = true;
					break;
				}
				
			}
		 
		 
		 if(event.getAgents()!=null)
		 agent = event.getAgents().size();
	
		if(event.getPatients()!=null)
		 patient =  event.getPatients().size();
		
		
		
		for(int i =0; i<sentence.length; i++){
			NPPhraseSpec np = nlgFactory.createNounPhrase("a", SOMEOBJECT.getCapableOfReceivingAction(event.getAction()));
			NLGElement elem = realiser.realise(np);
			String object = elem.toString();
			
			if(hasMainCharacter){
				
					if(agent>0 && storyPlan.getInitialStoryWorld().getMainCharacter().equals(event.getAgents().get(agent-1))){
						agent--;
						i--;
						continue;
					}

					if(patient>0  &&  !(event.getPatients().get(patient-1) instanceof Character)){
						patient--;
						i--;
						continue;
					}
					
					if(patient>0  && storyPlan.getInitialStoryWorld().getMainCharacter().equals(event.getPatients().get(patient-1))){
							patient--;
							i--;
							continue;
					}
						
					
				
			}
			sentence[i] = sentence[i].replace("<maincharacter>", storyPlan.getInitialStoryWorld().getMainCharacter().getName());
		
				if(agent>0){
					 sentence[i] = sentence[i].replace("<character>", event.getAgents().get(agent-1).getName());
					 if(!sentence[i].startsWith("<maincharacter>")){
						 agent--;	 
					 }
					 
				 }

				 else if(patient>0){
						if(!(event.getPatients().get(patient-1) instanceof Character)){
							patient--;
							i--;
							continue;
						}else{
							sentence[i] = sentence[i].replace("<character>", event.getPatients().get(patient-1).getName());
							 if(!sentence[i].startsWith("<maincharacter>")){
									patient--;
							 }
						
						}
					 
				 }
			
		

					sentence[i] = sentence[i].replace("<action>", event.getAction());
					
					sentence[i] = sentence[i].replace("<object>", object);
				
	
			if(!sentence[i].contains("<character>") && !sentence[i].contains("<maincharacter>")  ){
				paragraph.addComponent(nlgFactory.createSentence(sentence[i]));
			}
			
		}
		
		
		

	}
	
	public static void generateEmotionDialogue(NLGFactory nlgFactory, Realiser realiser, DocumentElement paragraph, Event event, String emotion){
		String dialogue;
		String[]s = emotion.split(":");
		
	//characters
		if(event.getAgents().size()>1){
			dialogue = Dialogue.getDialogue(Dialogue.EMOTION, Dialogue.PLURAL);
		}
		else {
			dialogue = Dialogue.getDialogue(Dialogue.EMOTION, Dialogue.SINGULAR);
			
		}
		
		if(emotion.contains("character:")){
			dialogue = dialogue.replace("<character>", "they");
		}
		else if(emotion.contains("agent:")){
			boolean hasPatientChar = false;
			
			if(event.getPatients() != null){
				for(Patient p : event.getPatients()){
					if(p instanceof Character){
						hasPatientChar =true;
						break;
						
					}
				}
				
			}
		
			if(event.getAgents().size() ==1){
				if(hasPatientChar){
					dialogue = dialogue.replace("<character>", event.getAgents().get(0).getName());
				}else{
					if(((Character)event.getAgents().get(0)).getGender() == Character.MALE){
						dialogue = dialogue.replace("<character>", "he");
					}else{
						dialogue = dialogue.replace("<character>", "she");
					}
				}
				
				
			}
			else{
			
				if(hasPatientChar){
					
					CoordinatedPhraseElement coordinatedphrase = nlgFactory.createCoordinatedPhrase();
					for(Agent a : event.getAgents()){
						coordinatedphrase.addCoordinate(a.getName());
						
					}
					NLGElement elem = realiser.realise(coordinatedphrase);
					String characters = elem.toString();
					dialogue = dialogue.replace("<character>", characters);
					
				}else{
					dialogue = dialogue.replace("<character>", "they");
				}
			
			}
			
		}
		else if(emotion.contains("patient:")){
			CoordinatedPhraseElement coordinatedphrase = nlgFactory.createCoordinatedPhrase();
			if(event.getPatients() != null){
				for(Patient p : event.getPatients()){
					if(p instanceof Character){
						coordinatedphrase.addCoordinate(p.getName());
					}
				}
				
			NLGElement elem = realiser.realise(coordinatedphrase);
			String characters = elem.toString();
			dialogue = dialogue.replace("<character>", characters);
			}
			
		}
		
		
		//emotion
		CoordinatedPhraseElement coordinatedphrase = nlgFactory.createCoordinatedPhrase();
		if(dialogue.contains("<emotion2>")){
			String[] tempDialogue = dialogue.split("<>");
			if(s.length == 2){
				dialogue = tempDialogue[0];
				
				for(int i=1; i<s.length; i++){
					coordinatedphrase.addCoordinate(s[i]);
				}
				NLGElement elem = realiser.realise(coordinatedphrase);
				String emotions = elem.toString();
				dialogue = dialogue.replace("<emotion>", emotions);
				dialogue = dialogue +"\"";
			
			}else{
				CoordinatedPhraseElement coordinatedphrase2 = nlgFactory.createCoordinatedPhrase();
				
				for(int i=1; i<= s.length/2; i++){
					coordinatedphrase.addCoordinate(s[i]);
				}
				
				NLGElement elem = realiser.realise(coordinatedphrase);
				String emotions = elem.toString();
				tempDialogue[0] = tempDialogue[0].replace("<emotion>", emotions);
				
				for(int i=(s.length/2)+1; i< s.length; i++){
					coordinatedphrase2.addCoordinate(s[i]);
				}
				
				elem = realiser.realise(coordinatedphrase2);
				emotions = elem.toString();
				tempDialogue[1] = tempDialogue[1].replace("<emotion2>", emotions);
				
				dialogue = tempDialogue[0] + "." + tempDialogue[1];
			}
			Log.i("sa", dialogue);
		}else{
			for(int i=1; i<s.length; i++){
				coordinatedphrase.addCoordinate(s[i]);
			}
			NLGElement elem = realiser.realise(coordinatedphrase);
			String emotions = elem.toString();
			dialogue = dialogue.replace("<emotion>", emotions);
			
		
			
		}
		
		
		if(!dialogue.contains("<character>") && !dialogue.contains("<emotion>") && !dialogue.contains("<emotion2>")  ){
			paragraph.addComponent(nlgFactory.createSentence(dialogue));
		}
	}
	
	


}
