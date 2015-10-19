package model.storyworldmodel;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class SettingNew implements Cloneable {
    private int nLocationId;
    private int nLocationConceptId;
    private String sLocation;
    private String sTime;
    private String sTimeDesc;
    private String sImagePath;
    private String sLocationDesc; // not sure if will be used

    public SettingNew() {
        // todo
    }

    public SettingNew(int nLocationId, String sLocation, int nLocationConceptId, String sImagePath) {
        this.nLocationId = nLocationId;
        this.sLocation = sLocation;
        this.nLocationConceptId = nLocationConceptId;
        this.sImagePath = sImagePath;
    }

    @Override
    public SettingNew clone() throws CloneNotSupportedException {
        return (SettingNew) super.clone();
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }

    public int getnLocationId() {
        return nLocationId;
    }

    public void setnLocationId(int nLocationId) {
        this.nLocationId = nLocationId;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public int getnLocationConceptId() {
        return nLocationConceptId;
    }

    public void setnLocationConceptId(int nLocationConceptId) {
        this.nLocationConceptId = nLocationConceptId;
    }

    public String getsLocationDesc() {
        return sLocationDesc;
    }

    public void setsLocationDesc(String sLocationDesc) {
        this.sLocationDesc = sLocationDesc;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getsTimeDesc() {
        return sTimeDesc;
    }

    public void setsTimeDesc(String sTimeDesc) {
        this.sTimeDesc = sTimeDesc;
    }
}
