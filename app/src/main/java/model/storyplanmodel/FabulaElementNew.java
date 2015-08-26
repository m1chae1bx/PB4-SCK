package model.storyplanmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.storyworldmodel.CharacterIdentifierNew;

/**
 * Created by M. Bonon on 6/24/2015.
 */
public class FabulaElementNew implements Cloneable {
    public static final String CATEGORY_GOAL = "goal";
    public static final String CATEGORY_ACTION = "action";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_PERCEPT = "perception";
    public static final String CATEGORY_INTERN = "internal element";
    public static final String CATEGORY_OUTCOME = "outcome";
    public static final String PARAMS_AGENT = "agent";
    public static final String PARAMS_PATIENT = "patient";
    public static final String PARAMS_TARGET = "target";
    public static final String PARAMS_HIDDEN = "hidden";
    public static final String PARAMS_DIRECTION = "direction";

    private int nId;
    private int nConceptId;
    private int nSubId;
    private String sLabel;
    private String sCategory;
    private List<String> sRequiredParams;
    private List<String> sPreconditions; // todo change to hashmap
    private List<String> sPostconditions; // todo change to hashmap
    // todo how about recommendations?
    private HashMap<String, ParameterValueNew> paramValues;

    private List<Object> executionAgents;

    private boolean isNegated; // performed or not
    //private boolean isInternal; // within another FB
    //private boolean isInfinitive; // basic form of verb
    //private boolean isIndependent; // requires another fabula to execute first
    // todo how about adding something that will indicate time, and other sentence modifiers

    private boolean isBeginning; // todo remove if unnecessary
    private FabulaElementNew subExecutionElement;

    public FabulaElementNew(int nId, String sLabel, int nConceptId, int nSubId, String sCategory, String sRequiredParams,
                            String sPreconditions, String sPostconditions, int isNegated) {

        this.nId = nId;
        this.sLabel = sLabel;
        this.nConceptId = nConceptId;
        this.nSubId = nSubId;
        this.sCategory = sCategory;
        this.paramValues = new HashMap<>();

        if (sRequiredParams == null) {
            this.sRequiredParams = new ArrayList<>();
        } else {
            sRequiredParams = sRequiredParams.substring(1, sRequiredParams.length() - 1);
            this.sRequiredParams = new ArrayList<>(Arrays.asList(sRequiredParams.split(",")));
        }

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

        this.isNegated = isNegated == 1;
        isBeginning = false;
        executionAgents = null;
        subExecutionElement = null;
    }

    public void setupExecutionAgents() {
        Object tempObj = paramValues.get(PARAMS_AGENT).getData();
        executionAgents = new ArrayList<>();
        if (tempObj instanceof CandidateCharacterIds) {
            executionAgents.addAll(((CandidateCharacterIds) tempObj).getCharacterIds());
        } else if (tempObj instanceof List) {
            executionAgents.addAll((List)tempObj);
        } else {
            executionAgents.add(tempObj);
        }
    }

    @Override
    public FabulaElementNew clone() throws CloneNotSupportedException {
        FabulaElementNew fabElClone = (FabulaElementNew) super.clone();
        fabElClone.sRequiredParams = new ArrayList<>(this.sRequiredParams);
        fabElClone.sPreconditions = new ArrayList<>(this.sPreconditions);
        fabElClone.sPostconditions = new ArrayList<>(this.sPostconditions);
        fabElClone.paramValues = new HashMap<>(this.paramValues);
        if (this.executionAgents != null)
            fabElClone.executionAgents = new ArrayList<>(this.executionAgents);
        if (this.subExecutionElement != null)
            fabElClone.subExecutionElement = this.subExecutionElement.clone();
        return fabElClone;
    }

    @Override
    public String toString() {
        String sTemp;
        sTemp = "label: " + sLabel + "; category: " + sCategory + "; ID: " + nId;
        return sTemp;
    }

    public boolean checkCondition(String sAttribute, String sValue, HashMap<String, ParameterValueNew> sParamValues) {
        Boolean isTrue = false, isComplete;
        ParameterValueNew thisParamValue, anotherParamValue;

        switch (sAttribute) {
            case "has_agent_character":
                thisParamValue = paramValues.get(PARAMS_AGENT);
                if (thisParamValue != null && thisParamValue.getData() instanceof CandidateCharacterIds) {
                    if (sValue.matches("#?[a-z_]+]")) {
                        anotherParamValue = sParamValues.get(sValue.substring(1));
                        if (anotherParamValue.getData() instanceof CandidateCharacterIds) {
                            isComplete = true;
                            for (CharacterIdentifierNew charId : ((CandidateCharacterIds) anotherParamValue.getData()).getCharacterIds()) {
                                isComplete = isComplete && ((CandidateCharacterIds) thisParamValue.getData()).getCharacterIds().contains(charId);
                            }
                            isTrue = isComplete;
                        }
                    }
                }
                break;
        }

        return isTrue;
    }

    protected void rippleHasAgentCharacter(ParameterValueNew tempParamValue) {
        ParameterValueNew agentParamValue;

        agentParamValue = paramValues.get(PARAMS_AGENT);
        if (tempParamValue != null && agentParamValue != null) {
            if (tempParamValue.getData() instanceof CandidateCharacterIds
                    && agentParamValue.getData() instanceof CandidateCharacterIds) {
                ((CandidateCharacterIds) agentParamValue.getData())
                        .addCandidates(((CandidateCharacterIds) tempParamValue.getData())
                                .getCharacterIds());
            }
            else {
                // todo complete
            }
            if (subExecutionElement != null)
                subExecutionElement.rippleHasAgentCharacter(tempParamValue);
        }
    }

    public void realizeCondition(String sAttribute, String sValue, String sCondition,
                                 HashMap<String, ParameterValueNew> sParamValues) {
        ParameterValueNew tempParamValue, agentParamValue;

        switch (sAttribute) {
            case "has_agent_character":
                if (sValue.matches("#[a-z_]+")) {
                    tempParamValue = sParamValues.get(sValue.substring(1));
                    agentParamValue = paramValues.get(PARAMS_AGENT);
                    if (tempParamValue != null && agentParamValue != null) {
                        if (tempParamValue.getData() instanceof CandidateCharacterIds
                                && agentParamValue.getData() instanceof CandidateCharacterIds) {
                            ((CandidateCharacterIds) agentParamValue.getData())
                                    .addCandidates(((CandidateCharacterIds) tempParamValue.getData())
                                            .getCharacterIds());
                        }
                        else {
                            // todo complete
                        }
                        if (subExecutionElement != null)
                            subExecutionElement.rippleHasAgentCharacter(tempParamValue);
                    }
                }
                else {
                    // todo complete
                }
                break;
            default:
                // todo what should be done here?
                break;
        }
    }

    // ------------------------------
    // Getters and Setters
    // ------------------------------


    public List<Object> getExecutionAgents() {
        return executionAgents;
    }

    public void setExecutionAgents(List<Object> executionAgents) {
        this.executionAgents = executionAgents;
    }

    public HashMap<String, ParameterValueNew> getParamValues() {
        return paramValues;
    }

    public void setParamValues(HashMap<String, ParameterValueNew> paramValues) {
        this.paramValues = paramValues;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getsLabel() {
        return sLabel;
    }

    public void setsLabel(String sLabel) {
        this.sLabel = sLabel;
    }

    public String getsCategory() {
        return sCategory;
    }

    public void setsCategory(String sCategory) {
        this.sCategory = sCategory;
    }

    public int getnConceptId() {
        return nConceptId;
    }

    public void setnConceptId(int nConceptId) {
        this.nConceptId = nConceptId;
    }

    public List<String> getsRequiredParams() {
        return sRequiredParams;
    }

    public void setsRequiredParams(List<String> sRequiredParams) {
        this.sRequiredParams = sRequiredParams;
    }

    public List<String> getsPreconditions() {
        return sPreconditions;
    }

    public void setsPreconditions(List<String> sPreconditions) {
        this.sPreconditions = sPreconditions;
    }

    public List<String> getsPostconditions() {
        return sPostconditions;
    }

    public void setsPostconditions(List<String> sPostconditions) {
        this.sPostconditions = sPostconditions;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public void setIsNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    public boolean isBeginning() {
        return isBeginning;
    }

    public void setIsBeginning(boolean isBeginning) {
        this.isBeginning = isBeginning;
    }

    public int getnSubId() {
        return nSubId;
    }

    public void setnSubId(int nSubId) {
        this.nSubId = nSubId;
    }

    public FabulaElementNew getSubExecutionElement() {
        return subExecutionElement;
    }

    public void setSubExecutionElement(FabulaElementNew subExecutionElement) {
        this.subExecutionElement = subExecutionElement;
    }

//    public boolean isInternal() {
//        return isInternal;
//    }
//
//    public void setIsInternal(boolean isInternal) {
//        this.isInternal = isInternal;
//    }
//
//    public boolean isInfinitive() {
//        return isInfinitive;
//    }
//
//    public void setIsInfinitive(boolean isInfinitive) {
//        this.isInfinitive = isInfinitive;
//    }
//
//    public boolean isIndependent() {
//        return isIndependent;
//    }
//
//    public void setIsIndependent(boolean isIndependent) {
//        this.isIndependent = isIndependent;
//    }
}
