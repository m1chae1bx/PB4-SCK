package model.narratologicalmodel;

/**
 * Created by M. Bonon on 7/25/2015.
 */
public class ResolutionGoal {
    private int nId;
    private int nConflictId;
    private int nGoal;

    public ResolutionGoal(int nId, int nConflictId, int nGoal) {
        this.nId = nId;
        this.nConflictId = nConflictId;
        this.nGoal = nGoal;
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

    public int getnConflictId() {
        return nConflictId;
    }

    public void setnConflictId(int nConflictId) {
        this.nConflictId = nConflictId;
    }

    public int getnGoal() {
        return nGoal;
    }

    public void setnGoal(int nGoal) {
        this.nGoal = nGoal;
    }
}
