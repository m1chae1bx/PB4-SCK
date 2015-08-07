package model.narratologicalmodel;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class ConflictGoals {
    private int nId;
    private int nGoalTrait;
    private int nConflictGoal;
    private int nCounterGoal;

    public ConflictGoals(int nId, int nGoalTrait, int nConflictGoal, int nCounterGoal) {
        this.nId = nId;
        this.nGoalTrait = nGoalTrait;
        this.nConflictGoal = nConflictGoal;
        this.nCounterGoal = nCounterGoal;
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getnGoalTrait() {
        return nGoalTrait;
    }

    public void setnGoalTrait(int nGoalTrait) {
        this.nGoalTrait = nGoalTrait;
    }

    public int getnConflictGoal() {
        return nConflictGoal;
    }

    public void setnConflictGoal(int nConflictGoal) {
        this.nConflictGoal = nConflictGoal;
    }

    public int getnCounterGoal() {
        return nCounterGoal;
    }

    public void setnCounterGoal(int nCounterGoal) {
        this.nCounterGoal = nCounterGoal;
    }
}
