package process.helpers;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;

/**
 * Created by M. Bonon on 7/25/2015.
 */
public class FabulaNodeNew {
    private FabulaElementNew data;
    private List<Pair<FabulaNodeNew, LinkNew>> sources;
    private List<Pair<FabulaNodeNew, LinkNew>> destinations;
    private static HashMap<Integer, FabulaNodeNew> existingFabNodes = new HashMap<>();

    private FabulaNodeNew(FabulaElementNew fabulaElement) {
        this.data = fabulaElement;
        existingFabNodes.put(fabulaElement.getnId(), this);
        sources = new ArrayList<>();
        destinations = new ArrayList<>();
    }

    public static FabulaNodeNew getFabulaNode(FabulaElementNew fabulaElement) {
        if(existingFabNodes.containsKey(fabulaElement.getnId())) {
            return existingFabNodes.get(fabulaElement.getnId());
        }
        else {
            return new FabulaNodeNew(fabulaElement);
        }
    }

    public void addSource(FabulaNodeNew fabNode, LinkNew link) {
        boolean isFound = false;
        Iterator<Pair<FabulaNodeNew, LinkNew>> sourcesIterator = sources.iterator();
        Pair<FabulaNodeNew, LinkNew> temp;

        while(sourcesIterator.hasNext() && !isFound) {
            temp = sourcesIterator.next();
            if(temp.second.getnLinkId() == link.getnLinkId())
                isFound = true;
        }
        if(!isFound)
            sources.add(new Pair<>(fabNode, link));
    }

    public void addDestination(FabulaNodeNew fabNode, LinkNew link) {
        boolean isFound = false;
        Iterator<Pair<FabulaNodeNew, LinkNew>> destinationsIterator = destinations.iterator();
        Pair<FabulaNodeNew, LinkNew> temp;

        while(destinationsIterator.hasNext() && !isFound) {
            temp = destinationsIterator.next();
            if(temp.second.getnLinkId() == link.getnLinkId())
                isFound = true;
        }
        if(!isFound)
            destinations.add(new Pair<>(fabNode, link));
    }

    public FabulaNodeNew getFabNodeInDestinations(int nFabElemId) {
        boolean isFound;
        Pair<FabulaNodeNew, LinkNew> pair;
        int i;
        FabulaNodeNew fabNodeReturn;

        fabNodeReturn = null;
        isFound = false;
        i = 0;
        while(i < destinations.size() && !isFound) {
            pair = destinations.get(i);
            if(pair.first.getData().getnId() == nFabElemId) {
                isFound = true;
                fabNodeReturn = pair.first;
            }
            i++;
        }

        return fabNodeReturn;
    }

    /* Getters and Setters */

    public FabulaElementNew getData() {
        return data;
    }

    public void setData(FabulaElementNew data) {
        this.data = data;
    }

    public List<Pair<FabulaNodeNew, LinkNew>> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Pair<FabulaNodeNew, LinkNew>> destinations) {
        this.destinations = destinations;
    }

    public List<Pair<FabulaNodeNew, LinkNew>> getSources() {
        return sources;
    }

    public void setSources(List<Pair<FabulaNodeNew, LinkNew>> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
