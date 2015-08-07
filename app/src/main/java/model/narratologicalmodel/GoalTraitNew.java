package model.narratologicalmodel;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class GoalTraitNew {
    private int nId;

    private int nTrait;
    private int nRBg;
    private int nRObj;

    public GoalTraitNew(int nId, int nTrait, int nRBg, int nRObj) {
        this.nId = nId;
        this.nTrait = nTrait;
        this.nRBg = nRBg;
        this.nRObj = nRObj;
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

    public int getnTrait() {
        return nTrait;
    }

    public void setnTrait(int nTrait) {
        this.nTrait = nTrait;
    }

    public int getnRBg() {
        return nRBg;
    }

    public void setnRBg(int nRBg) {
        this.nRBg = nRBg;
    }

    public int getnRObj() {
        return nRObj;
    }

    public void setnRObj(int nRObj) {
        this.nRObj = nRObj;
    }
}
