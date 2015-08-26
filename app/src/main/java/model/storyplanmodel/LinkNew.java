package model.storyplanmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import process.exceptions.MalformedDataException;

/**
 * Created by M. Bonon on 6/25/2015.
 */
public class LinkNew implements Cloneable {
    public static final String TYPE_CAUSES = "causes";
    public static final String TYPE_SUB = "subAction";
    public static final String TYPE_MOTIV = "motivates";
    public static final String TYPE_ENABLE = "enables";
    public static final String TYPE_INTERRUPT = "interrupt";

    private int nLinkId;
    private String sType;
    private int nFb1Id;
    private int nSub1Id;
    private int nFb2Id;
    private int nSub2Id;
    private int nPriority;
    private HashMap<String, String> sParamDependencies;

    private List<String> sPreconditions;
    private List<String> sPostconditions;

    private boolean isLocked; // used in organizing links based on norms

    public LinkNew(int nLinkId, String sType, int nFb1Id, int nSub1Id, int nFb2Id, int nSub2Id,
                   int nPriority, String sParamDependencies, String sPreconditions, String sPostconditions)
            throws MalformedDataException {
        List<String> sParamsTemp;
        String[] temp;
        this.nLinkId = nLinkId;
        this.sType = sType;
        this.nFb1Id = nFb1Id;
        this.nSub1Id = nSub1Id;
        this.nFb2Id = nFb2Id;
        this.nSub2Id = nSub2Id;
        this.nPriority = nPriority;
        this.isLocked = false;

        if (sParamDependencies != null) {
            this.sParamDependencies = new HashMap<>();
            sParamDependencies = sParamDependencies.substring(1, sParamDependencies.length() - 1);
            sParamsTemp = Arrays.asList(sParamDependencies.split(","));
            for (String str : sParamsTemp) {
                temp = str.split(">");
                try {
                    this.sParamDependencies.put(temp[0].trim(), temp[1].trim());
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    throw new MalformedDataException("Error in parsing link parameter dependency (" + str + ") for link #" + (nLinkId) + ".");
                }
            }
        } else
            this.sParamDependencies = new HashMap<>();

        if (sPreconditions == null) {
            this.sPreconditions = new ArrayList<>();
        } else {
            sPreconditions = sPreconditions.substring(1, sPreconditions.length() - 1);
            this.sPreconditions = new ArrayList<>(Arrays.asList(sPreconditions.split(",")));
        }

        if (sPostconditions == null) {
            this.sPostconditions = new ArrayList<>();
        } else {
            sPostconditions = sPostconditions.substring(1, sPostconditions.length() - 1);
            this.sPostconditions = new ArrayList<>(Arrays.asList(sPostconditions.split(",")));
        }
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    public List<String> getsPreconditions() {
        return sPreconditions;
    }

    public void setsPreconditions(List<String> sPreconditions) {
        this.sPreconditions = sPreconditions;
    }

    public int getnLinkId() {
        return nLinkId;
    }

    public void setnLinkId(int nLinkId) {
        this.nLinkId = nLinkId;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public int getnFb1Id() {
        return nFb1Id;
    }

    public void setnFb1Id(int nFb1Id) {
        this.nFb1Id = nFb1Id;
    }

    public int getnFb2Id() {
        return nFb2Id;
    }

    public void setnFb2Id(int nFb2Id) {
        this.nFb2Id = nFb2Id;
    }

    public HashMap<String, String> getsParamDependencies() {
        return sParamDependencies;
    }

    public void setsParamDependencies(HashMap<String, String> sParamDependencies) {
        this.sParamDependencies = sParamDependencies;
    }

    @Override
    public String toString() {
        return "type: " + sType + "; fb1_ID: " + nFb1Id + "; fb2_ID: " + nFb2Id;
    }

    @Override
    public LinkNew clone() throws CloneNotSupportedException {
        LinkNew newLink = (LinkNew) super.clone();
        HashMap<String, String> sParamDependenciesClone = new HashMap<>();
        List<String> sPreconditionsClone = new ArrayList<>();
        List<String> sPostconditionsClone = new ArrayList<>();

        newLink.nLinkId = nLinkId;
        newLink.sType = sType;
        newLink.nFb1Id = nFb1Id;
        newLink.nFb2Id = nFb2Id;
        newLink.nPriority = nPriority;

        for (Map.Entry<String, String> pair : sParamDependencies.entrySet()) {
            sParamDependenciesClone.put(pair.getKey(), pair.getValue());
        }
        newLink.sParamDependencies = sParamDependenciesClone;

        sPreconditionsClone.addAll(sPreconditions);
        newLink.sPreconditions = sPreconditionsClone;
        sPostconditionsClone.addAll(sPostconditions);
        newLink.sPostconditions = sPostconditionsClone;

        return newLink;
    }

    public int getnPriority() {
        return nPriority;
    }

    public void setnPriority(int nPriority) {
        this.nPriority = nPriority;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public int getnSub1Id() {
        return nSub1Id;
    }

    public void setnSub1Id(int nSub1Id) {
        this.nSub1Id = nSub1Id;
    }

    public int getnSub2Id() {
        return nSub2Id;
    }

    public void setnSub2Id(int nSub2Id) {
        this.nSub2Id = nSub2Id;
    }

    public List<String> getsPostconditions() {
        return sPostconditions;
    }

    public void setsPostconditions(List<String> sPostconditions) {
        this.sPostconditions = sPostconditions;
    }
}
