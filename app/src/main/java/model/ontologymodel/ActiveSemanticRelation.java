package model.ontologymodel;

@Deprecated
public class ActiveSemanticRelation extends ISemanticRep {

	
	public static final String MAIN_AGENT = "MainAgent";
	public static final String SUPPORT_AGENT = "SupportAgent";
	public static final String PATIENT = "Patient";
	public static final String BACKGROUND = "Background";
	public static final String INSTRUMENT = "Instrument";
	
	/*
	 * isTired:agent:true
	 */
//	public static final String AGENT_IS_TIRED 		= "isTired:agent:";
//	public static final String PATIENT_IS_TIRED 	= "isTired:patient:";
	public static final String IS_TIRED				= "IsTired";
	
	/*
	 * isAsleep:agent:true
	 */
//	public static final String AGENT_IS_ASLEEP 			= "isAsleep:agent:";
//	public static final String PATIENT_IS_ASLEEP 		= "isAsleep:patient:";
	public static final String IS_ASLEEP				= "IsAsleep";
	
	/*
	 * isHungry:agent:true
	 */
//	public static final String AGENT_IS_HUNGRY 			= "isHungry:agent:";
//	public static final String PATIENT_IS_HUNGRY 		= "isHungry:patient:";
	public static final String IS_HUNGRY				= "IsHungry";
	
	/*
	 * isThirsty:agent:true
	 */
//	public static final String AGENT_IS_THIRSTY 		= "isThirsty:agent:";
//	public static final String PATIENT_IS_THIRSTY 		= "isThirsty:patient:";
	public static final String IS_THIRSTY				= "IsThirsty";
	
	/*
	 * genderOf:agent:male
	 */
//	public static final String GENDER_OF_AGENT 			= "genderOf:agent:";
//	public static final String GENDER_OF_PATIENT 		= "genderOf:patient:";
	public static final String GENDER_OF				= "GenderOf";
	
	/*
	 * traitOf:agent:brave
	 */
//	public static final String TRAIT_OF_AGENT 			= "traitOf:agent:";
//	public static final String TRAIT_OF_PATIENT 		= "traitOf:patient:";
	public static final String TRAIT_OF					= "TraitOf";
	public static final String NOT_TRAIT_OF				= "NotTraitOf";
	
	/*
	 * emotionOf:agent:happy
	 */
//	public static final String EMOTION_OF_AGENT			= "emotionOf:agent:";
//	public static final String EMOTION_OF_PATIENT 		= "emotionOf:patient:";
	public static final String EMOTION_OF				= "EmotionOf";
	public static final String NOT_EMOTION_OF			= "NotEmotionOf";
	
	
	/*
	 * locationOf:agent:bedroom
	 */
//	public static final String LOCATION_OF_AGENT 		= "location:";
//	public static final String LOCATION_OF_PATIENT 		= "location:";
	public static final String LOCATION_OF				= "LocationOf";
	public static final String NOT_LOCATION_OF			= "NotLocationOf";
	
	/*
	 * holds:agent:ball
	 */
//	public static final String AGENT_HOLDS 				= "holds:agent:";
//	public static final String PATIENT_HOLDS 			= "holds:patient:";
	public static final String HOLDS					= "Holds";
	
	/*
	 * relationship:agent:patient:friends
	 */
	public static final String RELATIONSHIP = "Relationship";
	
	/*
	 * time:morning
	 */
	public static final String TIME = "Time";
	public static final String TIME_DESCRIPTION = "TimeDescription";
	public static final String BACKGROUND_LOCATION = "Location";
	public static final String BACKGROUND_DESCRIPTION = "Description";

	public static final String[] activeSemRepList = 
		{
		
			HOLDS, IS_ASLEEP, IS_HUNGRY, IS_THIRSTY, IS_TIRED, EMOTION_OF, GENDER_OF, LOCATION_OF,
			TRAIT_OF, RELATIONSHIP, TIME, TIME_DESCRIPTION, BACKGROUND_LOCATION, BACKGROUND_DESCRIPTION,
		
//			AGENT_HOLDS, AGENT_IS_ASLEEP, AGENT_IS_HUNGRY, AGENT_IS_THIRSTY, AGENT_IS_TIRED,
//			EMOTION_OF_AGENT, EMOTION_OF_PATIENT, GENDER_OF_AGENT, GENDER_OF_PATIENT,
//			LOCATION_OF_AGENT, LOCATION_OF_PATIENT, PATIENT_HOLDS, PATIENT_IS_ASLEEP, PATIENT_IS_HUNGRY,
//			PATIENT_IS_THIRSTY, PATIENT_IS_TIRED, TRAIT_OF_AGENT, TRAIT_OF_PATIENT
		};
}
