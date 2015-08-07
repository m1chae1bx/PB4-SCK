package model.storyworldmodel;

import java.util.ArrayList;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class StoryWorldNew implements Cloneable {
    private ArrayList<CharacterNew> characters;
    private ArrayList<ObjectNew> objects;
    private SettingNew setting;

    public StoryWorldNew() {
        // todo
    }

    @Override
    public StoryWorldNew clone() throws CloneNotSupportedException {
        StoryWorldNew swClone = (StoryWorldNew) super.clone();

        swClone.characters = new ArrayList<CharacterNew>();
        for (CharacterNew character : characters) {
            swClone.characters.add(character.clone());
        }
        swClone.objects = new ArrayList<ObjectNew>();
        for (ObjectNew obj : objects) {
            swClone.objects.add(obj.clone());
        }
        if (setting != null)
            swClone.setting = setting.clone();

        return swClone;
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------
    public ArrayList<ObjectNew> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<ObjectNew> objects) {
        this.objects = objects;
    }

    public ArrayList<CharacterNew> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<CharacterNew> characters) {
        this.characters = characters;
    }

    public SettingNew getSetting() {
        return setting;
    }

    public void setSetting(SettingNew setting) {
        this.setting = setting;
    }
}
