package process.storyplanning;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
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
                                      HashMap<String, List<CharacterIdentifierNew>> unsatisfiedConditionsMap,
                                      HashMap<String, List<CharacterIdentifierNew>> totalConditionsMap)
            throws MalformedDataException, DataMismatchException {
        boolean isFullySatisfied = true, isAllTrue = true, canProceed;
        List<String> sParts;
        HashMap<String, ParameterValueNew> sParamValues = fabEl.getParamValues();
        List<CharacterIdentifierNew> conflictingCharacters;
        List<CharacterIdentifierNew> nonConflictingCharacters;
        ParameterValueNew agentParam, patientParam, targetParam, tempParam;
        String sTemp;
        int nLastStop;
        Pattern pattern;
        Matcher matcher;
        ParameterValueNew paramValue;
        String sRegEx;

        agentParam = sParamValues.get("agent");
        patientParam = sParamValues.get("patient");
        targetParam = sParamValues.get("target");

        try {
            /*
            todo check about non-mandatory conditions (see PB4 documentation)
            todo how about OR conditions
            todo how about disabling non/conflicting characters when map arguments are set to null
            */

            /* Iterate through each precondition */
            for (String sCondition : sConditions) {
                sParts = Arrays.asList(sCondition.split(":"));
                conflictingCharacters = new ArrayList<>();
                nonConflictingCharacters = new ArrayList<>();
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
                        case "trait":
                        case "holds":
                        case "location":
                        case "current_goal":
                        case "has_current_goal_history":
                        case "not_has_current_goal_history":
                            isFullySatisfied = checkCondition(tempParam, sParts.get(1), sParts.get(2),
                                    conflictingCharacters, nonConflictingCharacters, "agent");
                            break;
                        case "has_perception":
                        case "has_belief":
                        case "not_has_perception":
                        case "not_has_belief":
                            if(sParts.get(1).equals("has_perception") || sParts.get(1).equals("not_has_perception"))
                                sRegEx = "[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*";
                            else
                                sRegEx = "(#*[a-z_]+)(,(#*[a-z_]+))*=[a-z_]+";

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
                                        sTemp += paramValue.getId();
                                    }
                                    else {
                                        throw new DataMismatchException("Invalid parameter encountered " +
                                                "in a fabula element precondition: "+sCondition+".");
                                    }
                                    nLastStop = matcher.end();
                                }
                                sTemp += sParts.get(2).substring(nLastStop, sParts.get(2).length());
                                isFullySatisfied = checkCondition(tempParam, sParts.get(1), sTemp,
                                        conflictingCharacters, nonConflictingCharacters, "agent");
                            }
                            else {
                                throw new MalformedDataException("Unable to parse malformed attribute " +
                                        "value in precondition: "+sCondition+".");
                            }
                            break;
                        // todo how about not_has_perception and not_has_belief
                        // todo verify if social activity, emotions and goal should be included here
                    }
                }
                else {
                    isFullySatisfied = false;
                }

                if (!isFullySatisfied) {
                    if (unsatisfiedConditionsMap != null)
                        unsatisfiedConditionsMap.put(sCondition, conflictingCharacters);
                }
                if (totalConditionsMap != null)
                    if (totalConditionsMap.containsKey(sCondition))
                        totalConditionsMap.put(sCondition, nonConflictingCharacters);

                isAllTrue = isAllTrue && isFullySatisfied;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MalformedDataException("Error in checking fabula conditions.");
        }

        canProceed = true;
        for (Map.Entry<String, List<CharacterIdentifierNew>> pair2 : totalConditionsMap.entrySet()) {
            if (pair2.getValue().isEmpty())
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
        Iterator iterator;

        characters = (CandidateCharacterIds) paramValue.getData();
        isSatisfied = true;
        iterator = characters.iterator();
        while (iterator.hasNext()) {
            charId = (CharacterIdentifierNew) iterator.next();
            isSatisfiedInner = checkCondition(charId, sAttribute, sValue, sParam);
            if (!isSatisfiedInner) {
                conflictingCharacters.add(charId);
                characters.removeCandidate(charId);
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

    public void realizeConditions(FabulaElementNew fabEl, List<String> sConditions)
            throws MalformedDataException, MissingDataException, DataMismatchException {
        String sParts[];
        ParameterValueNew parameterTemp;
        Object sParamValueData;
        HashMap<String, ParameterValueNew> sParamValues = fabEl.getParamValues();
        CharacterNew character;
        Iterator iteratorChar;
        Matcher matcher;
        Pattern pattern;
        String sTemp;
        int nLastStop;
        ParameterValueNew paramValue;
        String sRegEx;

        CandidateCharacterIds agentCandidateChars = null, patientCandidateChars = null, targetCandidateChars = null;

        parameterTemp = sParamValues.get("agent");
        if (parameterTemp != null && (sParamValueData = parameterTemp.getData()) != null) {
            if(sParamValueData instanceof CandidateCharacterIds) {
                agentCandidateChars = (CandidateCharacterIds) sParamValueData;
            }
        }

        parameterTemp = sParamValues.get("patient");
        if (parameterTemp != null && (sParamValueData = parameterTemp.getData()) != null) {
            if(sParamValueData instanceof CandidateCharacterIds) {
                patientCandidateChars = (CandidateCharacterIds) sParamValueData;
            }
        }

        parameterTemp = sParamValues.get("target");
        if (parameterTemp != null && (sParamValueData = parameterTemp.getData()) != null) {
            if(sParamValueData instanceof CandidateCharacterIds) {
                targetCandidateChars = (CandidateCharacterIds) sParamValueData;
            }
        }

        try {
            for (String sCondition : sConditions) {
                sParts = sCondition.split(":");

                iteratorChar = null;

                // Realize conditions if agent is a character instance
                switch (sParts[0]) {
                    case "agent":
                        if (agentCandidateChars != null) {
                            iteratorChar = agentCandidateChars.iterator();
                        }
                        break;
                    case "patient":
                        if (patientCandidateChars != null) {
                            iteratorChar = patientCandidateChars.iterator();
                        }
                        break;
                    case "target":
                        if (targetCandidateChars != null) {
                            iteratorChar = targetCandidateChars.iterator();
                        }
                        break;
                }

                // todo move inside CharacterNew class
                if (iteratorChar != null) {
                    switch (sParts[1]) {
                        case "is_hungry":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setIsHungry(Boolean.parseBoolean(sParts[2]));
                            }
                            break;
                        case "is_thirsty":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setIsThirsty(Boolean.parseBoolean(sParts[2]));
                            }
                            break;
                        case "is_tired":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setIsTired(Boolean.parseBoolean(sParts[2]));
                            }
                            break;
                        case "is_asleep":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setIsAsleep(Boolean.parseBoolean(sParts[2]));
                            }
                            break;
                        case "social_opportunity":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setIsSocialOpportunity(Boolean.parseBoolean(sParts[2]));
                            }
                            break;
                        case "feeling":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setnFeeling(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "trait":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.learnTrait(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "holds":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setnHolds(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "location":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setnLocation(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "current_goal":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.setnCurrentGoal(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "has_current_goal_history":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.addnCurrentGoalHistory(Integer.parseInt(sParts[2]));
                            }
                        case "not_has_current_goal_history":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                character.removenCurrentGoalHistory(Integer.parseInt(sParts[2]));
                            }
                            break;
                        case "has_perception":
                        case "not_has_perception":
                        case "has_belief":
                        case "not_has_belief":
                            while (iteratorChar.hasNext()) {
                                character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                if (sParts[1].equals("has_perception") || sParts[1].equals("not_has_perception"))
                                    sRegEx = "[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*";
                                else
                                    sRegEx = "(#*[a-z_]+)(,(#*[a-z_]+))*=[a-z_]+";

                                if (sParts[2].matches(sRegEx)) {
                                    sTemp = "";
                                    nLastStop = 0;
                                    pattern = Pattern.compile("#[a-z_]+");
                                    matcher = pattern.matcher(sParts[2]);
                                    while (matcher.find()) {
                                        paramValue = fabEl.getParamValues().get(sParts[2].substring(matcher.start() + 1, matcher.end()));
                                        sTemp += sParts[2].substring(nLastStop, matcher.start());
                                        if (paramValue != null) {
                                            sTemp += paramValue.getId();
                                        } else {
                                            throw new DataMismatchException("Invalid parameter encountered in a fabula element precondition: " + sCondition + ".");
                                        }
                                        nLastStop = matcher.end();
                                    }
                                    sTemp += sParts[2].substring(nLastStop, sParts[2].length());
                                    switch (sParts[1]) {
                                        case "has_perception":
                                            character.addsPerception(sTemp);
                                            break;
                                        case "not_has_perception":
                                            character.removesPerception(sTemp);
                                            break;
                                        case "has_belief":
                                            character.addsBelief(sTemp);
                                            break;
                                        case "not_has_belief":
                                            character.removesBelief(sTemp);
                                            break;
                                    }

                                } else {
                                    throw new MalformedDataException("Unable to parse malformed attribute value in precondition: " + sCondition + ".");
                                }
                            }
                            break;
                        // todo verify if social activity, emotions and goal should be included here
                        // todo how about character perception
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedDataException("Error in parsing Fabula Element preconditions/postconditions.");
        }
    }
}
