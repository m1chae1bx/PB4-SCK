package model.narratologicalmodel;

import process.helpers.FabulaNodeNew;

/**
 * Created by M. Bonon on 7/28/2015.
 */
public class AuthorGoal {
    private int nFabGoalId;
    private int nSubId;
    private boolean isReached;

    public AuthorGoal(int nFabGoalId, int nSubId) {
        this.nFabGoalId = nFabGoalId;
        this.nSubId = nSubId;
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

    public int getnSubId() {
        return nSubId;
    }

    public void setnSubId(int nSubId) {
        this.nSubId = nSubId;
    }
}
