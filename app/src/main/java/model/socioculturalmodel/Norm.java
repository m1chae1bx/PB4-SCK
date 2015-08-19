package model.socioculturalmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import process.exceptions.MalformedDataException;

/**
 * Created by M. Bonon on 8/12/2015.
 */
public class Norm {

    public final static int ORDER_BEFORE = 0;
    public final static int ORDER_AFTER = 1;

    private int nId;
    private int nFabElemId;
    private int nPolarity;
    private String sOrder;
    private List<String> sPreconditions;
    private HashMap<String, String> sParameters;

    public Norm(int nId, int nFabElemId, int nPolarity, String sOrder, String sPreconditions, String sParameters) throws MalformedDataException {
        List<String> sParamsTemp;
        String[] temp;

        this.nId = nId;
        this.nFabElemId = nFabElemId;
        this.nPolarity = nPolarity;
        this.sOrder = sOrder;

        if (sPreconditions == null) {
            this.sPreconditions = new ArrayList<>();
        }
        else {
            sPreconditions = sPreconditions.substring(1, sPreconditions.length() - 1);
            this.sPreconditions = Arrays.asList(sPreconditions.split(","));
        }

        if (sParameters != null) {
            this.sParameters = new HashMap<>();
            sParameters = sParameters.substring(1, sParameters.length() - 1);
            sParamsTemp = Arrays.asList(sParameters.split(","));
            for (String str : sParamsTemp) {
                temp = str.split(">");
                try {
                    this.sParameters.put(temp[0].trim(), temp[1].trim());
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    throw new MalformedDataException("Error in parsing norm parameter (" + str + ") for norm #" + (nId) + ".");
                }
            }
        } else
            this.sParameters = new HashMap<>();
    }

    /*
    Getters and Setters
     */

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getnFabElemId() {
        return nFabElemId;
    }

    public void setnFabElemId(int nFabElemId) {
        this.nFabElemId = nFabElemId;
    }

    public int getnPolarity() {
        return nPolarity;
    }

    public void setnPolarity(int nPolarity) {
        this.nPolarity = nPolarity;
    }

    public String getsOrder() {
        return sOrder;
    }

    public void setsOrder(String sOrder) {
        this.sOrder = sOrder;
    }

    public List<String> getsPreconditions() {
        return sPreconditions;
    }

    public void setsPreconditions(List<String> sPreconditions) {
        this.sPreconditions = sPreconditions;
    }

    public HashMap<String, String> getsParameters() {
        return sParameters;
    }

    public void setsParameters(HashMap<String, String> sParameters) {
        this.sParameters = sParameters;
    }
}
