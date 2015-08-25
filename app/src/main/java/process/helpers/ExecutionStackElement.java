package process.helpers;

import model.storyplanmodel.LinkNew;

/**
 * Created by M. Bonon on 7/31/2015.
 */
public final class ExecutionStackElement {
    public FabulaNodeNew fabulaNode;
    public LinkNew link;
    public FabulaNodeNew origin;

    public ExecutionStackElement(FabulaNodeNew fabulaNode, LinkNew link, FabulaNodeNew origin) {
        this.fabulaNode = fabulaNode;
        this.link = link;
        this.origin = origin;
    }

    @Override
    public String toString() {
        return fabulaNode.toString() + ", " + (link != null ? link.toString() : "null") + ", " + (origin != null ? origin.toString() : "null") + "\n";
    }
}
