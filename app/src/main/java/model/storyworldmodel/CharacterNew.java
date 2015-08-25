package model.storyworldmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DBQueriesNew;
import model.ontologymodel.SemanticRelation;
import model.storyplanmodel.CandidateCharacterIds;
import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.ParameterValueNew;
import process.exceptions.DataMismatchException;
import process.exceptions.MalformedDataException;
import process.exceptions.MissingDataException;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class CharacterNew implements Cloneable {
    public static final int FEMALE = 0;
    public static final int MALE = 1;
    public static final int FEELING_HAPPY = 47;
    public static final int PERCEPTION_SEE = 24;
    public static final int TRAIT_UNTIDY = 28;
    public static final int TRAIT_CLEAN = 6;


    //public static final String[] ATTRIBUTE_SET_A = {"nGender", "nPositiveTraits", "nNegativeTraits",
    //        "sStatusRelations", "nPreferences", "nLocation"};

    private int nId;
    private int nConceptId;
    private int nGender; // 0 - female, 1 - male
    private int nFeeling;
    private int nLocation;
    private int nHolds; // assuming character can only carry one object
    private int nCurrentGoal;
    private boolean isHungry;
    private boolean isThirsty;
    private boolean isTired;
    private boolean isAsleep;
    private boolean isSocialOpportunity;
    private String sName;
    private String sImagePath;
    private String sMiscellaneousState;
    private List<Integer> nPositiveTraits;
    private List<Integer> nNegativeTraits;
    private List<Integer> nPreferences;
    private List<Integer> nEmotions;
    private List<Integer> nCurrentGoalHistory;
    private List<String> sStatusRelations;
    private Set<String> sPerception;
    private Set<String> sBeliefs;

//    commented on 8-14
//    private int nSocialActivity; //  todo change to a list/stack

//    commented on 7-24
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
        isSocialOpportunity = false;
        nFeeling = -1;
        nHolds = -1;
        nCurrentGoal = -1;
        sMiscellaneousState = null;
        //nSocialActivity = -1;

        nCurrentGoalHistory = new ArrayList<>();
        nEmotions = new ArrayList<>();
        sPerception = new HashSet<>();
        sBeliefs = new HashSet<>();
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
        charClone.nCurrentGoalHistory = new ArrayList<>(this.nCurrentGoalHistory);
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

    public void addsBelief(String sBelief) {
        this.sBeliefs.add(sBelief);
    }

    public void removesBelief(String sBelief) {
        this.sBeliefs.remove(sBelief);
    }

    public void addnCurrentGoalHistory(int nCurrentGoal) {
        this.nCurrentGoalHistory.add(nCurrentGoal);
    }

    public void removenCurrentGoalHistory(int nCurrentGoal) {
        this.nCurrentGoalHistory.remove(nCurrentGoal);
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
            case "social_opportunity":
                isSatisfied = isSocialOpportunity == Boolean.parseBoolean(sValue);
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
            case "current_goal":
                isSatisfied = nCurrentGoal == Integer.parseInt(sValue);
                break;
            case "has_current_goal_history":
                isSatisfied = nCurrentGoalHistory.contains(Integer.parseInt(sValue));
                break;
            case "not_has_current_goal_history":
                isSatisfied = !nCurrentGoalHistory.contains(Integer.parseInt(sValue));
                break;
            case "has_perception":
                isSatisfied = sPerception.contains(sValue);
                break;
            case "not_has_perception":
                isSatisfied = !sPerception.contains(sValue);
                break;
            case "has_belief":
                isSatisfied = sBeliefs.contains(sValue);
                break;
            case "not_has_belief":
                isSatisfied = !sBeliefs.contains(sValue);
                break;
            case "miscellaneous_state":
                if (sMiscellaneousState != null)
                    isSatisfied = sMiscellaneousState.equals(sValue);
                else
                    isSatisfied = false;
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

    public Set<String> getsBeliefs() {
        return sBeliefs;
    }

    public void setsBeliefs(Set<String> sBeliefs) {
        this.sBeliefs = sBeliefs;
    }

    public List<Integer> getnCurrentGoalHistory() {
        return nCurrentGoalHistory;
    }

    public void setnCurrentGoalHistory(List<Integer> nCurrentGoalHistory) {
        this.nCurrentGoalHistory = nCurrentGoalHistory;
    }

    public int getnCurrentGoal() {
        return nCurrentGoal;
    }

    public void setnCurrentGoal(int nCurrentGoal) {
        this.nCurrentGoal = nCurrentGoal;
    }

    public String getsMiscellaneousState() {
        return sMiscellaneousState;
    }

    public void setsMiscellaneousState(String sMiscellaneousState) {
        this.sMiscellaneousState = sMiscellaneousState;
    }

    public boolean isSocialOpportunity() {
        return isSocialOpportunity;
    }

    public void setIsSocialOpportunity(boolean isSocialOpportunity) {
        this.isSocialOpportunity = isSocialOpportunity;
    }

    public void realizeCondition(String sAttribute, String sValue, String sCondition,
                                 HashMap<String, ParameterValueNew> sParamValues)
            throws MissingDataException, DataMismatchException, MalformedDataException {
        ParameterValueNew paramValue;
        Matcher matcher;
        Pattern pattern;
        String sTemp;
        String sRegEx;
        int nLastStop;
        Iterator<Integer> nCharIdIterator;
        List<Integer> nCharacterIdList;

        switch (sAttribute) {
            case "is_hungry":
                setIsHungry(Boolean.parseBoolean(sValue));
                break;
            case "is_thirsty":
                setIsThirsty(Boolean.parseBoolean(sValue));
                break;
            case "is_tired":
                setIsTired(Boolean.parseBoolean(sValue));
                break;
            case "is_asleep":
                setIsAsleep(Boolean.parseBoolean(sValue));
                break;
            case "social_opportunity":
                setIsSocialOpportunity(Boolean.parseBoolean(sValue));
                break;
            case "feeling":
                setnFeeling(Integer.parseInt(sValue));
                break;
            case "trait":
                learnTrait(Integer.parseInt(sValue));
                break;
            case "holds":
                setnHolds(Integer.parseInt(sValue));
                break;
            case "location":
                setnLocation(Integer.parseInt(sValue));
                break;
            case "current_goal":
                setnCurrentGoal(Integer.parseInt(sValue));
                break;
            case "has_current_goal_history":
                addnCurrentGoalHistory(Integer.parseInt(sValue));
            case "not_has_current_goal_history":
                removenCurrentGoalHistory(Integer.parseInt(sValue));
                break;
            case "miscellaneous_state":
                setsMiscellaneousState(sValue);
                break;
            case "has_perception":
            case "not_has_perception":
            case "has_belief":
            case "not_has_belief":
                if (sAttribute.equals("has_perception") || sAttribute.equals("not_has_perception"))
                    sRegEx = "[0-9]+=(#?[a-z_]+)";
                else
                    sRegEx = "[a-z_]+=(#?[a-z_]+)";

                if (sValue.matches(sRegEx)) {
                    sTemp = "";
                    nLastStop = 0;
                    pattern = Pattern.compile("#[a-z_]+");
                    matcher = pattern.matcher(sValue);
                    while (matcher.find()) {
                        paramValue = sParamValues.get(sValue.substring(matcher.start() + 1, matcher.end()));
                        sTemp += sValue.substring(nLastStop, matcher.start());
                        if (paramValue != null) {
                            if (paramValue.getData() == null)
                                sTemp += "null";
                            else if (paramValue.getData() instanceof CandidateCharacterIds){ // todo what if there are many characters inside?
                                nCharacterIdList = new ArrayList<>();
                                for (CharacterIdentifierNew tempCharId : ((CandidateCharacterIds) paramValue.getData()).getCharacterIds()) {
                                    nCharacterIdList.add(tempCharId.getnCharacterId());
                                }
                                Collections.sort(nCharacterIdList);
                                nCharIdIterator = nCharacterIdList.iterator();
                                while (nCharIdIterator.hasNext()) {
                                    sTemp += "c" + nCharIdIterator.next();
                                    if (nCharIdIterator.hasNext())
                                        sTemp += ":";
                                }
                            }
                            else if (paramValue.getData() instanceof FabulaElementNew) {
                                sTemp += "f" + ((FabulaElementNew) paramValue.getData()).getnId();
                            }
                            else {
                                sTemp += paramValue.getData().toString();
                            }
                        } else {
                            throw new DataMismatchException("Invalid parameter encountered in a fabula" +
                                    " element precondition: " + sCondition + ".");
                        }
                        nLastStop = matcher.end();
                    }
                    sTemp += sValue.substring(nLastStop, sValue.length());
                    switch (sAttribute) {
                        case "has_perception":
                            addsPerception(sTemp);
                            break;
                        case "not_has_perception":
                            removesPerception(sTemp);
                            break;
                        case "has_belief":
                            addsBelief(sTemp);
                            break;
                        case "not_has_belief":
                            removesBelief(sTemp);
                            break;
                    }
                } else {
                    throw new MalformedDataException("Unable to parse malformed attribute value in " +
                            "precondition: " + sCondition + ".");
                }
                break;
            // todo verify if social activity, emotions and goal should be included here
        }
    }
}
