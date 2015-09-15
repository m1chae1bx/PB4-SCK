package model.storyplanmodel;

import model.storyworldmodel.CharacterIdentifierNew;
import process.exceptions.OperationUnavailableException;
import process.helpers.Ided;

/**
 * Created by M. Bonon on 7/14/2015.
 */
public class ParameterValueNew extends Ided implements Cloneable {

    private Object data;

    public ParameterValueNew() {
        super();
        data = null;
    }

    public ParameterValueNew(Object data) {
        super();
        this.data = data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void restore(CharacterIdentifierNew characterId) throws OperationUnavailableException {
        if (data instanceof CandidateCharacterIds) {
            ((CandidateCharacterIds) data).addCandidate(characterId);
        } else {
            throw new OperationUnavailableException("Restore operation is unavailable for the current " +
                    "data contained in this object. Use restore operation only for story characterId references.");
        }
    }

    @Override
    public ParameterValueNew clone() throws CloneNotSupportedException {
        ParameterValueNew clone = (ParameterValueNew) super.clone();
        if (data instanceof CandidateCharacterIds) {
            clone.data = ((CandidateCharacterIds)data).clone();
        }
        else if (data instanceof FabulaElementNew) {
            clone.data = ((FabulaElementNew)data).clone();
        }
        return clone;
    }

    @Override
    public String toString() {
        if (data != null)
            return data.getClass().getSimpleName() + "[" + System.identityHashCode(data) + "]: " + data.toString();
        else
            return null;
    }

//    public void resetCharacterValues() {
//        if (singleCharacterStorage != null) {
//            data = singleCharacterStorage;
//            singleCharacterStorage = null;
//        } else {
//            ((CandidateListNew<CharacterNew>) data).addAll(multipleCharacterStorage);
//            multipleCharacterStorage.clear();
//        }
//    }
//
//    public CharacterNew getSingleCharacterStorage() {
//        return singleCharacterStorage;
//    }
//
//    public void setSingleCharacterStorage(CharacterNew singleCharacterStorage) {
//        this.singleCharacterStorage = singleCharacterStorage;
//    }
//


}
