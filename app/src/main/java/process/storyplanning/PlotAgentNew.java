package process.storyplanning;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DBQueriesNew;
import model.narratologicalmodel.AuthorGoal;
import model.narratologicalmodel.ConflictGoals;
import model.narratologicalmodel.ContextGoals;
import model.narratologicalmodel.GoalTraitNew;
import model.narratologicalmodel.ResolutionGoal;
import model.ontologymodel.SemanticRelation;
import model.socioculturalmodel.Norm;
import model.storyplanmodel.CandidateCharacterIds;
import model.storyplanmodel.FabulaElementNew;
import model.storyplanmodel.LinkNew;
import model.storyplanmodel.ParameterValueNew;
import model.storyplanmodel.StoryPlanNew;
import model.storyworldmodel.CharacterIdentifierNew;
import process.exceptions.DataMismatchException;
import process.exceptions.MalformedDataException;
import process.exceptions.MissingDataException;
import process.exceptions.OperationUnavailableException;
import process.helpers.FabulaNodeNew;
import process.helpers.ExecutionStackElement;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class PlotAgentNew {

    private FabulaNodeNew currentElementInExecution;

    public PlotAgentNew() {
        currentElementInExecution = null;
    }

    public GoalTraitNew selectGoalTrait(List<Integer> nNegTraitsCIds, int nLocId,
                                        List<Integer> nObjCIds) throws MissingDataException {

        List<GoalTraitNew> possibleGoalTraits = DBQueriesNew.getGoalTraits(nLocId); // get goal traits that are related to bg
        int nScore;
        int nMaxScore = 0;
        int nOppositeTraitCId;
        Map<Integer, GoalTraitNew> nScoreMap = new HashMap<>();
        List<GoalTraitNew> tempList;

        /* ------ Score each goal trait (goal traits in the list are related to the background) ------ */
        for (GoalTraitNew g : possibleGoalTraits) {
            nScore = 0;
            try {
                nOppositeTraitCId = DBQueriesNew.getRelatedConcepts(g.getnTrait(),
                        SemanticRelation.OPPOSITE_OF).get(0);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                new MissingDataException("Error while querying opposite trait of a goal trait").printStackTrace();
                continue;
            }
            if (nNegTraitsCIds.contains(nOppositeTraitCId))
                nScore++;
            if (nObjCIds.contains(g.getnRObj()))
                nScore++;
            if (nMaxScore < nScore)
                nMaxScore = nScore;
            nScoreMap.put(nScore, g);
        }

        /* ------ Get top goal traits ------ */
        tempList = new ArrayList<>();
        for (Map.Entry<Integer, GoalTraitNew> entry : nScoreMap.entrySet()) {
            if (nMaxScore == entry.getKey())
                tempList.add(entry.getValue());
        }

        /* ------ Choose one randomly from top goal traits and return ------ */
        if (!tempList.isEmpty())
            return tempList.get((int) (Math.random() * tempList.size()));
        else
            throw new MissingDataException("Error in querying for goal trait using the given input.");
    }

    public ConflictGoals selectConflict(int nGoalTraitId) {
        List<ConflictGoals> conflictGoalsList = DBQueriesNew.getConflictGoals(nGoalTraitId);
        return conflictGoalsList.get((int) (Math.random() * conflictGoalsList.size()));
    }

    /**
     * Returns true if critical preconditions of context goals can be met by chosen story world characters; otherwise, false.
     *
     * @param characters the list of characters
     * @return
     */
    public boolean checkIfContextSuitsChars(List<CharacterIdentifierNew> characters) { // todo change to character identifier
        boolean isMet = false;

        // todo complete this, for now set return to true
//      Character Representation:
//        private int nId;
//        private int nConceptId;
//        private String sName;
//        private String sImagePath;
//        private int nGender; // 0 - female, 1 - male
//        private List<Integer> nPositiveTraits;
//        private List<Integer> nNegativeTraits;
//        private List<String> nStatusRelations;
//        private List<Integer> nPreferences;
//        private List<Integer> nEmotions = new ArrayList<>();
//        private List<Integer> nFeelings = new ArrayList<>();
//        private List<Integer> nPerception = new ArrayList<>();
//        private boolean isHungry;
//        private boolean isThirsty;
//        private boolean isTired;
//        private boolean isAsleep;
//        private Integer nLocation;
//        private Integer nCarries; // assuming character can only carry one object
//        private Integer nSocialEvent;


        return true; // for now
    }

    /**
     * Returns true if critical preconditions of conflict goals can be met by chosen story world
     * characters; otherwise, false.
     *
     * @param characters
     * @return
     */
    public boolean checkIfConflictSuitsChars(List<CharacterIdentifierNew> characters) { // todo change to character identifier
        // todo complete this
        // temporarily returning true always

        return true; // temporarily
    }

    /**
     * Initialize required parameters of a fabula element each with a new instance of ParameterValueNew
     * class. If a particular parameter is already initialized through linking of parameter values
     * between two fabula elements, it is ignored by the method.
     *
     * @param fabulaElement the fabula elements whose required required parameters are to be initialized.
     */
    private void initializeRequiredParameters(FabulaElementNew fabulaElement) {
        List<String> sRequiredParams = fabulaElement.getsRequiredParams();
        HashMap<String, ParameterValueNew> paramValues = fabulaElement.getParamValues();
        for (String sRequiredParam : sRequiredParams) {
            switch (sRequiredParam) {
                case "agent":
                    if (!paramValues.containsKey(FabulaElementNew.PARAMS_AGENT)) {
                        paramValues.put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
                    }
                    break;
                case "patient":
                    if (!paramValues.containsKey(FabulaElementNew.PARAMS_PATIENT)) {
                        paramValues.put(FabulaElementNew.PARAMS_PATIENT, new ParameterValueNew());
                    }
                    break;
                case "target":
                    if (!paramValues.containsKey(FabulaElementNew.PARAMS_TARGET)) {
                        paramValues.put(FabulaElementNew.PARAMS_TARGET, new ParameterValueNew());
                    }
                    break;
            }
        }
        if (!paramValues.containsKey(FabulaElementNew.PARAMS_AGENT)) {
            paramValues.put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
        }
    }

    private boolean checkForPrerequisites(
            HashMap<String, Object> totalConditionsMap,
            HashMap<String, Object> unsatisfiedConditionsMap, FabulaNodeNew currentFabNode,
            FabulaNodeNew prevFabNode, List<Pair<FabulaNodeNew, LinkNew>> additionalNodesAndLink,
            List<FabulaNodeNew> storyPath) throws OperationUnavailableException {

        List<Pair<FabulaNodeNew, LinkNew>> possiblePrerequisites = new ArrayList<>();
        List<String> sConditionParts;
        Set<CharacterIdentifierNew> charactersIncluded;
        Set<CharacterIdentifierNew> charactersToRestore;
        LinkNew currentLink;
        HashMap<String, String> sParamDependencies;
        String sTransformedCondition;
        String sTemp;
        String sTempNew;
        String sMatched;
        Pattern pattern;
        Matcher matcher;
        Iterator<Map.Entry<String, Object>> iteratorUCM;
        Iterator<String> iteratorPC;
        Iterator<Pair<FabulaNodeNew, LinkNew>> iteratorPPQ;
        Map.Entry<String, Object> pair;
        Pair<FabulaNodeNew, LinkNew> sourcePair;
        String sPostcondition;
        FabulaNodeNew fabNodePrerequisite;
        int nLastStop;
        boolean canProceed;
        boolean isInclude;
        long seed;

        for (Pair<FabulaNodeNew, LinkNew> pair2 : currentFabNode.getSources()) {
            if (pair2.first != prevFabNode && !storyPath.contains(pair2.first)) {
                possiblePrerequisites.add(pair2);
            }
        }
        seed = System.nanoTime();
        Collections.shuffle(possiblePrerequisites, new Random(seed));

        charactersToRestore = new HashSet<>();

        iteratorPPQ = possiblePrerequisites.iterator();
        while (iteratorPPQ.hasNext() && !unsatisfiedConditionsMap.isEmpty()) {
            sourcePair = iteratorPPQ.next();
            fabNodePrerequisite = sourcePair.first;
            currentLink = sourcePair.second;

            sParamDependencies = currentLink.getsParamDependencies();
            isInclude = false;
            charactersIncluded = new HashSet<>();
            iteratorPC = fabNodePrerequisite.getData().getsPostconditions().iterator();
            while (iteratorPC.hasNext() && !unsatisfiedConditionsMap.isEmpty()) {
                sPostcondition = iteratorPC.next();
                sConditionParts = Arrays.asList(sPostcondition.split(":"));
                sTransformedCondition = "" + sParamDependencies.get(sConditionParts.get(0)) + ":" +
                        sConditionParts.get(1) + ":";
                pattern = Pattern.compile("#[a-z_]+");
                sTemp = sConditionParts.get(2);
                sTempNew = "";
                matcher = pattern.matcher(sTemp);
                nLastStop = 0;
                while (matcher.find()) {
                    sMatched = sTemp.substring(matcher.start() + 1, matcher.end());
                    sTempNew += sTemp.substring(nLastStop, matcher.start() + 1);
                    sTempNew += sParamDependencies.get(sMatched);
                    nLastStop = matcher.end();
                }
                sTempNew += sTemp.substring(nLastStop, sTemp.length());
                sTransformedCondition += sTempNew;
                iteratorUCM = unsatisfiedConditionsMap.entrySet().iterator();
                while (iteratorUCM.hasNext()) {
                    pair = iteratorUCM.next();
                    if (pair.getKey().equalsIgnoreCase(sTransformedCondition)) { // just terminated here
                        totalConditionsMap.put(sTransformedCondition, pair.getValue());
                        isInclude = true;
                        if (pair.getValue() instanceof CandidateCharacterIds)
                            charactersIncluded.addAll(((CandidateCharacterIds) pair.getValue()).getCharacterIds());
                        unsatisfiedConditionsMap.remove(pair.getKey());
                    }
                }
            }
            if (isInclude) {
                additionalNodesAndLink.add(new Pair<>(fabNodePrerequisite, currentLink));
                charactersToRestore.addAll(charactersIncluded);
                try {
                    linkParamValuesUpward(fabNodePrerequisite.getData(), currentFabNode.getData(), sParamDependencies);
                } catch (DataMismatchException e) {
                    e.printStackTrace();
                    continue;
                }
                initializeRequiredParameters(fabNodePrerequisite.getData());
            }

        }

        canProceed = true;
        for (Map.Entry<String, Object> pair2 : totalConditionsMap.entrySet()) {
            if ((pair2.getValue() instanceof CandidateCharacterIds &&
                    ((CandidateCharacterIds) pair2.getValue()).getCharacterIds().isEmpty()) ||
                    (pair2.getValue() == null))
                canProceed = false;
        }

        if (canProceed) {
            restoreCharacters(currentFabNode.getData(), charactersToRestore);
        }

        return canProceed;
    }

    private void restoreCharacters(FabulaElementNew fabulaElement, Set<CharacterIdentifierNew> characterIds)
            throws OperationUnavailableException {
        for (CharacterIdentifierNew characterId : characterIds) {
            (fabulaElement.getParamValues().
                    get(characterId.getsRecentParamAssignment())).restore(characterId);
        }
    }

    // added on 7-25
    public ContextGoals selectContext(int nConflictId) {
        List<ContextGoals> contextGoalsList;
        contextGoalsList = DBQueriesNew.getContextGoals(nConflictId);
        return contextGoalsList.get((int) (Math.random() * contextGoalsList.size()));
    }

    public ResolutionGoal selectResolution(int nConflictId) {
        List<ResolutionGoal> resolutionGoalsList = DBQueriesNew.getResolutionGoals(nConflictId);
        return resolutionGoalsList.get((int) (Math.random() * resolutionGoalsList.size()));
    }

    public boolean generateStory(ContextGoals contextGoals, ConflictGoals conflictGoals,
                                 ResolutionGoal resolutionGoal, StoryPlanNew storyPlan,
                                 WorldAgentNew worldAgent)
            throws DataMismatchException, MalformedDataException, MissingDataException,
            CloneNotSupportedException {

        int nContextDirection;
        long seed;
        Stack<FabulaNodeNew> possibleStartingGoalsMain;
        Stack<FabulaNodeNew> possibleStartingGoalsSupport;
        Stack<AuthorGoal> authorGoals;
        Stack<AuthorGoal> authorGoalsCopy;
        Stack<ExecutionStackElement> executionStack;
        List<Integer> linkIdsOfExecutedFBEs;
        WorldAgentNew worldAgentClone;
        FabulaNodeNew fabNodeTemp;
        boolean forTermination;
        boolean isFirstLoop;

        FabulaNodeNew.clearExistingNodes();
        CharacterIdentifierNew.clearExistingCharIdentifiers();

        nContextDirection = contextGoals.getnSearchDirection();
        if (!linkContextGoals(nContextDirection, contextGoals, worldAgent.getMainCharacter().getIdentifier(),
                worldAgent.getCharacterIds().subList(1, worldAgent.getCharacterIds().size()))) {
            throw new MissingDataException("No path has been found between the context goals. " +
                    "Story generation terminated.");
        }

        authorGoals = compileAuthorGoals(contextGoals, conflictGoals, resolutionGoal);
        authorGoalsCopy = new Stack<>();
        authorGoalsCopy.addAll(authorGoals);

        // search starting goals for context main goal
        possibleStartingGoalsMain = searchAndLinkStartingGoals(contextGoals.getMainGoalNode(), worldAgent);
        seed = System.nanoTime();
        Collections.shuffle(possibleStartingGoalsMain, new Random(seed));

        // search starting goals for context supporting goals
        possibleStartingGoalsSupport = searchAndLinkStartingGoals(contextGoals.getSupportingGoalNode(), worldAgent);
        seed = System.nanoTime();
        Collections.shuffle(possibleStartingGoalsSupport, new Random(seed));

        executionStack = new Stack<>();
        linkIdsOfExecutedFBEs = new ArrayList<>();

        isFirstLoop = true;
        while (!authorGoalsCopy.empty() && !possibleStartingGoalsMain.empty() && !possibleStartingGoalsSupport.empty()) {

            worldAgentClone = worldAgent.clone();
            executionStack.clear();

            if (isFirstLoop) {
                if (nContextDirection == 1) {
                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                } else {
                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                }
            } else {
                if (nContextDirection == 1 && authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnSupportingGoal()) {
                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                } else if (nContextDirection == 1 && authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnMainGoal()) {
                    fabNodeTemp = possibleStartingGoalsSupport.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                } else if (authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnSupportingGoal()) {
                    fabNodeTemp = possibleStartingGoalsMain.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                } else {
                    fabNodeTemp = possibleStartingGoalsSupport.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new ExecutionStackElement(fabNodeTemp, null, null));
                }
            }

            authorGoalsCopy.clear();
            authorGoalsCopy.addAll(authorGoals);

            // generate story path from execution stack until all goals are successfully met
            forTermination = false;
            while (!executionStack.empty() && !forTermination) { // todo check for infinite loop, is there any other condition
                try {
                    forTermination = !execute(executionStack, storyPlan, worldAgentClone, authorGoalsCopy, linkIdsOfExecutedFBEs);
                } catch (MalformedDataException | MissingDataException | DataMismatchException | OperationUnavailableException e) {
                    forTermination = false;
                    e.printStackTrace();
                }
            }

            isFirstLoop = false;
        }

        System.out.println("Author Goals: ");
        for (AuthorGoal aGoal : authorGoalsCopy) {
            System.out.println("aGoal = " + aGoal.toString());
        }
        System.out.println();
        return authorGoalsCopy.empty();
    }

    private boolean execute(Stack<ExecutionStackElement> executionStack,
                            StoryPlanNew storyPlan, WorldAgentNew worldAgentClone,
                            Stack<AuthorGoal> authorGoals, List<Integer> linkIdsOfExecutedFBEs)
            throws CloneNotSupportedException, DataMismatchException, MissingDataException,
            MalformedDataException, OperationUnavailableException {

        FabulaElementNew currentFabElem;
        boolean isSuccessful;
        List<FabulaNodeNew> storyPath;
        CandidateCharacterIds candidateCharacterIds;
        ExecutionStackElement executionStackElement;


        isSuccessful = false;
        executionStackElement = executionStack.peek();
        currentElementInExecution = executionStackElement.fabulaNode;
        currentFabElem = currentElementInExecution.getData();
        storyPath = new ArrayList<>();

        // Debug log
        System.out.println("[DEBUG]: current goal/action in execution = " + currentElementInExecution.getData().toString());
        System.out.println("[DEBUG]: execution stack = " + executionStack.toString());

        switch (currentFabElem.getsCategory()) {
            case FabulaElementNew.CATEGORY_GOAL:
                // Realize preconditions for starting goal only
                if (executionStack.peek().link == null) {
                    if (!currentFabElem.getsPreconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(currentFabElem, currentFabElem.getsPreconditions());
                    }
                    if (!currentFabElem.getsPostconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(currentFabElem, currentFabElem.getsPostconditions());
                    }
                }

                if (currentFabElem.getParamValues().get(FabulaElementNew.PARAMS_AGENT).getData() instanceof CandidateCharacterIds) {
                    candidateCharacterIds = (CandidateCharacterIds)currentFabElem.getParamValues().get(FabulaElementNew.PARAMS_AGENT).getData();
                    for (CharacterIdentifierNew charId : candidateCharacterIds.getCharacterIds()) {
                        worldAgentClone.updateCharacterCurrentGoal(currentFabElem.getnId(), charId.getnCharacterId());
                    }
                }

                if (authorGoals.peek().getnFabGoalId() == currentFabElem.getnId())
                    authorGoals.pop();

                isSuccessful = executeRecurse(currentElementInExecution, executionStack.peek().origin,
                        executionStack.peek().link, storyPath, executionStack,
                        linkIdsOfExecutedFBEs, worldAgentClone); // executeRecurse() returns false
                // when goal is not achievable, outcome FBE cannot be traced
                break;
            case FabulaElementNew.CATEGORY_ACTION:
                isSuccessful = executeRecurse(currentElementInExecution, executionStack.peek().origin,
                        executionStack.peek().link, storyPath, executionStack,
                        linkIdsOfExecutedFBEs, worldAgentClone);
                executionStack.remove(executionStackElement);
                break;
        }

        if (isSuccessful) {
            storyPlan.addAll(storyPath);
        }

        currentElementInExecution = null;

        return isSuccessful;
    }

    private boolean executeRecurse(FabulaNodeNew originFabNode, FabulaNodeNew parentOfOriginFabNode,
                                   LinkNew prevLink, List<FabulaNodeNew> storyPath,
                                   Stack<ExecutionStackElement> executionStack,
                                   List<Integer> linkIdsOfExecutedFBEs,
                                   WorldAgentNew worldAgentClone)
            throws MalformedDataException, DataMismatchException, OperationUnavailableException, MissingDataException, CloneNotSupportedException {

        List<LinkNew> linksOfCurrentFBE;
        List<LinkNew> motivateLinks;
        List<LinkNew> causesAndSubLinks;
        List<LinkNew> interruptLinks;
        List<LinkNew> enableLinks;
        List<LinkNew> tempLinks;
        Iterator<LinkNew> linksIterator;
        Iterator<ExecutionStackElement> executionStackIterator;
        LinkNew linkTemp;
        FabulaNodeNew linkedFabNode;
        FabulaNodeNew fabNodeTemp;
        FabulaElementNew fabElemTemp;
        boolean isExecuteRecurseSuccessful, hasPositiveChange, validInterruptFound;
        long seed;

        isExecuteRecurseSuccessful = true;

        // Add origin fabula node to story path
        storyPath.add(originFabNode);

        // get all links of current FBE
        linksOfCurrentFBE = DBQueriesNew.getLinksOfSourceFabElem(originFabNode.getData().getnId(),
                originFabNode.getData().getnSubId());
        linksIterator = linksOfCurrentFBE.iterator();

        // remove links of successfully executed FBEs
        while (linksIterator.hasNext()) {
            linkTemp = linksIterator.next();
            if (linkIdsOfExecutedFBEs.contains(linkTemp.getnLinkId())) {
                linksIterator.remove();
            }
        }

        // group links according to link type (motivates, causes/sub-action, enables, interrupts)
        motivateLinks = new ArrayList<>();
        causesAndSubLinks = new ArrayList<>();
        interruptLinks = new ArrayList<>();
        enableLinks = new ArrayList<>();
        tempLinks = new ArrayList<>();
        for (LinkNew linkTemp2 : linksOfCurrentFBE) {
            switch (linkTemp2.getsType()) {
                case LinkNew.TYPE_MOTIV:
                    motivateLinks.add(linkTemp2);
                    break;
                case LinkNew.TYPE_CAUSES:
                case LinkNew.TYPE_SUB:
                    causesAndSubLinks.add(linkTemp2);
                    break;
                case LinkNew.TYPE_ENABLE:
                    enableLinks.add(linkTemp2);
                    break;
                case LinkNew.TYPE_INTERRUPT:
                    interruptLinks.add(linkTemp2);
                    break;
            }
        }

        // Check what type of FBE is current FBE in context
        switch (originFabNode.getData().getsCategory()) {
            case FabulaElementNew.CATEGORY_GOAL:

                processEnableLinks(enableLinks, originFabNode, worldAgentClone, linkIdsOfExecutedFBEs);

                // Apply norms on motivate links
                applyNorms(motivateLinks);

                // Shuffle motivation links and evaluate motivate link conditions
                evaluateLinks(motivateLinks, originFabNode, worldAgentClone);

                // iterate through them
                linksIterator = motivateLinks.iterator();
                hasPositiveChange = false; // todo is this necessary?
                while (linksIterator.hasNext() && executionStack.peek().fabulaNode == originFabNode) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {

                        // if goal, then add to execution stack
                        // if action, continue execution
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_GOAL:
                                linkedFabNode.getData().setupExecutionAgents();
                                if (executionStack.peek().fabulaNode == currentElementInExecution)
                                    currentElementInExecution.getData().setSubExecutionElement(linkedFabNode.getData());
                                executionStack.add(new ExecutionStackElement(linkedFabNode, linkTemp, originFabNode));
                                hasPositiveChange = true;
                                break;
                            case FabulaElementNew.CATEGORY_ACTION:
                                hasPositiveChange = executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element " + linkedFabNode.getData().getsCategory() +
                                        ":" + linkedFabNode.getData().getsLabel() + " in current link " + linkTemp.getnLinkId() + " was encountered.");
                        }
                    }
                }

                if (!executionStack.isEmpty() && executionStack.peek().fabulaNode == originFabNode && !hasPositiveChange) {
                    isExecuteRecurseSuccessful = false;
                }

                break;
            case FabulaElementNew.CATEGORY_ACTION:

                processEnableLinks(enableLinks, originFabNode, worldAgentClone, linkIdsOfExecutedFBEs);

                validInterruptFound = false;
                if (!interruptLinks.isEmpty()) {
                    linksIterator = interruptLinks.iterator();
                    linkedFabNode = null;
                    linkTemp = null;
                    while (linksIterator.hasNext() && !validInterruptFound) {
                        linkTemp = linksIterator.next();
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        if (linkedFabNode != null) {
                            validInterruptFound = true;
                        }
                    }

                    if (validInterruptFound) {
                        if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                                storyPath, linkIdsOfExecutedFBEs)) {

                            // Execute only when an interrupt is executed
                            originFabNode.getData().setupExecutionAgents();
                            if (executionStack.peek().fabulaNode == currentElementInExecution)
                                currentElementInExecution.getData().setSubExecutionElement(originFabNode.getData());
                            executionStack.add(new ExecutionStackElement(originFabNode, prevLink, parentOfOriginFabNode));

                            // If event, continue execution
                            // Otherwise, throw exception
                            switch (linkedFabNode.getData().getsCategory()) {
                                case FabulaElementNew.CATEGORY_EVENT:
                                    executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                            executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                    break;
                                default:
                                    throw new DataMismatchException("Unexpected fabula element " + linkedFabNode.getData().getsCategory() +
                                            ":" + linkedFabNode.getData().getsLabel() + " in current link " + linkTemp.getnLinkId() + " was encountered.");
                            }
                        }
                    }
                }

                // If no interrupt was executed, proceed here
                if (!validInterruptFound) {
                    // Evaluate causality and sub action link conditions
                    evaluateLinks(causesAndSubLinks, originFabNode, worldAgentClone);

                    // iterate through them
                    linksIterator = causesAndSubLinks.iterator();
                    while (linksIterator.hasNext()) {

                        // Get linked FBE
                        linkTemp = linksIterator.next();
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                                storyPath, linkIdsOfExecutedFBEs)) {

                            // if outcome, then remove associated goal from execution stack
                            // if event, internal element or perception, continue execution
                            switch (linkedFabNode.getData().getsCategory()) {
                                case FabulaElementNew.CATEGORY_OUTCOME:
                                    updateExecutionStackOnOutcome(linkedFabNode, executionStack);
                                    executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                            executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                    break;
                                case FabulaElementNew.CATEGORY_EVENT:
                                case FabulaElementNew.CATEGORY_INTERN:
                                case FabulaElementNew.CATEGORY_PERCEPT:
                                case FabulaElementNew.CATEGORY_ACTION:
                                    executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                            executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                    break;
                                default:
                                    throw new DataMismatchException("Unexpected fabula element " + linkedFabNode.getData().getsCategory() +
                                            ":" + linkedFabNode.getData().getsLabel() + " in current link " + linkTemp.getnLinkId() + " was encountered.");
                            }
                        }
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_INTERN:
            case FabulaElementNew.CATEGORY_PERCEPT:

                processEnableLinks(enableLinks, originFabNode, worldAgentClone, linkIdsOfExecutedFBEs);

                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks, originFabNode, worldAgentClone);

                // Iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                        originFabNode.addDestination(linkedFabNode, linkTemp);
                        linkedFabNode.addSource(originFabNode, linkTemp);
                    }

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {
                        // if outcome, then remove associated goal from execution stack
                        // if goal, add goal to execution stack
                        // if internal element, continue execution
                        // otherwise, throw new exception
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_GOAL:
                                linkedFabNode.getData().setupExecutionAgents();
                                executionStack.add(new ExecutionStackElement(linkedFabNode, linkTemp, originFabNode));
                                break;
                            case FabulaElementNew.CATEGORY_OUTCOME:
                                updateExecutionStackOnOutcome(linkedFabNode, executionStack);
                                executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            case FabulaElementNew.CATEGORY_INTERN:
                                executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element " + linkedFabNode.getData().getsCategory() +
                                        ":" + linkedFabNode.getData().getsLabel() + " in current link " + linkTemp.getnLinkId() + " was encountered.");
                        }
                    }
                }

                // Apply norms on motivate links
                applyNorms(motivateLinks);

                // Evaluate motivation link conditions
                evaluateLinks(motivateLinks, originFabNode, worldAgentClone);

                // Iterate through them
                linksIterator = motivateLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                        originFabNode.addDestination(linkedFabNode, linkTemp);
                        linkedFabNode.addSource(originFabNode, linkTemp);
                    }

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {
                        // if action, continue execution
                        // otherwise, throw new exception
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_ACTION:
                                executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                        }
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_EVENT:

                processEnableLinks(enableLinks, originFabNode, worldAgentClone, linkIdsOfExecutedFBEs);

                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks, originFabNode, worldAgentClone);

                // iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                        originFabNode.addDestination(linkedFabNode, linkTemp);
                        linkedFabNode.addSource(originFabNode, linkTemp);
                    }

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {

                        // if event or perception, continue execution
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_EVENT:
                            case FabulaElementNew.CATEGORY_PERCEPT:
                                executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                        }
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_OUTCOME:

                processEnableLinks(enableLinks, originFabNode, worldAgentClone, linkIdsOfExecutedFBEs);

                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks, originFabNode, worldAgentClone);

                // Iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
                        linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                        originFabNode.addDestination(linkedFabNode, linkTemp);
                        linkedFabNode.addSource(originFabNode, linkTemp);
                    }

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {

                        // if internal element, continue execution
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_GOAL:
                                linkedFabNode.getData().setupExecutionAgents();
                                executionStack.add(new ExecutionStackElement(linkedFabNode, linkTemp, originFabNode));
                                break;
                            case FabulaElementNew.CATEGORY_INTERN:
                                executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                        }
                    }
                }
        }

        //* when should a link be valid? what are the nature of link preconditions?

        return isExecuteRecurseSuccessful;
    }

    private void processEnableLinks(List<LinkNew> enableLinks, FabulaNodeNew originFabNode,
                                    WorldAgentNew worldAgentClone, List<Integer> linkIdsOfExecutedFBEs)
            throws DataMismatchException, MalformedDataException, CloneNotSupportedException, MissingDataException {
        long seed;
        Iterator<LinkNew> linksIterator;
        LinkNew linkTemp;
        FabulaNodeNew linkedFabNode;
        FabulaElementNew fabElemTemp;
        boolean isNewFabElem;

        /* Processing of Enable Links */
        seed = System.nanoTime();
        Collections.shuffle(enableLinks, new Random(seed));
        linksIterator = enableLinks.iterator();
        while (linksIterator.hasNext()) {
            isNewFabElem = false;
            linkTemp = linksIterator.next();
            linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
            if (linkedFabNode == null) {
                isNewFabElem = true;
                linkedFabNode = FabulaNodeNew.getFabulaNode(DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id()));
                originFabNode.addDestination(linkedFabNode, linkTemp);
                linkedFabNode.addSource(originFabNode, linkTemp);
            }

            linkedFabNode.backupData();
            fabElemTemp = linkedFabNode.getData();

            linkParamValuesDownward(originFabNode.getData(), fabElemTemp, linkTemp.getsParamDependencies(), worldAgentClone);
            unlinkParams(originFabNode.getData());
            // commented on 8-27, 11:29 pm
            // linkIdsOfExecutedFBEs.add(linkTemp.getnLinkId());

            if (!worldAgentClone.checkPreconditions(fabElemTemp, linkTemp.getsPreconditions(), null, new HashMap<String, Object>()).second) {
                if (isNewFabElem) {
                    linkedFabNode.removeFromMasterList();
                    originFabNode.removeDestination(linkedFabNode, linkTemp);
                }
                else {
                    linkedFabNode.restoreData();
                }
            }
        }
    }

    private void applyNorms(List<LinkNew> motivateLinks) throws MalformedDataException, CloneNotSupportedException {
        List<LinkNew> motivateList = new ArrayList<>();
        List<LinkNew> tempLinks;
        List<Norm> norms;
        int nDestinationNode;
        String sOrder;
        String[] sParsedOrder;
        List<Integer> nFabElemIds;
        int nNewPosition, nOrigPosition;
        LinkNew tempLink;
        LinkNew cloneLink;
        long seed;
        int nTemp;

        norms = DBQueriesNew.getNorms();

        // Shuffle links
        seed = System.nanoTime();
        Collections.shuffle(motivateLinks, new Random(seed));

        nFabElemIds = new ArrayList<>();
        tempLinks = new ArrayList<>();
        for (LinkNew tempLink2 : motivateLinks) {
            if (tempLink2.getnPriority() == 1) {
                tempLinks.add(0, tempLink2);
                nFabElemIds.add(0, tempLink2.getnFb2Id());
            }
            else {
                tempLinks.add(tempLink2);
                nFabElemIds.add(tempLink2.getnFb2Id());
            }
        }

        motivateList.addAll(tempLinks);

        for (LinkNew motivateLink : tempLinks) {
            nDestinationNode = motivateLink.getnFb2Id();
            for (Norm norm : norms) {
                if (norm.getnFabElemId() == nDestinationNode) {
                    if ((sOrder = norm.getsOrder()) != null) {
                        sParsedOrder = sOrder.split(":");
                        if (sParsedOrder.length < 2)
                            throw new MalformedDataException("Problem encountered when parsing order " +
                                    "of norm with id = " + norm.getnId());

                        nOrigPosition = nFabElemIds.indexOf(nDestinationNode);
                        nNewPosition = -1;

                        switch (sParsedOrder[0]) {
                            case Norm.ORDER_BEFORE + "":
                                nNewPosition = nFabElemIds.indexOf(Integer.parseInt(sParsedOrder[1]));
                                break;
                            case Norm.ORDER_AFTER + "":
                                nNewPosition = nFabElemIds.indexOf(Integer.parseInt(sParsedOrder[1])) + 1;
                                break;
                            default:
                                // todo handle this default
                                break;
                        }

                        if (nNewPosition != -1) { // todo unnecessary when default is throwing an exception
                            tempLink = motivateList.get(nOrigPosition);
                            cloneLink = tempLink.clone();
                            cloneLink.getsPreconditions().addAll(norm.getsPreconditions());
                            cloneLink.getsParamDependencies().putAll(norm.getsParameters());
                            cloneLink.lock();
                            motivateList.add(nNewPosition, cloneLink);

                            nTemp = nFabElemIds.get(nOrigPosition);
                            nFabElemIds.add(nNewPosition, nTemp);

                            if (!tempLink.isLocked()) {
                                motivateList.remove(tempLink);
                                if (nNewPosition < nOrigPosition)
                                    nFabElemIds.remove(nOrigPosition + 1);
                                else
                                    nFabElemIds.remove(nOrigPosition);
                            }
                        }
                    }
                    else {
                        nOrigPosition = nFabElemIds.indexOf(nDestinationNode);
                        tempLink = motivateList.get(nOrigPosition);

                        if (tempLink.isLocked()) {
                            cloneLink = tempLink.clone();
                            cloneLink.getsPreconditions().addAll(norm.getsPreconditions());
                            cloneLink.getsParamDependencies().putAll(norm.getsParameters());
                            cloneLink.lock();
                            motivateList.add(nOrigPosition, cloneLink);
                            nTemp = nFabElemIds.get(nOrigPosition);
                            nFabElemIds.add(nOrigPosition, nTemp);
                        }
                        else {
                            tempLink.getsPreconditions().addAll(norm.getsPreconditions());
                            tempLink.getsParamDependencies().putAll(norm.getsParameters());
                            tempLink.lock();
                        }
                    }
                }
            }

        }
        motivateLinks.clear();
        motivateLinks.addAll(motivateList);
    }

    private void updateExecutionStackOnOutcome(FabulaNodeNew linkedFabNode, Stack<ExecutionStackElement> executionStack) {
        Iterator<ExecutionStackElement> executionStackIterator;
        boolean isFound;
        FabulaNodeNew fabNodeTemp;
        FabulaElementNew fabElemTemp;
        ParameterValueNew parameterValue;

        executionStackIterator = executionStack.iterator();
        isFound = false;
        while (executionStackIterator.hasNext() && !isFound) {
            fabNodeTemp = executionStackIterator.next().fabulaNode;
            fabElemTemp = fabNodeTemp.getData();
            if (fabElemTemp.getnConceptId() == linkedFabNode.getData().getnConceptId()) {
                parameterValue = linkedFabNode.getData().getParamValues().get(FabulaElementNew.PARAMS_AGENT);
                if (parameterValue.getData() instanceof CandidateCharacterIds) {
                    fabElemTemp.getExecutionAgents().removeAll(((CandidateCharacterIds) parameterValue.getData()).getCharacterIds());
                }
                else if (parameterValue.getData() instanceof List) {
                    fabElemTemp.getExecutionAgents().removeAll((List)parameterValue.getData());
                }
                else {
                    fabElemTemp.getExecutionAgents().remove(parameterValue.getData());
                }

                if (fabElemTemp.getExecutionAgents().isEmpty())
                    executionStackIterator.remove();

                isFound = true;
            }
        }
    }

    private void evaluateLinks(List<LinkNew> links, FabulaNodeNew originFabNode, WorldAgentNew worldAgentClone) throws DataMismatchException, MalformedDataException, CloneNotSupportedException, MissingDataException {
        Iterator<LinkNew> linksIterator;
        LinkNew linkTemp;
        List<LinkNew> tempLinks = new ArrayList<>();
        FabulaNodeNew linkedFabNode;
        FabulaElementNew fabElemTemp;
        boolean isNewFabElem;

        linksIterator = links.iterator();
        while (linksIterator.hasNext()) {
            isNewFabElem = false;
            linkTemp = linksIterator.next();
            linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id(), linkTemp.getnSub2Id());
            if (linkedFabNode == null) {
                isNewFabElem = true;
                linkedFabNode = FabulaNodeNew.getFabulaNode(DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id(), linkTemp.getnSub2Id()));
                originFabNode.addDestination(linkedFabNode, linkTemp);
                linkedFabNode.addSource(originFabNode, linkTemp);
            }

            linkedFabNode.backupData();
            fabElemTemp = linkedFabNode.getData();

            linkParamValuesDownward(originFabNode.getData(), fabElemTemp, linkTemp.getsParamDependencies(), worldAgentClone);
            initializeRequiredParameters(fabElemTemp);
            unlinkParams(originFabNode.getData());

            if (worldAgentClone.checkPreconditions(fabElemTemp, linkTemp.getsPreconditions(), null, new HashMap<String, Object>()).second) {
                tempLinks.add(linkTemp);
            }
            else {
                if (isNewFabElem) {
                    linkedFabNode.removeFromMasterList();
                    originFabNode.removeDestination(linkedFabNode, linkTemp);
                }
                else {
                    linkedFabNode.restoreData();
                }
            }
        }
        links.clear();
        links.addAll(tempLinks);
    }

    private boolean executeFabulaElement(FabulaNodeNew fabNodeTemp, LinkNew linkTemp, FabulaNodeNew originFabNode,
                                         WorldAgentNew worldAgentClone, List<FabulaNodeNew> storyPath,
                                         List<Integer> linkIdsOfExecutedFBEs)
            throws MalformedDataException, DataMismatchException, OperationUnavailableException, MissingDataException, CloneNotSupportedException {

        FabulaElementNew fabElemTemp;
        FabulaElementNew additionalFabElem;
        HashMap<String, Object> sUnsatisfiedConditions, sTotalPreconditions;
        List<Pair<FabulaNodeNew, LinkNew>> additionalNodesAndLinks;
        boolean isAllTrue, isExecutable, isSuccessful;
        CandidateCharacterIds candidateCharacterIds;

        fabElemTemp = fabNodeTemp.getData();
        isSuccessful = false;

        System.out.println("[DEBUG]: fabula element being executed = " + fabElemTemp.toString());

        // Initialize the required Collections for the succeeding operations
        sUnsatisfiedConditions = new HashMap<>();
        sTotalPreconditions = new HashMap<>();
        additionalNodesAndLinks = new ArrayList<>();

        // Check if current FBE preconditions are all met
        isExecutable = false;
        isAllTrue = true;
        if (!fabElemTemp.getsPreconditions().isEmpty()) {
            isAllTrue = worldAgentClone.checkPreconditions(fabElemTemp, fabElemTemp.getsPreconditions(),
                    sUnsatisfiedConditions, sTotalPreconditions).first;
            if (!isAllTrue) {
                additionalNodesAndLinks.clear();
                isExecutable = checkForPrerequisites(sTotalPreconditions, sUnsatisfiedConditions,
                        fabNodeTemp, originFabNode, additionalNodesAndLinks, storyPath); // todo how about making this a recursive call, checking the conditions of the prerequisites themselves and doing the same operation
            }
        }

        if (isAllTrue || isExecutable) { // todo add events for conditions that are false
            for (Pair<FabulaNodeNew, LinkNew> additionalNodeAndLink : additionalNodesAndLinks) {
                // Add fabula nodes to story path and realize postconditions

                additionalFabElem = additionalNodeAndLink.first.getData();

                linkParamValuesDownward(fabElemTemp, additionalFabElem, additionalNodeAndLink.second.getsParamDependencies(),
                        worldAgentClone);
                unlinkParams(additionalFabElem);

                if (!additionalFabElem.getsPostconditions().isEmpty()) {
                    worldAgentClone.realizeConditions(additionalFabElem, additionalFabElem.getsPostconditions()); // todo how about the preconditions of these additional nodes
                }
                fabElemTemp.getsPostconditions().addAll(additionalNodeAndLink.second.getsPostconditions());

                storyPath.add(additionalNodeAndLink.first);
                linkIdsOfExecutedFBEs.add(additionalNodeAndLink.second.getnLinkId());
            }

            fabElemTemp.getsPostconditions().addAll(linkTemp.getsPostconditions());

            if (!fabElemTemp.getsPostconditions().isEmpty()) {
                worldAgentClone.realizeConditions(fabElemTemp, fabElemTemp.getsPostconditions());
            }

            if (fabElemTemp.getParamValues().get(FabulaElementNew.PARAMS_AGENT).getData() instanceof CandidateCharacterIds) {
                candidateCharacterIds = (CandidateCharacterIds)fabElemTemp.getParamValues().get(FabulaElementNew.PARAMS_AGENT).getData();
                for (CharacterIdentifierNew charId : candidateCharacterIds.getCharacterIds()) {
                    worldAgentClone.updateCharacterCurrentGoalHistory(fabElemTemp.getnId(), charId.getnCharacterId());
                }
            }

            linkIdsOfExecutedFBEs.add(linkTemp.getnLinkId());
            isSuccessful = true;
        }

        System.out.println("[DEBUG]: result is \"" + isSuccessful + "\"");

        return isSuccessful;
    }

    /**
     * Replaces the ParameterValue objects in the current fabula element shared with other fabula
     * elements with a new ParameterValue instance but copying the same data contained in the
     * old ParameterValue object.
     *
     * @param fabElemTemp
     */
    private void unlinkParams(FabulaElementNew fabElemTemp) throws CloneNotSupportedException {
        HashMap<String, ParameterValueNew> paramValueMap;
        ParameterValueNew paramValTemp;
        Object data;

        paramValueMap = fabElemTemp.getParamValues();
        for (Map.Entry<String, ParameterValueNew> entry : paramValueMap.entrySet()) {
            paramValTemp = new ParameterValueNew();
            data = entry.getValue().getData();
            if (data instanceof CandidateCharacterIds) {
                paramValTemp.setData(((CandidateCharacterIds) data).clone());
            }
            else {
                paramValTemp.setData(data);
            }
            entry.setValue(paramValTemp);
        }
    }

    private Stack<AuthorGoal> compileAuthorGoals(ContextGoals contextGoals, ConflictGoals conflictGoals,
                                                 ResolutionGoal resolutionGoal) {

        Stack<AuthorGoal> authorGoals = new Stack<>();

        authorGoals.add(new AuthorGoal(resolutionGoal.getnGoal(), resolutionGoal.getnGoalSub()));
        authorGoals.add(new AuthorGoal(conflictGoals.getnCounterGoal(), conflictGoals.getnCounterSub()));
        authorGoals.add(new AuthorGoal(conflictGoals.getnConflictGoal(), conflictGoals.getnConflictSub()));

        if (contextGoals.getnSearchDirection() == 1) {
            authorGoals.add(new AuthorGoal(contextGoals.getnMainGoal(), contextGoals.getnMainGoalSubId()));
            authorGoals.add(new AuthorGoal(contextGoals.getnSupportingGoal(), contextGoals.getnSupportGoalSubId()));
        } else {
            authorGoals.add(new AuthorGoal(contextGoals.getnSupportingGoal(), contextGoals.getnSupportGoalSubId()));
            authorGoals.add(new AuthorGoal(contextGoals.getnMainGoal(), contextGoals.getnMainGoalSubId()));
        }

        return authorGoals;
    }

    private Stack<FabulaNodeNew> searchAndLinkStartingGoals(FabulaNodeNew fabNodeContextGoal,
                                                            WorldAgentNew worldAgent)
            throws MalformedDataException, DataMismatchException {

        Stack<FabulaNodeNew> possibleStartingGoals;
        List<Integer> conceptsDoneAtLocation;
        int nLocation;

        possibleStartingGoals = new Stack<>();

        // Search for concepts related to background
        nLocation = worldAgent.getSetting().getnLocationConceptId();
        conceptsDoneAtLocation = DBQueriesNew.getRelatedLeftHandConcepts(nLocation, SemanticRelation.DONE_AT);

        searchAndLinkStartingGoalsRecurse(fabNodeContextGoal, possibleStartingGoals, new ArrayList<Integer>(),
                false, false, null, FabulaElementNew.PARAMS_AGENT, conceptsDoneAtLocation, worldAgent);

        return possibleStartingGoals;
    }

    private boolean
    searchAndLinkStartingGoalsRecurse(FabulaNodeNew currFabNode, Stack<FabulaNodeNew> possibleStartingGoals,
                                      List<Integer> nVisitedNodes, boolean isActionEncountered,
                                      boolean isLocationEncountered, FabulaNodeNew fabNodeCandidate,
                                      String sParam, List<Integer> conceptsDoneAtLocation, WorldAgentNew worldAgent)
            throws MalformedDataException, DataMismatchException {

        int nLinkedNode;
        boolean isFound = true;
        String sCategory;
        HashMap<String, String> sParamDependencies;
        List<Integer> nVisitedNodesTemp;
        FabulaNodeNew passNode;
        FabulaNodeNew prevFabNodeCandidate;
        FabulaElementNew fabTemp;
        String sNewParam = null;

        for (LinkNew link : DBQueriesNew.getLinksOfDestinationFabElem(currFabNode.getData().getnId(),
                currFabNode.getData().getnSubId())) {
            nLinkedNode = link.getnFb1Id();
            isFound = false;

            // Check if the fabula node is visited already in the search path in context
            nVisitedNodesTemp = new ArrayList<>();
            nVisitedNodesTemp.addAll(nVisitedNodes);
            if (!nVisitedNodesTemp.contains(nLinkedNode)) {
                prevFabNodeCandidate = fabNodeCandidate;
                fabTemp = DBQueriesNew.getFabulaElementById(nLinkedNode, link.getnSub1Id());
                fabTemp = FabulaNodeNew.getFabulaNode(fabTemp).getData();
                sCategory = fabTemp.getsCategory();
                nVisitedNodesTemp.add(nLinkedNode);

                // Parameter dependencies in the current fabula link
                sParamDependencies = link.getsParamDependencies();
                if (sParam != null)
                    sNewParam = sParamDependencies.get(sParam);

                try {
                    linkParamValuesUpward(fabTemp, currFabNode.getData(), sParamDependencies);
                } catch (DataMismatchException e) {
                    e.printStackTrace();
                    continue;
                }

                initializeRequiredParameters(fabTemp);

                // todo change algorithm on checking location compatibility in context
                if (isLocationEncountered || conceptsDoneAtLocation.contains(fabTemp.getnConceptId()))
                    isLocationEncountered = true;

                // Evaluate fabula element based on category
                switch (sCategory) {
                    case FabulaElementNew.CATEGORY_GOAL:
                        passNode = FabulaNodeNew.getFabulaNode(fabTemp);
                        currFabNode.addSource(passNode, link);
                        passNode.addDestination(currFabNode, link);
                        if (isActionEncountered && isLocationEncountered) {
                            fabNodeCandidate = passNode;
                        }
                        if (searchAndLinkStartingGoalsRecurse(passNode, possibleStartingGoals,
                                nVisitedNodesTemp, isActionEncountered, isLocationEncountered,
                                fabNodeCandidate, sNewParam, conceptsDoneAtLocation, worldAgent)) {

                            // Make sure the candidate starting goal has the same agent parameter with the root node
                            if (sNewParam != null && sNewParam.equals("agent"))
                                if (fabNodeCandidate != null && !containsSameFabulaElem(possibleStartingGoals, fabNodeCandidate)) {

                                    fabTemp.setIsBeginning(true); // todo remove if unnecessary
                                    if (checkIfStartingGoalIsValid(fabTemp, worldAgent)) {
                                        possibleStartingGoals.add(fabNodeCandidate);
                                    }
                                }
                        }
                        break;
                    case FabulaElementNew.CATEGORY_ACTION:
                        if (!isActionEncountered) {
                            passNode = FabulaNodeNew.getFabulaNode(fabTemp);
                            currFabNode.addSource(passNode, link);
                            passNode.addDestination(currFabNode, link);
                            searchAndLinkStartingGoalsRecurse(passNode, possibleStartingGoals,
                                    nVisitedNodesTemp, true, isLocationEncountered, fabNodeCandidate, sNewParam,
                                    conceptsDoneAtLocation, worldAgent);
                        }
                        break;
                    default:
                        if (fabNodeCandidate != null) {
                            isFound = true;
                        } else {
                            passNode = FabulaNodeNew.getFabulaNode(fabTemp);
                            currFabNode.addSource(passNode, link);
                            passNode.addDestination(currFabNode, link);
                            searchAndLinkStartingGoalsRecurse(passNode, possibleStartingGoals,
                                    nVisitedNodesTemp, isActionEncountered, isLocationEncountered, null,
                                    sNewParam, conceptsDoneAtLocation, worldAgent);
                        }
                        break;
                }
                fabNodeCandidate = prevFabNodeCandidate;
            }
        }

        return isFound;
    }

    private boolean checkIfStartingGoalIsValid(FabulaElementNew fabTemp, WorldAgentNew worldAgent)
            throws DataMismatchException, MalformedDataException {
        boolean isGoalValid;
        boolean isValidA;
        List<String> sPreconditions;
        List<String> sParts;
        List<CharacterIdentifierNew> candidateCharacterIds;
        Iterator<CharacterIdentifierNew> charIdIterator;
        CharacterIdentifierNew charId;

        sPreconditions = fabTemp.getsPreconditions();
        if (fabTemp.getParamValues().get(FabulaElementNew.PARAMS_AGENT).getData() instanceof CandidateCharacterIds) {
            candidateCharacterIds = ((CandidateCharacterIds) (fabTemp.getParamValues().get(FabulaElementNew.PARAMS_AGENT)).getData()).getCharacterIds();
        } else {
            throw new DataMismatchException("No character was assigned to possible starting goal");
        }

        isValidA = true;
        isGoalValid = false;
        charIdIterator = candidateCharacterIds.iterator();
        while (charIdIterator.hasNext()) {
            charId = charIdIterator.next();
            if (sPreconditions != null) {
                for (String sPrecondition : sPreconditions) {
                    try {
                        sParts = Arrays.asList(sPrecondition.split(":"));
                        if (sParts.get(0).equals(FabulaElementNew.PARAMS_AGENT) && (sParts.get(1).equals("gender")
                                || sParts.get(2).equals("trait"))) {
                            isValidA = isValidA && worldAgent.checkCondition(charId, sParts.get(1),
                                    sParts.get(2), FabulaElementNew.PARAMS_AGENT);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        throw new MalformedDataException("Error in parsing " +
                                "Fabula Element preconditions.");
                    }
                }
            }
            if (!isValidA) {
                charIdIterator.remove();
            }
            isGoalValid = isGoalValid || isValidA;
        }

        return isGoalValid;
    }

    private boolean linkContextGoals(int nDirection, ContextGoals contextGoals, CharacterIdentifierNew mainCharId,
                                     List<CharacterIdentifierNew> supportingCharsId)
            throws DataMismatchException, MalformedDataException {

        int nStart, nStartSub;
        int nEnd, nEndSub;
        boolean isFound;
        FabulaElementNew fabulaElementStart, fabulaElementEnd;
        FabulaNodeNew fabulaNodeStart, fabulaNodeEnd;
        List<Integer> nVisitedFabElems = new ArrayList<>();
        CandidateCharacterIds candidateCharsTemp;
        int nMainGoal, nSupportingGoal;

        nMainGoal = contextGoals.getnMainGoal();
        nSupportingGoal = contextGoals.getnSupportingGoal();
        if (nDirection == 1) {
            nStart = nMainGoal;
            nStartSub = contextGoals.getnMainGoalSubId();
            nEnd = nSupportingGoal;
            nEndSub = contextGoals.getnSupportGoalSubId();
        } else if (nDirection == 0) {
            nStart = nSupportingGoal;
            nStartSub = contextGoals.getnSupportGoalSubId();
            nEnd = nMainGoal;
            nEndSub = contextGoals.getnMainGoalSubId();
        } else {
            throw new DataMismatchException("Invalid search direction value of chosen context goals.");
        }

        fabulaElementStart = DBQueriesNew.getFabulaElementById(nStart, nStartSub);
        fabulaElementEnd = DBQueriesNew.getFabulaElementById(nEnd, nEndSub);
        fabulaElementStart.getParamValues().put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
        fabulaElementEnd.getParamValues().put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
        if (nDirection == 1) {
            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidate(mainCharId);
            fabulaElementStart.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);

            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidates(supportingCharsId);
            fabulaElementEnd.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);
        } else {
            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidate(mainCharId);
            fabulaElementEnd.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);

            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidates(supportingCharsId);
            fabulaElementStart.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);
        }

        fabulaNodeStart = FabulaNodeNew.getFabulaNode(fabulaElementStart);
        fabulaNodeEnd = FabulaNodeNew.getFabulaNode(fabulaElementEnd);

        isFound = linkContextGoalsRecurse(fabulaNodeStart, fabulaNodeEnd, nVisitedFabElems);

        // todo not sure if this is needed
        if (isFound) {
            if (nDirection == 1) {
                contextGoals.setMainGoalNode(fabulaNodeStart);
                contextGoals.setSupportingGoalNode(fabulaNodeEnd);
            } else {
                contextGoals.setMainGoalNode(fabulaNodeEnd);
                contextGoals.setSupportingGoalNode(fabulaNodeStart);
            }
        }

        return isFound;
    }

    private boolean linkContextGoalsRecurse(FabulaNodeNew previousNode, FabulaNodeNew fabulaNodeEnd,
                                            List<Integer> nVisitedFabElems) throws MalformedDataException {

        FabulaElementNew fabulaElement;
        FabulaNodeNew passNode;
        List<Integer> nVisitedFabElemsTemp;
        HashMap<String, String> sParamDependencies;
        List<LinkNew> relatedLinks;
        Iterator<LinkNew> relatedLinksIterator;
        LinkNew link;
        int nLinkedFabElem, nEnd;
        boolean isPathFound = false;

        relatedLinks = DBQueriesNew.getLinksOfDestinationFabElem(previousNode.getData().getnId(), previousNode.getData().getnSubId());
        relatedLinksIterator = relatedLinks.iterator();
        nEnd = fabulaNodeEnd.getData().getnId();

        while (relatedLinksIterator.hasNext() && !isPathFound) {
            link = relatedLinksIterator.next();
            nLinkedFabElem = link.getnFb1Id();
            nVisitedFabElemsTemp = new ArrayList<>();
            nVisitedFabElemsTemp.addAll(nVisitedFabElems);

            if (!nVisitedFabElems.contains(nLinkedFabElem)) {
                if (nLinkedFabElem == nEnd) {
                    passNode = fabulaNodeEnd;
                    fabulaElement = passNode.getData();
                } else {
                    fabulaElement = DBQueriesNew.getFabulaElementById(nLinkedFabElem, link.getnSub1Id());
                    passNode = FabulaNodeNew.getFabulaNode(fabulaElement);
                }

                nVisitedFabElemsTemp.add(nLinkedFabElem);
                sParamDependencies = link.getsParamDependencies();

                try {
                    linkParamValuesUpward(fabulaElement, previousNode.getData(), sParamDependencies);
                } catch (DataMismatchException e) {
                    e.printStackTrace();
                    continue;
                }
                initializeRequiredParameters(fabulaElement);

                previousNode.addSource(passNode, link);
                passNode.addDestination(previousNode, link);

                isPathFound = passNode == fabulaNodeEnd || linkContextGoalsRecurse(passNode,
                        fabulaNodeEnd, nVisitedFabElemsTemp);
            }
        }

        return isPathFound;
    }

    // todo is this really upward only, consider changes below in code
    private void linkParamValuesUpward(FabulaElementNew currFabulaElement, FabulaElementNew prevFabulaElement,
                                       HashMap<String, String> sParamDependencies) throws DataMismatchException {

        String key;
        String sDestination;
        Iterator iterator = sParamDependencies.entrySet().iterator();
        HashMap<String, ParameterValueNew> prevParamValues = prevFabulaElement.getParamValues();
        Object objTemp;
        ParameterValueNew parameterValueTemp;

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            key = (String) pair.getKey();

            switch (key) {
                case FabulaElementNew.PARAMS_AGENT:
                case FabulaElementNew.PARAMS_PATIENT:
                case FabulaElementNew.PARAMS_TARGET:
                case FabulaElementNew.PARAMS_HIDDEN:
                case FabulaElementNew.PARAMS_DIRECTION:
                    sDestination = (String) pair.getValue();
                    if (!prevParamValues.containsKey(sDestination)) {
                        prevParamValues.put(sDestination, new ParameterValueNew());
                    }
                    if (!currFabulaElement.getParamValues().containsKey(key)) {
                        currFabulaElement.getParamValues().put(key, prevParamValues.get(sDestination));
                    }
                    else {
                        objTemp = currFabulaElement.getParamValues().get(key).getData();
                        parameterValueTemp = prevParamValues.get(sDestination);
                        if (objTemp != null && parameterValueTemp.getData() == null) {
                            parameterValueTemp.setData(objTemp);
                        }
                        currFabulaElement.getParamValues().put(key, parameterValueTemp);
                    }
                    break;
            }
        }
        System.out.println("Breakpoint");
    }

    private void linkParamValuesDownward(FabulaElementNew sourceFabulaElement, FabulaElementNew destFabulaElement,
                                         HashMap<String, String> sParamDependencies, WorldAgentNew worldAgentClone)
            throws DataMismatchException, MalformedDataException, CloneNotSupportedException, MissingDataException {

        String key, key2;
        String[] sParts;
        String sDestination;
        Iterator iterator;
        HashMap<String, ParameterValueNew> sourceParamValues;
        HashMap<String, ParameterValueNew> destParamValues;
        HashMap<String, ParameterValueNew> sourceSubParamValues;
        FabulaElementNew fabElemTemp;

        iterator = sParamDependencies.entrySet().iterator();
        destParamValues = destFabulaElement.getParamValues();
        sourceParamValues = sourceFabulaElement.getParamValues();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            key = (String) pair.getKey();

            if (key.matches("[a-z_]+\\.[a-z_]+")) {
                sParts = key.split("\\.");
                key = sParts[0];
                key2 = sParts[1];

                switch (key) {
                    case FabulaElementNew.PARAMS_AGENT:
                    case FabulaElementNew.PARAMS_PATIENT:
                    case FabulaElementNew.PARAMS_TARGET:
                    case FabulaElementNew.PARAMS_HIDDEN:
                    case FabulaElementNew.PARAMS_DIRECTION:
                        sDestination = (String) pair.getValue();
                        if (sourceParamValues.get(key) != null && sourceParamValues.get(key).getData() instanceof FabulaElementNew) {
                            sourceSubParamValues = ((FabulaElementNew) sourceParamValues.get(key).getData()).getParamValues();
                            if (sourceSubParamValues.containsKey(key2)) {
                                destParamValues.put(sDestination, sourceSubParamValues.get(key2));
                            }
                        }
                        else {
                            // todo what to do when link param dependency doesn't match parameter value?
                        }
                        break;
                }
            }
            else if (key.matches("%[a-z_]+")) {
                sDestination = (String) pair.getValue();
                if (!destParamValues.containsKey(sDestination)) {
                    destParamValues.put(sDestination, new ParameterValueNew());
                }
                key.replace('_', ' ');
                (destParamValues.get(sDestination)).setData(key.substring(1));
            } else if (key.matches("#this")) {
                sDestination = (String) pair.getValue();
                if (!destParamValues.containsKey(sDestination)) {
                    destParamValues.put(sDestination, new ParameterValueNew());
                }
                (destParamValues.get(sDestination)).setData(sourceFabulaElement);
            }
            else {
                switch (key) {
                    case FabulaElementNew.PARAMS_AGENT:
                    case FabulaElementNew.PARAMS_PATIENT:
                    case FabulaElementNew.PARAMS_TARGET:
                    case FabulaElementNew.PARAMS_HIDDEN:
                    case FabulaElementNew.PARAMS_DIRECTION:
                        sDestination = (String) pair.getValue();
                        if (destParamValues.get(sDestination) == null) {
                            if (sourceParamValues.containsKey(key))
                                destParamValues.put(sDestination, sourceParamValues.get(key));
                        }
                }
            }
        }
    }


    private boolean containsSameFabulaElem(List<FabulaNodeNew> fabulaNodes, FabulaNodeNew fabulaNode) {
        boolean isFound = false;
        for (FabulaNodeNew fabulaNodeTemp : fabulaNodes) {
            if (fabulaNodeTemp.getData().getnId() == fabulaNode.getData().getnId())
                isFound = true;
        }
        return isFound;
    }
}
