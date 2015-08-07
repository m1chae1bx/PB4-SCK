package model.storyworldmodel;

import java.util.ArrayList;

@Deprecated
public class StoryWorldOld implements Cloneable {

	private ArrayList<Character> characters;
	private ArrayList<Object> objects;
	private Background background;
	
	// CONSTRUCTORS
	public StoryWorldOld() {
		
	}
	
	// CHARACTER RELATED METHODS
	public ArrayList<Character> getCharacters() {
		return characters;
	}
	public void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}
	public void addCharacter(Character character) {
		if(characters == null) 
			characters = new ArrayList();
		characters.add(character);
	}
	public Character getMainCharacter() {
		return characters.get(0);
	}
	public ArrayList<Character> getSupportingCharacters() {
//		ArrayList<Character> supportCharactertList = new ArrayList<Character>();
//		for(int i = 1; i < characters.size(); i++) {
//			supportCharactertList.add(characters.get(i));
//		}
//		return supportCharactertList;
		return new ArrayList(characters.subList(1, characters.size()));
	}
	public ArrayList<Character> getPossibleSupporters(Character main) {
		ArrayList<Character> supportCharactertList = new ArrayList();
		for(int i = 0; i < characters.size(); i++) {
			if(main.getID() != characters.get(i).getID()) {
				supportCharactertList.add(characters.get(i));
			}
		}
		return supportCharactertList;
	}
	public Character getCharacterByID(int charID) {
		for(Character character : characters) {
			if(character.getID() == charID) {
				return character;
			}
		}
		return null;
	}
	public Character getCharacterByOrder(int order) {
		return characters.get(order);
	}
	
	// OBJECT RELATED METHODS
	public ArrayList<Object> getObjects() {
		return objects;
	}
	public void setObjects(ArrayList<Object> objects) {
		this.objects = objects;
	}
	public void addObject(Object object) {
		if(objects == null)
			objects = new ArrayList();
		objects.add(object);
	}
		
	// BACKGROUND RELATED METHODS
	public Background getBackground() {
		return background;
	}
	public void setBackground(Background background) {
		this.background = background;
	}
	public String getTime() {
		return background.getTime();
	}
	
	@Override
	public String toString() {
		String str = "@@@@@@@@@@@@@@@@@@@ STORY WORLD @@@@@@@@@@@@@@@@@@@\n";
		

		str += background.getTimeDesc() + " " + background.getTime() + "\n";
		str += background.getBGDesc() + " " + background.getName() + "\n";
		
		
		str += "*** CHARACTERS ***\n";
		
		for(int i = 0; i < characters.size(); i++){
			str += characters.get(i).toString() + "\n"; 
			str += "------------------------\n";
		}
		
		str += "*** OBJECTS ***\n";
		
		for(Object o : objects) {
			str += o.toString() + "\n";
		}
		
		str += "*** BACROUND ***\n";
		str += background.toString() + "\n";
//		
		str += "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n";
		return str;
	}	
	@Override
	public StoryWorldOld clone() {
		try {
			return (StoryWorldOld) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
