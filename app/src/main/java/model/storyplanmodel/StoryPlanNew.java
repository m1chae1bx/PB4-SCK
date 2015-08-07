package model.storyplanmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import process.helpers.FabulaNodeNew;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class StoryPlanNew {

//    private List<FabulaElementNew> exposition;
//    private List<FabulaElementNew> initiatingIncident;
//    private List<FabulaElementNew> risingAction;
//    private List<FabulaElementNew> climax;
//    private List<FabulaElementNew> resolution; // falling action and resolution have been merged
//    private List<FabulaElementNew> marker;

    private List<FabulaNodeNew> storyFragments;

    public StoryPlanNew() {
        storyFragments = new ArrayList<>();
    }

    public boolean add(FabulaNodeNew fabulaNode) {
        return storyFragments.add(fabulaNode);
    }

    public boolean addAll(List<FabulaNodeNew> fabulaNodes) {
        return storyFragments.addAll(fabulaNodes);
    }

    // todo How about these?
    //private GoalTraitNew goalTrait;
    //private ConflictGoals desireAndDifficulty;
    //resolution

//    public void addFabula(FabulaElementNew fabula) {
//        marker.add(fabula);
//    }
//
//    public boolean moveMarker() {
//        boolean isSuccess = true;
//        if (marker == exposition)
//            marker = initiatingIncident;
//        else if (marker == initiatingIncident)
//            marker = risingAction;
//        else if (marker == risingAction)
//            marker = climax;
//        else if (marker == climax)
//            marker = resolution;
//        else
//            isSuccess = false;
//
//        return isSuccess;
//    }
//
//    // ------------------------------
//    // Getters and Setters
//    // ------------------------------
//
//    public List<FabulaElementNew> getExposition() {
//        return exposition;
//    }
//
//    public void setExposition(List<FabulaElementNew> exposition) {
//        this.exposition = exposition;
//    }
//
//    public List<FabulaElementNew> getInitiatingIncident() {
//        return initiatingIncident;
//    }
//
//    public void setInitiatingIncident(List<FabulaElementNew> initiatingIncident) {
//        this.initiatingIncident = initiatingIncident;
//    }
//
//    public List<FabulaElementNew> getRisingAction() {
//        return risingAction;
//    }
//
//    public void setRisingAction(List<FabulaElementNew> risingAction) {
//        this.risingAction = risingAction;
//    }
//
//    public List<FabulaElementNew> getClimax() {
//        return climax;
//    }
//
//    public void setClimax(List<FabulaElementNew> climax) {
//        this.climax = climax;
//    }
//
//    public List<FabulaElementNew> getResolution() {
//        return resolution;
//    }
//
//    public void setResolution(List<FabulaElementNew> resolution) {
//        this.resolution = resolution;
//    }
//
//    public List<FabulaElementNew> getMarker() {
//        return marker;
//    }
//
//    public void setMarker(List<FabulaElementNew> marker) {
//        this.marker = marker;
//    }
}
