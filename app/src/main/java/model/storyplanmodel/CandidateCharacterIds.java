package model.storyplanmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.storyworldmodel.CharacterIdentifierNew;

/**
 * Created by M. Bonon on 7/19/2015.
 */
public class CandidateCharacterIds implements Cloneable {

    private List<CharacterIdentifierNew> candidates;

    public CandidateCharacterIds() {
        candidates = new ArrayList<>();
    }

    public void addCandidate(CharacterIdentifierNew candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(CharacterIdentifierNew candidate) {
        candidates.remove(candidate);
    }

    public Iterator iterator() {
        return candidates.iterator();
    }

    public int size() {
        return candidates.size();
    }

    public List<CharacterIdentifierNew> getCharacterIds() {
        return candidates;
    }

    public void addCandidates(List<CharacterIdentifierNew> supportingCharsId) {
        candidates.addAll(supportingCharsId);
    }

    @Override
    public CandidateCharacterIds clone() throws CloneNotSupportedException {
        List<CharacterIdentifierNew> candidatesClone;
        CandidateCharacterIds clone = (CandidateCharacterIds) super.clone();

        candidatesClone = new ArrayList<>();
        for (CharacterIdentifierNew temp : candidates) {
            candidatesClone.add(temp);
        }
        clone.candidates = candidatesClone;

        return clone;
    }

    @Override
    public String toString() {
        String sTemp = "{ ";
        for (CharacterIdentifierNew cId : candidates) {
            sTemp += cId.toString() + "; ";
        }
        return sTemp + "}";
    }
}
