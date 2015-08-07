package model.ontologymodel;

@Deprecated
public abstract class ISemanticRep {

	/**
	 * 
	 * @param condition insert description here
	 * @return true if condition is Passive Semantic Relation, 
	 * 			false if Active Semantic Relation
	 */
	public static boolean check(String condition) {
		
		for(int i = 0; i < ActiveSemanticRelation.activeSemRepList.length; i++) {
			if(condition.contains(ActiveSemanticRelation.activeSemRepList[i])) {
				return false;
			}
		}
		return true;
	}
}
