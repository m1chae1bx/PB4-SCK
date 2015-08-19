package model.narratologicalmodel;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class ConflictGoals {
    private int nId;
    private int nGoalTrait;
    private int nConflictGoal;
    private int nConflictSub;
    private int nCounterGoal;
    private int nCounterSub;

    public ConflictGoals(int nId, int nGoalTrait, int nConflictGoal, int nConflictSub, int nCounterGoal,
                         int nCounterSub) {
        this.nId = nId;
        this.nConflictSub = nConflictSub;
        this.nGoalTrait = nGoalTrait;
        this.nConflictGoal = nConflictGoal;
        this.nCounterGoal = nCounterGoal;
        this.nCounterSub = nCounterSub;
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

    public int getnConflictSub() {
        return nConflictSub;
    }

    public void setnConflictSub(int nConflictSub) {
        this.nConflictSub = nConflictSub;
    }

    public int getnCounterSub() {
        return nCounterSub;
    }

    public void setnCounterSub(int nCounterSub) {
        this.nCounterSub = nCounterSub;
    }
}
