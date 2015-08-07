package model.storyworldmodel;

/**
 * Created by M. Bonon on 6/22/2015.
 */
public class ObjectNew implements Cloneable {
    private int nId;
    private int nConceptId;
    private String sName;
    private String sImagePath;

    public ObjectNew(int nId, int nConceptId, String sName, String sImagePath) {
        this.nId = nId;
        this.nConceptId = nConceptId;
        this.sName = sName;
        this.sImagePath = sImagePath;
    }

    @Override
    protected ObjectNew clone() throws CloneNotSupportedException {
        return (ObjectNew) super.clone();
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

    public int getnConceptId() {
        return nConceptId;
    }

    public void setnConceptId(int nConceptId) {
        this.nConceptId = nConceptId;
    }

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }
}
