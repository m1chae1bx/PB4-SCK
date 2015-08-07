package model.storyplanmodel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import process.exceptions.MalformedDataException;

/**
 * Created by M. Bonon on 6/25/2015.
 */
public class LinkNew {
    public static final String TYPE_CAUSES = "causes";
    public static final String TYPE_SUB = "subAction";
    public static final String TYPE_MOTIV = "motivates";
    public static final String TYPE_ENABLE = "enables";
    public static final String TYPE_INTERRUPT = "interrupt";

    private int nLinkId;
    private String sType;
    private int nFb1Id;
    private int nFb2Id;
    private int nPriority;
    private HashMap<String, String> sParamDependencies;

    public LinkNew(int nLinkId, String sType, int nFb1Id, int nFb2Id, int nPriority, String sParamDependencies) throws MalformedDataException {
        List<String> sParamsTemp;
        String[] temp;
        this.nLinkId = nLinkId;
        this.sType = sType;
        this.nFb1Id = nFb1Id;
        this.nFb2Id = nFb2Id;
        this.nPriority = nPriority;

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
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

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

    public int getnPriority() {
        return nPriority;
    }

    public void setnPriority(int nPriority) {
        this.nPriority = nPriority;
    }
}
