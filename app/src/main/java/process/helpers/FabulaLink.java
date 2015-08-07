package process.helpers;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;

/**
 * Created by M. Bonon on 7/6/2015.
 */
public class FabulaLink {
    private FabulaElementNew linkedFabula;
    private LinkNew link;

    public FabulaElementNew getLinkedFabula() {
        return linkedFabula;
    }

    public void setLinkedFabula(FabulaElementNew linkedFabula) {
        this.linkedFabula = linkedFabula;
    }

    public LinkNew getLink() {
        return link;
    }

    public void setLink(LinkNew link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return link.toString();
    }
}
