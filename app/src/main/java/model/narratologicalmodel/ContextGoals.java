package model.narratologicalmodel;

import process.helpers.FabulaNodeNew;

/**
 * Created by M. Bonon on 7/5/2015.
 */
public class ContextGoals {
    private int nId;
    private int nConflict;
    private int nMainGoal;
    private int nMainGoalSubId;
    private int nSupportGoal;
    private int nSupportGoalSubId;
    private int nSearchDirection;
    private FabulaNodeNew mainGoalNode;
    private FabulaNodeNew supportingGoalNode;

    public ContextGoals(int nId, int nConflict, int nMainGoal, int nMainGoalSubId, int nSupportGoal, int nSupportGoalSubId, int nSearchDirection) {
        this.nId = nId;
        this.nMainGoal = nMainGoal;
        this.nConflict = nConflict;
        this.nMainGoalSubId = nMainGoalSubId;
        this.nSupportGoal = nSupportGoal;
        this.nSupportGoalSubId = nSupportGoalSubId;
        this.nSearchDirection = nSearchDirection; // assumption: 0 means head first then support, 1 means support first then head
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    public int getnConflict() {
        return nConflict;
    }

    public void setnConflict(int nConflict) {
        this.nConflict = nConflict;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getnMainGoal() {
        return nMainGoal;
    }

    public void setnMainGoal(int nMainGoal) {
        this.nMainGoal = nMainGoal;
    }

    public int getnSearchDirection() {
        return nSearchDirection;
    }

    public void setnSearchDirection(int nSearchDirection) {
        this.nSearchDirection = nSearchDirection;
    }

    public FabulaNodeNew getMainGoalNode() {
        return mainGoalNode;
    }

    public void setMainGoalNode(FabulaNodeNew mainGoalNode) {
        this.mainGoalNode = mainGoalNode;
    }

    public int getnSupportingGoal() {
        return nSupportGoal;
    }

    public void setnSupportGoal(int nSupportGoal) {
        this.nSupportGoal = nSupportGoal;
    }

    public FabulaNodeNew getSupportingGoalNode() {
        return supportingGoalNode;
    }

    public void setSupportingGoalNode(FabulaNodeNew supportingGoalNode) {
        this.supportingGoalNode = supportingGoalNode;
    }

    public int getnMainGoalSubId() {
        return nMainGoalSubId;
    }

    public void setnMainGoalSubId(int nMainGoalSubId) {
        this.nMainGoalSubId = nMainGoalSubId;
    }

    public int getnSupportGoalSubId() {
        return nSupportGoalSubId;
    }

    public void setnSupportGoalSubId(int nSupportGoalSubId) {
        this.nSupportGoalSubId = nSupportGoalSubId;
    }
}
