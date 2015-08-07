package model.storyplanmodel;

import java.util.ArrayList;
import java.util.List;

import model.storyworldmodel.CharacterIdentifierNew;

/**
 * Created by M. Bonon on 7/9/2015.
 */
public class SharedGoalNew {
    private FabulaElementNew referenceGoalFabEl;
    private List<CharacterIdentifierNew> participants;

    public SharedGoalNew(FabulaElementNew goalFabulaElement) {
        referenceGoalFabEl = goalFabulaElement;
        participants = new ArrayList<>();
    }

    public FabulaElementNew getReferenceGoalFabEl() {
        return referenceGoalFabEl;
    }

    public void setReferenceGoalFabEl(FabulaElementNew referenceGoalFabEl) {
        this.referenceGoalFabEl = referenceGoalFabEl;
    }

    public List<CharacterIdentifierNew> getParticipants() {
        return participants;
    }

    public void addParticipant(CharacterIdentifierNew participant) {
        participants.add(participant);
    }
}
