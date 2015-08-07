package model.ontologymodel;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class ConceptNew {
    private int nId;
    private String sName;

    public ConceptNew(int nId, String sName) {
        this.nId = nId;
        this.sName = sName;
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

}
