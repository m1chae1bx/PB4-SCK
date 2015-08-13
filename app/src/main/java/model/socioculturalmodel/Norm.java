package model.socioculturalmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> sParameters;

    public Norm(int nId, int nFabElemId, int nPolarity, String sOrder, String sPreconditions, String sParameters) {
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

        if (sParameters == null) {
            this.sParameters = new ArrayList<>();
        }
        else {
            sParameters = sParameters.substring(1, sParameters.length() - 1);
            this.sParameters = Arrays.asList(sParameters.split(","));
        }
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

    public List<String> getsParameters() {
        return sParameters;
    }

    public void setsParameters(List<String> sParameters) {
        this.sParameters = sParameters;
    }
}
