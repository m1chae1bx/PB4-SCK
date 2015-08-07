package model.narratologicalmodel;

import process.helpers.FabulaNodeNew;

/**
 * Created by M. Bonon on 7/28/2015.
 */
public class AuthorGoal {
    private int nFabGoalId;
    private boolean isReached;

    public AuthorGoal(int nFabGoalId) {
        this.nFabGoalId = nFabGoalId;
        isReached = false;
    }

    public int getnFabGoalId() {
        return nFabGoalId;
    }

    public void setnFabGoalId(int nFabGoalId) {
        this.nFabGoalId = nFabGoalId;
    }

    public boolean isReached() {
        return isReached;
    }

    public void setIsReached(boolean isReached) {
        this.isReached = isReached;
    }
}
