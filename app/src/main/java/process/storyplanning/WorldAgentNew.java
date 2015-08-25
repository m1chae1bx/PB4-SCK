package process.storyplanning;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.CandidateCharacterIds;
import model.storyplanmodel.ParameterValueNew;
import model.storyworldmodel.CharacterIdentifierNew;
import model.storyworldmodel.CharacterNew;
import model.storyworldmodel.ObjectNew;
import model.storyworldmodel.SettingNew;
import model.storyworldmodel.StoryWorldNew;
import process.exceptions.DataMismatchException;
import process.exceptions.MalformedDataException;
import process.exceptions.MissingDataException;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class WorldAgentNew implements Cloneable {
    private StoryWorldNew storyWorld;
    private WorldAgentNew restorePoint;

    public void setRestorePoint() {
        // todo
    }

    public void restore() {
        // todo
    }

    public WorldAgentNew(StoryWorldNew storyWorld) {
        this.storyWorld = storyWorld;
    }

    public CharacterNew getMainCharacter() {
        return storyWorld.getCharacters().get(0);
    }

    public SettingNew getSetting() {
        return storyWorld.getSetting();
    }

    public List<Integer> getObjectConceptIds() {
        List<Integer> nConceptIds = new ArrayList<>();
        for (ObjectNew o : storyWorld.getObjects()) {
            nConceptIds.add(o.getnConceptId());
        }
        return nConceptIds;
    }

    public List<CharacterNew> getCharacters() {
        return storyWorld.getCharacters();
    }

    public List<CharacterIdentifierNew> getCharacterIds() {
        List<CharacterIdentifierNew> charIds = new ArrayList<>();
        for(CharacterNew character : storyWorld.getCharacters()) {
            charIds.add(new CharacterIdentifierNew(character.getnId()));
        }
        return charIds;
    }

    private CharacterNew getCharacterById(int nInstanceId) {
        CharacterNew charTemp = null;
        for (CharacterNew character : storyWorld.getCharacters()) {
            if (character.getnId() == nInstanceId) {
                charTemp = character;
                break;
            }
        }
        return charTemp;
    }

    public void update(WorldAgentNew worldAgent) {
        this.storyWorld = worldAgent.storyWorld;
    }

    @Override
    public WorldAgentNew clone() throws CloneNotSupportedException {
        WorldAgentNew worldAgentClone = (WorldAgentNew) super.clone();
        worldAgentClone.storyWorld = storyWorld.clone();

        return worldAgentClone;
    }

    public Pair<Boolean, Boolean> checkPreconditions(FabulaElementNew fabEl, List<String> sConditions,
                                      HashMap<String, Object> unsatisfiedConditionsMap,
                                      HashMap<String, Object> totalConditionsMap)
            throws MalformedDataException, DataMismatchException {
        boolean isFullySatisfied = true, isAllTrue = true, canProceed;
        List<String> sParts;
        HashMap<String, ParameterValueNew> sParamValues = fabEl.getParamValues();

        CandidateCharacterIds conflictingCharacterIds;
        CandidateCharacterIds nonConflictingCharacterIds;
        ParameterValueNew agentParam, patientParam, targetParam, tempParam, hiddenParam;
        String sTemp;
        int nLastStop;
        Pattern pattern;
        Matcher matcher;
        ParameterValueNew paramValue;
        String sRegEx;
        Iterator<Integer> nCharIdIterator;
        List<Integer> nCharacterIdList;

        agentParam = sParamValues.get("agent");
        patientParam = sParamValues.get("patient");
        targetParam = sParamValues.get("target");
        hiddenParam = sParamValues.get("hidden");

        try {
            /*
            todo check about non-mandatory conditions (see PB4 documentation)
            todo how about OR conditions
            todo how about disabling non/conflicting characters when map arguments are set to null
            */

            /* Iterate through each precondition */
            for (String sCondition : sConditions) {
                sParts = Arrays.asList(sCondition.split(":"));

                conflictingCharacterIds = new CandidateCharacterIds();
                nonConflictingCharacterIds = new CandidateCharacterIds();
                tempParam = null;

                totalConditionsMap.put(sCondition, null);

                /* Check which parameter is involved */
                switch (sParts.get(0)) {
                    case "agent":
                        if (agentParam.getData() != null ) {
                            tempParam = agentParam;
                        }
                        break;
                    case "patient":
                        if (patientParam.getData() != null) {
                            tempParam = patientParam;
                        }
                        break;
                    case "target":
                        if (targetParam.getData() != null) {
                            tempParam = targetParam;
                        }
                        break;
                    case "hidden":
                        if (hiddenParam.getData() != null) {
                            tempParam = hiddenParam;
                        }
                        break;
                }

                /* If parameter indicated has a non-null value and are instances of character
                identifiers, proceed in evaluating condition */
                if (tempParam != null && tempParam.getData() instanceof CandidateCharacterIds) {
                    switch (sParts.get(1)) {
                        case "is_hungry":
                        case "is_thirsty":
                        case "is_tired":
                        case "is_asleep":
                        case "social_opportunity":
                        case "feeling":
                        case "has_trait":
                        case "holds":
                        case "location":
                        case "current_goal":
                        case "has_current_goal_history":
                        case "not_has_current_goal_history":
                        case "miscellaneous_state":
                            isFullySatisfied = checkCondition(tempParam, sParts.get(1), sParts.get(2),
                                    conflictingCharacterIds.getCharacterIds(), nonConflictingCharacterIds.getCharacterIds(), sParts.get(0));
                            break;
                        case "has_perception":
                        case "has_belief":
                        case "not_has_perception":
                        case "not_has_belief":
                            if(sParts.get(1).equals("has_perception") || sParts.get(1).equals("not_has_perception"))
                                sRegEx = "[0-9]+=(#?[a-z_]+)";
                            else
                                sRegEx = "[a-z_]+=(#?[a-z_]+)";

                            if(sParts.get(2).matches(sRegEx)) {
                                sTemp = "";
                                nLastStop = 0;
                                pattern = Pattern.compile("#[a-z_]+");
                                matcher = pattern.matcher(sParts.get(2));
                                while (matcher.find()) {
                                    paramValue = fabEl.getParamValues().get(sParts.get(2).substring(
                                            matcher.start() + 1, matcher.end()));
                                    sTemp += sParts.get(2).substring(nLastStop, matcher.start());
                                    if(paramValue != null) {
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
                                    }
                                    else {
                                        throw new DataMismatchException("Invalid parameter encountered " +
                                                "in a fabula element precondition: "+sCondition+".");
                                    }
                                    nLastStop = matcher.end();
                                }
                                sTemp += sParts.get(2).substring(nLastStop, sParts.get(2).length());
                                isFullySatisfied = checkCondition(tempParam, sParts.get(1), sTemp,
                                        conflictingCharacterIds.getCharacterIds(), nonConflictingCharacterIds.getCharacterIds(), sParts.get(0));
                            }
                            else {
                                throw new MalformedDataException("Unable to parse malformed attribute " +
                                        "value in precondition: "+sCondition+".");
                            }
                            break;
                        default:
                            isFullySatisfied = false;
                            break;

                        // todo verify if social activity, emotions and goal should be included here
                    }
                }
                else if (tempParam != null && tempParam.getData() instanceof FabulaElementNew) {
                    switch (sParts.get(1)) {
                        case "has_agent_character":
                            isFullySatisfied = ((FabulaElementNew)tempParam.getData()).checkCondition(sParts.get(1), sParts.get(2), sParamValues);
                            break;
                    }
                }
                else {
                    isFullySatisfied = false; // todo is this correct?
                }

                if (!isFullySatisfied) {
                    if (unsatisfiedConditionsMap != null) {
                        if (tempParam.getData() instanceof CandidateCharacterIds)
                            unsatisfiedConditionsMap.put(sCondition, conflictingCharacterIds);
                        else if (tempParam.getData() instanceof FabulaElementNew)
                            unsatisfiedConditionsMap.put(sCondition, tempParam.getData());
                        else
                            unsatisfiedConditionsMap.put(sCondition, null);
                    }
                }
                if (totalConditionsMap != null)
                    if (totalConditionsMap.containsKey(sCondition)) {
                        if (tempParam.getData() instanceof CandidateCharacterIds)
                            totalConditionsMap.put(sCondition, nonConflictingCharacterIds);
                        else if (tempParam.getData() instanceof FabulaElementNew) {
                            if (isFullySatisfied)
                                totalConditionsMap.put(sCondition, tempParam.getData());
                        }
                    }

                isAllTrue = isAllTrue && isFullySatisfied;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MalformedDataException("Error in checking fabula conditions.");
        }

        canProceed = true;
        for (Map.Entry<String, Object> pair2 : totalConditionsMap.entrySet()) {
            if ((pair2.getValue() instanceof CandidateCharacterIds &&
                    ((CandidateCharacterIds) pair2.getValue()).getCharacterIds().isEmpty()) ||
                    (pair2.getValue() == null))
                canProceed = false;
        }

        return new Pair<>(isAllTrue, canProceed);
    }

    public boolean checkCondition(ParameterValueNew paramValue, String sAttribute, String sValue,
                                  List<CharacterIdentifierNew> conflictingCharacters,
                                  List<CharacterIdentifierNew> nonConflictingCharacters, String sParam) {
        CharacterIdentifierNew charId;
        CandidateCharacterIds characters;
        boolean isSatisfied;
        boolean isSatisfiedInner;
        Iterator<CharacterIdentifierNew> iterator;

        characters = (CandidateCharacterIds) paramValue.getData();
        isSatisfied = true;
        iterator = characters.iterator();
        while (iterator.hasNext()) {
            charId = iterator.next();
            isSatisfiedInner = checkCondition(charId, sAttribute, sValue, sParam);
            if (!isSatisfiedInner) {
                conflictingCharacters.add(charId);
                iterator.remove();
            } else {
                nonConflictingCharacters.add(charId);
            }
            isSatisfied = isSatisfied && isSatisfiedInner;
        }
        return isSatisfied;

    }

    public boolean checkCondition(CharacterIdentifierNew characterId, String sAttribute, String sValue, String sParam) {
        CharacterNew character = getCharacterById(characterId.getnCharacterId());
        characterId.setsRecentParamAssignment(sParam);
        return character.checkCondition(sAttribute, sValue);

    }

    public void updateCharacterCurrentGoal(int nGoal, int nCharId) {
        for (CharacterNew character : getCharacters()) {
            if (character.getnId() == nCharId) {
                character.setnCurrentGoal(nGoal);
                character.getnCurrentGoalHistory().clear();
            }
        }
    }

    public void updateCharacterCurrentGoalHistory(int nFabElemId, int nCharId) {
        for (CharacterNew character : getCharacters()) {
            if (character.getnId() == nCharId) {
                character.getnCurrentGoalHistory().add(nFabElemId);
            }
        }
    }

    public void realizeConditions(FabulaElementNew fabElem, List<String> sConditions)
            throws MalformedDataException, MissingDataException, DataMismatchException {

        String sParts[];
        ParameterValueNew parameterTemp;
        HashMap<String, ParameterValueNew> sParamValues;
        CharacterNew character;
        Iterator iteratorChar;
        Object agentValueData = null;
        Object patientValueData = null;
        Object targetValueData = null;
        Object hiddenValueData = null;
        Object currentValueData = null;

        sParamValues = fabElem.getParamValues();

        parameterTemp = sParamValues.get("agent");
        if (parameterTemp != null) {
            agentValueData = parameterTemp.getData();
        }

        parameterTemp = sParamValues.get("patient");
        if (parameterTemp != null) {
            patientValueData = parameterTemp.getData();
        }

        parameterTemp = sParamValues.get("target");
        if (parameterTemp != null) {
            targetValueData = parameterTemp.getData();
        }

        parameterTemp = sParamValues.get("hidden");
        if (parameterTemp != null) {
            hiddenValueData = parameterTemp.getData();
        }

        try {
            for (String sCondition : sConditions) {
                sParts = sCondition.split(":");

                switch (sParts[0]) {
                    case "agent":
                        currentValueData = agentValueData;
                        break;
                    case "patient":
                        currentValueData = patientValueData;
                        break;
                    case "target":
                        currentValueData = targetValueData;
                        break;
                    case "hidden":
                        currentValueData = hiddenValueData;
                        break;
                }

                if (currentValueData instanceof CandidateCharacterIds) {
                    iteratorChar = ((CandidateCharacterIds) currentValueData).iterator();
                    while (iteratorChar.hasNext()) {
                        character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                        character.realizeCondition(sParts[1], sParts[2], sCondition, sParamValues);
                    }
                }
                else if (currentValueData instanceof FabulaElementNew) {
                    ((FabulaElementNew)currentValueData).realizeCondition(sParts[1], sParts[2], sCondition, sParamValues);
                }
                else {
                    // todo what should be here?
                    System.out.println("...");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedDataException("Error in parsing Fabula Element preconditions/postconditions.");
        }
    }
}
