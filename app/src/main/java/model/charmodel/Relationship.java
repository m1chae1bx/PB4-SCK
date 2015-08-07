package model.charmodel;

import model.storyworldmodel.Character;

@Deprecated
public class Relationship {

	public static final String FRIEND = "friend";
	public static final String ENEMY = "enemy";
	public static final String ACQUAINTANCE = "acquaintance";
	
	public static final String BROTHER = "brother";
	public static final String SISTER = "sister";
	public static final String SON = "son";
	public static final String DAUGHTER = "daughter";
	public static final String FATHER = "father";
	public static final String MOTHER = "mother";

	private model.storyworldmodel.Character relationshipTo;
	private String relationship;
	private int score;
	
	public Relationship(Character relationshipTo, String relationship, int score) {
		this.relationshipTo = relationshipTo;
		this.relationship = relationship;
		this.score = score;
	}
	
	public Character getRelationshipTo() {
		return relationshipTo;
	}
	public String getRelationship() {
		return relationship;
	}
	public int getScore() {
		return score;
	}
	
	public void updateScore(int points) {
		score += points;
		
		if(score < 0) score = 0;
		else if (score > 10) score = 10;
		
		if(!relationship.equalsIgnoreCase(BROTHER) &&
				!relationship.equalsIgnoreCase(SISTER) &&
				!relationship.equalsIgnoreCase(SON) &&
				!relationship.equalsIgnoreCase(DAUGHTER) &&
				!relationship.equalsIgnoreCase(FATHER) &&
				!relationship.equalsIgnoreCase(MOTHER)) {
			if(score > 5) {
				relationship = FRIEND;
			}
			else if(score < 5) {
				relationship = ENEMY;
			}
			else {
				relationship = ACQUAINTANCE;
			}
		}
	}
	
	@Override
	public String toString() {
		return relationshipTo.getName() + ": " + relationship + " " + score;
	}
}
