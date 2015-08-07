package model.storyworldmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.DBQueriesNew;
import model.ontologymodel.SemanticRelation;
import process.exceptions.DataMismatchException;
import process.exceptions.MissingDataException;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class CharacterNew implements Cloneable {
    public static final int FEMALE = 0;
    public static final int MALE = 1;
    public static final int FEELING_HAPPY = 47;
    public static final int PERCEPTION_SEE = 24;

    //public static final String[] ATTRIBUTE_SET_A = {"nGender", "nPositiveTraits", "nNegativeTraits",
    //        "sStatusRelations", "nPreferences", "nLocation"};

    private int nId;
    private int nConceptId;
    private String sName;
    private String sImagePath;
    private int nGender; // 0 - female, 1 - male
    private List<Integer> nPositiveTraits;
    private List<Integer> nNegativeTraits;
    private List<String> sStatusRelations;
    private List<Integer> nPreferences;
    private List<Integer> nEmotions = new ArrayList<>();
    private Set<String> sPerception = new HashSet<>();
    private boolean isHungry;
    private boolean isThirsty;
    private boolean isTired;
    private boolean isAsleep;
    private int nFeeling;
    private int nLocation;
    private int nHolds; // assuming character can only carry one object
    private int nSocialActivity; //  todo change to a list/stack



    // double check
    // commented on 7-24
//    private Stack<FabulaElementNew> goalFabEl; // todo change to a list/stack


    // todo how about current action?
    // todo how about roles?
    // todo how about skills?


    public CharacterNew(int nId, int nConceptId, String sName, String sImagePath, int nLocation) {
        this.nId = nId;
        this.nConceptId = nConceptId;
        this.sName = sName;
        this.sImagePath = sImagePath;
        this.nLocation = nLocation;

        isHungry = false;
        isThirsty = false;
        isTired = false;
        isAsleep = false;
        nFeeling = -1;
        nHolds = -1;
        nSocialActivity = -1;
    }

    @Override
    protected CharacterNew clone() throws CloneNotSupportedException {
        CharacterNew charClone = (CharacterNew) super.clone();

        charClone.nPositiveTraits = new ArrayList<>(this.nPositiveTraits);
        charClone.nNegativeTraits = new ArrayList<>(this.nNegativeTraits);
        charClone.sStatusRelations = new ArrayList<>(this.sStatusRelations);
        charClone.nPreferences = new ArrayList<>(this.nPreferences);
        charClone.nEmotions = new ArrayList<>(this.nEmotions);
        charClone.sPerception = new HashSet<>(this.sPerception);
        // commented on 7-24
//        if (goalFabEl != null)
//            charClone.goalFabEl = goalFabEl.clone();

        return charClone;
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------

    // commented on 7-24
//    public FabulaElementNew getGoalFabEl() {
//        return goalFabEl;
//    }
//
//    public void setGoalFabEl(FabulaElementNew goalFabEl) {
//        this.goalFabEl = goalFabEl;
//    }

    public List<Integer> getnPreferences() {
        return nPreferences;
    }

    public void setnPreferences(List<Integer> nPreferences) {
        this.nPreferences = nPreferences;
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

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }

    public int getnGender() {
        return nGender;
    }

    public void setnGender(int nGender) {
        this.nGender = nGender;
    }

    public List<Integer> getnPositiveTraits() {
        return nPositiveTraits;
    }

    public void setnPositiveTraits(List<Integer> nPositiveTraits) {
        this.nPositiveTraits = nPositiveTraits;
    }

    public List<Integer> getnNegativeTraits() {
        return nNegativeTraits;
    }

    public void setnNegativeTraits(List<Integer> nNegativeTraits) {
        this.nNegativeTraits = nNegativeTraits;
    }

    public List<Integer> getnEmotions() {
        return nEmotions;
    }

    public void setnEmotions(List<Integer> nEmotions) {
        this.nEmotions = nEmotions;
    }

    public int getnFeeling() {
        return nFeeling;
    }

    public void setnFeeling(int nFeeling) {
        this.nFeeling = nFeeling;
    }

    public List<String> getsStatusRelations() {
        return sStatusRelations;
    }

    public void setsStatusRelations(List<String> sStatusRelations) {
        this.sStatusRelations = sStatusRelations;
    }

    public Set<String> getsPerception() {
        return sPerception;
    }

    public void setsPerception(Set<String> sPerception) {
        this.sPerception = sPerception;
    }

    public void addsPerception(String sPerception) {
        this.sPerception.add(sPerception);
    }

    public void removesPerception(String sPerception) {
        this.sPerception.remove(sPerception);
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setIsHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }

    public boolean isThirsty() {
        return isThirsty;
    }

    public void setIsThirsty(boolean isThirsty) {
        this.isThirsty = isThirsty;
    }

    public boolean isTired() {
        return isTired;
    }

    public void setIsTired(boolean isTired) {
        this.isTired = isTired;
    }

    public boolean isAsleep() {
        return isAsleep;
    }

    public void setIsAsleep(boolean isAsleep) {
        this.isAsleep = isAsleep;
    }

    public int getnLocation() {
        return nLocation;
    }

    public void setnLocation(int nLocation) {
        this.nLocation = nLocation;
    }

    public int getnHolds() {
        return nHolds;
    }

    public void setnHolds(int nHolds) {
        this.nHolds = nHolds;
    }

    public int getnSocialActivity() {
        return nSocialActivity;
    }

    public void setnSocialActivity(Integer nSocialActivity) {
        this.nSocialActivity = nSocialActivity;
    }

    public boolean checkCondition(String sAttribute, String sValue) {
        boolean isSatisfied;

        switch (sAttribute) {
            case "is_hungry": // assumes binary value: true or false
                isSatisfied = isHungry == Boolean.parseBoolean(sValue);
                break;
            case "is_thirsty":
                isSatisfied = isThirsty == Boolean.parseBoolean(sValue);
                break;
            case "is_tired":
                isSatisfied = isTired == Boolean.parseBoolean(sValue);
                break;
            case "is_asleep":
                isSatisfied = isAsleep == Boolean.parseBoolean(sValue);
                break;
            case "feeling":
                isSatisfied = nFeeling == Integer.parseInt(sValue);
                break;
            case "trait":
                isSatisfied = nPositiveTraits.contains(Integer.parseInt(sValue)) || nNegativeTraits.contains(Integer.parseInt(sValue));
                break;
            case "holds":
                isSatisfied = nHolds == Integer.parseInt(sValue);
                break;
            case "location":
                isSatisfied = nLocation == Integer.parseInt(sValue);
                break;
            case "has_perception":
                isSatisfied = getsPerception().contains(sValue);
                break;
            default:
                isSatisfied = false;
                break;
            // todo verify if social activity, emotions and goal should be included here
        }
        return isSatisfied;
    }

    public void learnTrait(int nTrait) throws MissingDataException, DataMismatchException {
        int nOppositeTrait;
        int polarity;

        try {
            nOppositeTrait = DBQueriesNew.getRelatedConcepts(nTrait, SemanticRelation.OPPOSITE_OF).get(0);
            if (nNegativeTraits.contains(nOppositeTrait)) {
                nNegativeTraits.remove((Number) nOppositeTrait);
                nPositiveTraits.add(nTrait);
            } else if (nPositiveTraits.contains(nOppositeTrait)) {
                nPositiveTraits.remove((Number) nOppositeTrait);
                nNegativeTraits.add(nTrait);
            } else
                throw new DataMismatchException("Data mismatch on character learning a trait.");
        } catch (IndexOutOfBoundsException e) {
            throw new MissingDataException("No opposite trait for the given trait is found in the knowledge base");
        }
    }

    public CharacterIdentifierNew getIdentifier() {
        return new CharacterIdentifierNew(nId);
    }

    @Override
    public String toString() {
        return "name: " + sName + "; ID: " + nId;
    }
}
