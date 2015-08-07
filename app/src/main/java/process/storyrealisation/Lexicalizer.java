package process.storyrealisation;

import model.storyplanmodel.Event;
import model.storyplanmodel.StoryPlan;

public class Lexicalizer {

	
	
	public static void lexicalize(StoryPlan storyPlan){
		
		for(Event e: storyPlan.getStoryEvents()){
			e.setAction(searchConcept(e.getAction()));
			if(e.getInstrument()!= null)
				e.getInstrument().setName(searchConcept(e.getInstrument().getName()));
			String agent ="agent";
			String patient="patient";
			for(int i =0; i< e.getPostconditions().size(); i++){
				
				//remove false postconditions
				if(e.getPostconditions().get(i).contains("false")){
					e.getPostconditions().remove(i);
					i--;
				}
				else if( e.getPostconditions().get(i).contains("Agent") || e.getPostconditions().get(i).contains("Patient")) {
					String temp = "";
					if(e.getPostconditions().get(i).contains("Agent")){
						
						if(e.getPostconditions().get(i).contains("EmotionOf")){
							String[] temp2= e.getPostconditions().get(i).split(":");
							temp = temp2[2];
							
						}
					
						else if(e.getPostconditions().get(i).contains("true")){
							String[] temp2= e.getPostconditions().get(i).split(":");
							temp = temp2[1].substring(2, temp2[1].length());
							
							
						}
						agent = agent+ ":"+temp.toLowerCase();
					
						
					}
					else if(e.getPostconditions().get(i).contains("Patient")){
						if(e.getPostconditions().get(i).contains("EmotionOf")){
							String[] temp2= e.getPostconditions().get(i).split(":");
							temp = temp2[2];
							
						}
						else if(e.getPostconditions().get(i).contains("true")){
							String[] temp2= e.getPostconditions().get(i).split(":");
							temp = temp2[1].substring(2, temp2[1].length());
							
						}
						patient = patient+ ":"+temp.toLowerCase();
					}
					
				


					e.getPostconditions().remove(i);
					i--;
				
				}
				
			
			}
			
			
			String temp = agent.replace("agent:", "character:");
			String temp1 = patient.replace("patient:", "character:");
			
			if(temp.equals(temp1)){
				e.getPostconditions().add(temp);
			}else{
				e.getPostconditions().add(agent);
				e.getPostconditions().add(patient);
			}
			
			
		
		}
		
	}
	
	public static String searchConcept(String ontology){
		String word ="";

		// commented on 7-10
		/*SQLiteDatabase db = DBFactory.sqldb;
		String sql = "SELECT " + DBField.COLUMN_CONCEPT_NAME + " FROM "+ DBField.TABLE_CONCEPT + " WHERE " + DBField.COLUMN_CONCEPT_ONTOLOGY + " = ? ORDER BY RANDOM() LIMIT 1";
		Cursor c = db.rawQuery(sql, new String[] { ontology });

		c.moveToFirst();
		
		while (!c.isAfterLast()) {
		
			word = c.getString(c.getColumnIndex(DBField.COLUMN_CONCEPT_NAME));
			
			
			c.moveToNext();
		 }
		 c.close();*/
		 
		 return word;
		 
	}
}
