package process.helpers;

import java.util.ArrayList;
import java.util.List;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;

/**
 * Created by M. Bonon on 7/14/2015.
 */
@Deprecated
public class FabulaNode {
    private FabulaElementNew data;
    private FabulaNode parent;
    private LinkNew parentLink;
    private List<FabulaNode> children;
    private List<LinkNew> childrenLinks;

    public FabulaNode addChild(FabulaElementNew fabEl, LinkNew link) {
        FabulaNode node = new FabulaNode();

        node.data = fabEl;
        node.children = new ArrayList<>();
        node.childrenLinks = new ArrayList<>();
        children.add(node);
        childrenLinks.add(link);

        return node;
    }

    public FabulaNode setParent(FabulaElementNew fabEl, LinkNew link) {
        FabulaNode node = new FabulaNode();

        node.data = fabEl;
        node.children = new ArrayList<>();
        node.childrenLinks = new ArrayList<>();
        parent = node;
        parentLink = link;

        return node;
    }

    public void clearChildren() {
        children.clear();
        childrenLinks.clear();
    }

        /*
        Getters and Setters
         */

    public FabulaElementNew getData() {
        return data;
    }

    public void setData(FabulaElementNew data) {
        this.data = data;
    }

    public FabulaNode getParent() {
        return parent;
    }

    public void setParent(FabulaNode parent) {
        this.parent = parent;
    }

    public void setParent(FabulaNode parent, LinkNew link) {
        this.parent = parent;
        this.parentLink = link;
    }

    public LinkNew getParentLink() {
        return parentLink;
    }

    public void setParentLink(LinkNew parentLink) {
        this.parentLink = parentLink;
    }

    public List<FabulaNode> getChildren() {
        return children;
    }

    public void setChildren(List<FabulaNode> children) {
        this.children = children;
    }

    public List<LinkNew> getChildrenLinks() {
        return childrenLinks;
    }

    public void setChildrenLinks(List<LinkNew> childrenLinks) {
        this.childrenLinks = childrenLinks;
    }

    @Override
    public String toString() {
        return "data: { " + data.toString() + "};";
    }
}