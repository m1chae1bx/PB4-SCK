package process.storyrealisation;

import model.ontologymodel.Concept;

public class Referencer {
	
	public static String getPronoun(int gender){
		if(Concept.MALE == gender)
			return "he";
		if(Concept.FEMALE == gender)
			return "she";
		return "";	
	}
	

}
