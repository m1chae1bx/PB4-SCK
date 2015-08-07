package process.helpers;

import java.util.ArrayList;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;

/**
 * Created by M. Bonon on 7/7/2015.
 */
// from http://stackoverflow.com/questions/3522454/java-tree-data-structure
// modified version, added methods and edge
public class FabulaTree {
    private FabulaNode root;
    private boolean isInverted;

    public FabulaTree(FabulaElementNew rootData) {
        isInverted = false;
        root = new FabulaNode();
        root.setData(rootData);
        root.setChildren(new ArrayList<FabulaNode>());
        root.setChildrenLinks(new ArrayList<LinkNew>());
    }

    public FabulaTree(FabulaNode root) {
        isInverted = false;
        this.root = root;
    }

    /*
    Getters and Setters
     */

    public FabulaNode getRoot() {
        return root;
    }

    public void setRoot(FabulaNode root) {
        this.root = root;
    }

    public boolean isInverted() {
        return isInverted;
    }

    public void setIsInverted(boolean isInverted) {
        this.isInverted = isInverted;
    }
}


