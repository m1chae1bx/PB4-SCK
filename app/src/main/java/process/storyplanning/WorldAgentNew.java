package process.storyplanning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    public boolean checkPreconditions(FabulaElementNew fabEl,
                                      HashMap<String, List<CharacterIdentifierNew>> unsatisfiedConditionsMap,
                                      HashMap<String, List<CharacterIdentifierNew>> totalConditionsMap)
            throws MalformedDataException, DataMismatchException {
        boolean isFullySatisfied = true, isAllTrue = true;
        List<String> sParts;
        HashMap<String, ParameterValueNew> sParamValues = fabEl.getParamValues();
        List<String> sConditions = fabEl.getsPreconditions();
        List<CharacterIdentifierNew> conflictingCharacters;
        List<CharacterIdentifierNew> nonConflictingCharacters;
        ParameterValueNew agentParam, patientParam, targetParam;
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
            todo how about perception
            */
            for (String sCondition : sConditions) {
                sParts = Arrays.asList(sCondition.split(":"));
                conflictingCharacters = new ArrayList<>();
                nonConflictingCharacters = new ArrayList<>();
                totalConditionsMap.put(sCondition, new ArrayList<CharacterIdentifierNew>());

                switch (sParts.get(0)) {
                    case "agent":
                        // Check conditions if agent is a character instance
                        if (agentParam.getData() != null && agentParam.getData() instanceof CandidateCharacterIds) {
                            switch (sParts.get(1)) {
                                case "is_hungry":
                                case "is_thirsty":
                                case "is_tired":
                                case "is_asleep":
                                case "feeling":
                                case "trait":
                                case "holds":
                                case "location":
                                    isFullySatisfied = checkCondition(agentParam, sParts.get(1), sParts.get(2),
                                            conflictingCharacters, nonConflictingCharacters, "agent");
                                    break;
                                case "has_perception":
                                case "has_belief":
                                    if(sParts.get(1).equals("has_perception"))
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
                                        isFullySatisfied = checkCondition(agentParam, sParts.get(1), sTemp,
                                                conflictingCharacters, nonConflictingCharacters, "agent");
                                    }
                                    else {
                                        throw new MalformedDataException("Unable to parse malformed attribute " +
                                                "value in precondition: "+sCondition+".");
                                    }
                                    break;
                                // todo verify if social activity, emotions and goal should be included here
                            }
                        }
                        break;
                    case "patient":
                        if (patientParam.getData() != null && patientParam.getData() instanceof CandidateCharacterIds) {
                            switch (sParts.get(1)) {
                                case "is_hungry":
                                case "is_thirsty":
                                case "is_tired":
                                case "is_asleep":
                                case "feeling":
                                case "trait":
                                case "holds":
                                case "location":
                                    isFullySatisfied = checkCondition(patientParam, sParts.get(1), sParts.get(2),
                                            conflictingCharacters, nonConflictingCharacters, "patient");
                                    break;
                                case "has_perception":
                                    if(sParts.get(2).matches("[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*")) {
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
                                        isFullySatisfied = checkCondition(patientParam, sParts.get(1), sTemp,
                                                conflictingCharacters, nonConflictingCharacters, "patient");
                                    }
                                    else {
                                        throw new MalformedDataException("Unable to parse malformed attribute " +
                                                "value in precondition: "+sCondition+".");
                                    }
                                    break;
                                    // todo verify if social activity, emotions and goal should be included here
                            }
                        }
                        break;
                    case "target":
                        if (targetParam.getData() != null && targetParam.getData() instanceof CandidateCharacterIds) {
                            switch (sParts.get(1)) {
                                case "is_hungry":
                                case "is_thirsty":
                                case "is_tired":
                                case "is_asleep":
                                case "feeling":
                                case "trait":
                                case "holds":
                                case "location":
                                    isFullySatisfied = checkCondition(targetParam, sParts.get(1), sParts.get(2),
                                            conflictingCharacters, nonConflictingCharacters, "target");
                                    break;
                                case "has_perception":
                                    if(sParts.get(2).matches("[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*")) {
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
                                        isFullySatisfied = checkCondition(targetParam, sParts.get(1), sTemp,
                                                conflictingCharacters, nonConflictingCharacters, "target");
                                    }
                                    else {
                                        throw new MalformedDataException("Unable to parse malformed attribute " +
                                                "value in precondition: "+sCondition+".");
                                    }
                                    break;
                                    // todo verify if social activity, emotions and goal should be included here
                            }
                        }
                        break;
                }
                if (!isFullySatisfied) {
                    unsatisfiedConditionsMap.put(sCondition, conflictingCharacters);
                }
                totalConditionsMap.put(sCondition, nonConflictingCharacters);

                isAllTrue = isAllTrue && isFullySatisfied;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MalformedDataException("Error in checking fabula conditions.");
        }
        return isAllTrue;
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
        String sMatched;

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

                switch (sParts[0]) {
                    case "agent":
                        // Realize conditions if agent is a character instance
                        if (agentCandidateChars != null) {
                            iteratorChar = agentCandidateChars.iterator();
                            // todo move inside CharacterNew class
                            switch (sParts[1]) {
                                case "is_hungry":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew)iteratorChar.next()).getnCharacterId());
                                        character.setIsHungry(Boolean.parseBoolean(sParts[2]));
                                    }
                                    break;
                                case "is_thirsty":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew)iteratorChar.next()).getnCharacterId());
                                        character.setIsThirsty(Boolean.parseBoolean(sParts[2]));
                                    }
                                    break;
                                case "is_tired":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew)iteratorChar.next()).getnCharacterId());
                                        character.setIsTired(Boolean.parseBoolean(sParts[2]));
                                    }
                                    break;
                                case "is_asleep":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                        character.setIsAsleep(Boolean.parseBoolean(sParts[2]));
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
                                case "has_perception":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                        if(sParts[2].matches("[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*")) {
                                            sTemp = "";
                                            nLastStop = 0;
                                            pattern = Pattern.compile("#[a-z_]+");
                                            matcher = pattern.matcher(sParts[2]);
                                            while (matcher.find()) {
                                                paramValue = fabEl.getParamValues().get(sParts[2].substring(matcher.start() + 1, matcher.end()));
                                                sTemp += sParts[2].substring(nLastStop, matcher.start());
                                                if(paramValue != null) {
                                                    sTemp += paramValue.getId();
                                                }
                                                else {
                                                    throw new DataMismatchException("Invalid parameter encountered in a fabula element precondition: "+sCondition+".");
                                                }
                                                nLastStop = matcher.end();
                                            }
                                            sTemp += sParts[2].substring(nLastStop, sParts[2].length());
                                            character.addsPerception(sTemp);
                                        }
                                        else {
                                            throw new MalformedDataException("Unable to parse malformed attribute value in precondition: "+sCondition+".");
                                        }
                                    }
                                    break;
                                // todo verify if social activity, emotions and goal should be included here
                                // todo how about character perception
                            }
                        }
                        break;
                    case "patient":
                        if (patientCandidateChars != null) {
                            iteratorChar = patientCandidateChars.iterator();
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
                                case "has_perception":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                        if(sParts[2].matches("[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*")) {
                                            sTemp = "";
                                            nLastStop = 0;
                                            pattern = Pattern.compile("#[a-z_]+");
                                            matcher = pattern.matcher(sParts[2]);
                                            while (matcher.find()) {
                                                paramValue = fabEl.getParamValues().get(sParts[2].substring(matcher.start() + 1, matcher.end()));
                                                sTemp += sParts[2].substring(nLastStop, matcher.start());
                                                if(paramValue != null) {
                                                    sTemp += paramValue.getId();
                                                }
                                                else {
                                                    throw new DataMismatchException("Invalid parameter encountered in a fabula element precondition: "+sCondition+".");
                                                }
                                                nLastStop = matcher.end();
                                            }
                                            sTemp += sParts[2].substring(nLastStop, sParts[2].length());
                                            character.addsPerception(sTemp);
                                        }
                                        else {
                                            throw new MalformedDataException("Unable to parse malformed attribute value in precondition: "+sCondition+".");
                                        }
                                    }
                                    break;
                                // todo verify if social activity, emotions and goal should be included here
                                // todo how about character perception
                            }
                        }
                        break;
                    case "target":
                        if (targetCandidateChars != null) {
                            iteratorChar = targetCandidateChars.iterator();
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
                                case "has_perception":
                                    while (iteratorChar.hasNext()) {
                                        character = getCharacterById(((CharacterIdentifierNew) iteratorChar.next()).getnCharacterId());
                                        if(sParts[2].matches("[0-9]+=(#*[a-z_]+)(,(#*[a-z_]+))*")) {
                                            sTemp = "";
                                            nLastStop = 0;
                                            pattern = Pattern.compile("#[a-z_]+");
                                            matcher = pattern.matcher(sParts[2]);
                                            while (matcher.find()) {
                                                paramValue = fabEl.getParamValues().get(sParts[2].substring(matcher.start() + 1, matcher.end()));
                                                sTemp += sParts[2].substring(nLastStop, matcher.start());
                                                if(paramValue != null) {
                                                    sTemp += paramValue.getId();
                                                }
                                                else {
                                                    throw new DataMismatchException("Invalid parameter encountered in a fabula element precondition: "+sCondition+".");
                                                }
                                                nLastStop = matcher.end();
                                            }
                                            sTemp += sParts[2].substring(nLastStop, sParts[2].length());
                                            character.addsPerception(sTemp);
                                        }
                                        else {
                                            throw new MalformedDataException("Unable to parse malformed attribute value in precondition: "+sCondition+".");
                                        }
                                    }
                                    break;
                                // todo verify if social activity, emotions and goal should be included here
                                // todo how about character perception
                            }
                        }
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedDataException("Error in parsing Fabula Element preconditions/postconditions.");
        }
    }
}
