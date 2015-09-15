package model.storyworldmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M. Bonon on 7/18/2015.
 */
public class CharacterIdentifierNew implements Cloneable {
    private static List<CharacterIdentifierNew> listOfCharacterIdentifiers = new ArrayList<>();

    private int nCharacterId;
    private String sRecentParamAssignment;

    public CharacterIdentifierNew(int nId) {
        nCharacterId = nId;
        listOfCharacterIdentifiers.add(this);
    }

    public int getnCharacterId() {
        return nCharacterId;
    }

    public String getsRecentParamAssignment() {
        return sRecentParamAssignment;
    }

    public void setsRecentParamAssignment(String sRecentParamAssignment) {
        this.sRecentParamAssignment = sRecentParamAssignment;
    }

    public static List<CharacterIdentifierNew> getListOfCharacterIdentifiers() {
        return listOfCharacterIdentifiers;
    }

    public static void clearExistingCharIdentifiers() {
        listOfCharacterIdentifiers.clear();
    }

    @Override
    public CharacterIdentifierNew clone() throws CloneNotSupportedException {
        CharacterIdentifierNew clone = (CharacterIdentifierNew) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return nCharacterId + "";
    }
}
