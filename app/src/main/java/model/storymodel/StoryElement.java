package model.storymodel;

// edited starting 6-20
public class StoryElement {
    private int nId;
    private int nConceptId; // added 6-20
    private int gender;
    private String sName;
    private String sImagePath;
    private int imageResource;

    // not sure if used or will be used // todo figure out if variables will be used
    private String sDescription;
    private int x;
    private int y;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

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

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "" + nId + ", " + sName + ", " + nConceptId;
    }
}