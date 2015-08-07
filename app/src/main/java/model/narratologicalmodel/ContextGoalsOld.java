package model.narratologicalmodel;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.storyworldmodel.CharacterIdentifierNew;

/**
 * Created by M. Bonon on 7/25/2015.
 */
@Deprecated
public class ContextGoalsOld {
    private Pair<CharacterIdentifierNew, ContextGoals> mainGoal;
    private HashMap<ContextGoals, List<CharacterIdentifierNew>> supportingGoals;

    public ContextGoalsOld() {
        supportingGoals = new HashMap<>();
    }

    public Pair<CharacterIdentifierNew, ContextGoals> getMainGoal() {
        return mainGoal;
    }

    public void setMainGoal(CharacterIdentifierNew characterId, ContextGoals mainGoal) {
        this.mainGoal = new Pair<>(characterId, mainGoal);
    }

    public void addSupportingGoal(CharacterIdentifierNew characterId, ContextGoals supportingGoal) {
        if(supportingGoals.containsKey(supportingGoal)) {
            supportingGoals.get(supportingGoal).add(characterId);
        }
        else {
            supportingGoals.put(supportingGoal, new ArrayList<>(Collections.singletonList(characterId)));
        }
    }

    public HashMap<ContextGoals, List<CharacterIdentifierNew>> getSupportingGoals() {
        return supportingGoals;
    }
}
