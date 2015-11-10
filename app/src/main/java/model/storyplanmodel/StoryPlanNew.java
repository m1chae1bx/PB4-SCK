package model.storyplanmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import process.helpers.FabulaNodeNew;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class StoryPlanNew {

    private List<FabulaNodeNew> storyFragments;

    /* ----------- Methods ------------ */

    public StoryPlanNew() {
        storyFragments = new ArrayList<>();
    }

    public List<FabulaNodeNew> getStoryFragments() {
        return storyFragments;
    }

    public boolean add(FabulaNodeNew fabulaNode) {
        return storyFragments.add(fabulaNode);
    }

    public boolean addAll(List<FabulaNodeNew> fabulaNodes) {
        return storyFragments.addAll(fabulaNodes);
    }

    @Override
    public String toString() {
        String sTemp = "";

        for (FabulaNodeNew fabNode : storyFragments) {
            sTemp += fabNode.toString() + "\n";
        }

        return sTemp;
    }
}
