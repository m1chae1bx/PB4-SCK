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
import process.helpers.FabulaNode;
import process.helpers.FabulaNodeNew;
import process.helpers.FabulaTree;
import process.helpers.Triple;

/**
 * Created by M. Bonon on 6/23/2015.
 */
public class PlotAgentNew {

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

    @Deprecated
    private boolean containsSameFabulaElem(List<FabulaNode> fabulaNodes, FabulaNode fabulaNode) {
        return false;
    }

    /**
     * Search for starting goals of the main characterId from the 'head' goal, one of the context goals.
     *
     * @param fabulaNodeHead        the fabula ID of the head, one of the context goals
     * @param fabulaElements        the whole list of fabula elements in the knowledge base
     * @param links                 the whole list of fabula links in the knowledge base
     * @param possibleStartingGoals an empty list that will store the possible starting goals
     *                              identified by the search process
     * @param nLocation             story location predetermined by the user input
     * @param characterId           the reference to the identifier to the main character instance
     * @return a fabula tree with the head goal as the root node and the
     * possible starting goals as leaves
     * @throws MalformedDataException
     */
    @Deprecated
    public FabulaTree searchAndLinkStartingGoals(FabulaNode[] fabulaNodeHead, List<FabulaElementNew> fabulaElements, List<LinkNew> links,
                                                 List<FabulaNode> possibleStartingGoals, int nLocation, CharacterIdentifierNew characterId,
                                                 WorldAgentNew worldAgent) throws MalformedDataException, CloneNotSupportedException {
        FabulaTree fabulaTree;
        FabulaNode root;
        List<Integer> conceptsDoneAtLocation;

        // Initialize fabula tree
        fabulaTree = new FabulaTree(fabulaNodeHead[0]);
        root = fabulaTree.getRoot();
        fabulaTree.setIsInverted(true);

        // Search for concepts related to background
        conceptsDoneAtLocation = DBQueriesNew.getRelatedLeftHandConcepts(nLocation, SemanticRelation.DONE_AT);

        // Call recursive method to search for fabula through the fabula network via the links
        // and at the same time build the fabula tree
        searchForStartingGoalsRec(fabulaNodeHead[0].getData().getnId(), fabulaElements, links,
                possibleStartingGoals, new ArrayList<Integer>(), false,
                false, null, "agent", root, conceptsDoneAtLocation, characterId, worldAgent);

        return fabulaTree;
    }

    /**
     * Recursive method for searching starting goals of main characterId
     *
     * @param nFabulaId              the fabula ID of current fabula
     * @param fabulaElements         the whole list of fabula elements in the knowledge base
     * @param links                  the whole list of fabula links in the knowledge base
     * @param possibleStartingGoals  initially, an empty list that will store the possible starting goals
     * @param visitedNodes           stores the nodes containing fabula elements which were visited
     *                               while traversing a particular path in the network
     * @param isActionEncountered    // todo
     * @param isLocationEncountered  // todo
     * @param fabNodeCandidate       // todo
     * @param sParam                 // todo
     * @param currNode               // todo
     * @param conceptsDoneAtLocation // todo
     * @param characterId            // todo
     */
    @Deprecated
    private boolean searchForStartingGoalsRec(int nFabulaId, List<FabulaElementNew> fabulaElements, List<LinkNew> links,
                                              List<FabulaNode> possibleStartingGoals, List<Integer> visitedNodes,
                                              boolean isActionEncountered, boolean isLocationEncountered,
                                              FabulaNode fabNodeCandidate, String sParam, FabulaNode currNode,
                                              List<Integer> conceptsDoneAtLocation, CharacterIdentifierNew characterId,
                                              WorldAgentNew worldAgent)
            throws MalformedDataException, CloneNotSupportedException {
        HashMap<String, String> sParamDependencies;
        FabulaElementNew fabTemp;
        FabulaNode passNode;
        FabulaNode prevFabNodeCandidate;
        String sCategory;
        String sNewParam = null;
        List<String> sPreconditions;
        List<String> sParts;
        List<Integer> nVisitedNodesTemp;
        CandidateCharacterIds candidateCharacterIds;
        int nLinkedNode;
        boolean isValid;
        boolean isFound = true;

        for (LinkNew link : links) {

            // Check for links connected to current fabula element in context
            if (link.getnFb2Id() == nFabulaId) {
                nLinkedNode = link.getnFb1Id();
                isFound = false;

                // Check if the fabula node is visited already in the search path in context
                nVisitedNodesTemp = new ArrayList<>();
                nVisitedNodesTemp.addAll(visitedNodes);
                if (!nVisitedNodesTemp.contains(nLinkedNode)) {
                    prevFabNodeCandidate = fabNodeCandidate;
                    fabTemp = fabulaElements.get(nLinkedNode - 1).clone();
                    sCategory = fabTemp.getsCategory();
                    nVisitedNodesTemp.add(nLinkedNode);

                    // Parameter dependencies in the current fabula link
                    sParamDependencies = link.getsParamDependencies();
                    if (sParam != null)
                        sNewParam = sParamDependencies.get(sParam);

                    try {
                        linkParameterValues(fabTemp, currNode.getData(), sParamDependencies);
                    } catch (DataMismatchException e) {
                        e.printStackTrace();
                        continue;
                    }

                    initializeRequiredParameters(fabTemp);

                    // Evaluate fabula element based on category
                    if (sCategory.equals(FabulaElementNew.CATEGORY_GOAL)) {
                        if (isLocationEncountered || conceptsDoneAtLocation.contains(fabTemp.getnConceptId()))
                            isLocationEncountered = true;
                        passNode = currNode.addChild(fabTemp, link);
                        passNode.setParent(currNode, link);
                        if (isActionEncountered && isLocationEncountered) {
                            fabNodeCandidate = passNode;
                        }
                        if (searchForStartingGoalsRec(nLinkedNode, fabulaElements, links, possibleStartingGoals,
                                nVisitedNodesTemp, isActionEncountered, isLocationEncountered,
                                fabNodeCandidate, sNewParam, passNode, conceptsDoneAtLocation, characterId, worldAgent)) {

                            // Make sure the candidate starting goal has the same agent parameter with the root node
                            if (sNewParam != null && sNewParam.equals("agent")) {
                                if (!possibleStartingGoals.contains(fabNodeCandidate)) {

                                    // Validate goal's preconditions
                                    sPreconditions = fabTemp.getsPreconditions();
                                    isValid = true;
                                    if (sPreconditions != null) {
                                        for (String sPrecondition : sPreconditions) {
                                            try {
                                                sParts = Arrays.asList(sPrecondition.split(":"));
                                                if (sParts.get(0).equals("agent") && (sParts.get(1).equals("gender")
                                                        || sParts.get(2).equals("trait"))) {
                                                    isValid = isValid && worldAgent.checkCondition(characterId,
                                                            sParts.get(1), sParts.get(2), "agent");
                                                }
                                            } catch (IndexOutOfBoundsException e) {
                                                throw new MalformedDataException("Error in parsing Fabula Element preconditions.");
                                            }
                                        }
                                    }
                                    if (isValid && fabNodeCandidate != null) {
                                        candidateCharacterIds = new CandidateCharacterIds();
                                        candidateCharacterIds.addCandidates(characterId);
                                        ((ParameterValueNew) fabNodeCandidate.getData().getParamValues().get("agent")).setData(candidateCharacterIds);
                                    }
                                    possibleStartingGoals.add(fabNodeCandidate);
                                }
                            }
                        }
                    } else if (sCategory.equals(FabulaElementNew.CATEGORY_ACTION)) {
                        if (!isActionEncountered) {
                            passNode = currNode.addChild(fabTemp, link);
                            passNode.setParent(currNode, link);
                            searchForStartingGoalsRec(nLinkedNode, fabulaElements, links, possibleStartingGoals,
                                    nVisitedNodesTemp, true, isLocationEncountered, fabNodeCandidate, sNewParam,
                                    passNode, conceptsDoneAtLocation, characterId, worldAgent);
                        }
                    } else {
                        if (fabNodeCandidate != null) {
                            isFound = true;
                        } else {
                            passNode = currNode.addChild(fabTemp, link);
                            passNode.setParent(currNode, link);
                            searchForStartingGoalsRec(nLinkedNode, fabulaElements, links, possibleStartingGoals,
                                    nVisitedNodesTemp, isActionEncountered, isLocationEncountered, null,
                                    sNewParam, passNode, conceptsDoneAtLocation, characterId, worldAgent);
                        }
                    }
                    fabNodeCandidate = prevFabNodeCandidate;
                }
            }
        }

        return isFound;
    }

    @Deprecated
    public FabulaTree searchAndLinkStartingGoals(FabulaNode[] fabulaNodeSupport, List<FabulaElementNew> fabEls,
                                                 List<LinkNew> links, List<FabulaNode> possibleStartingGoals,
                                                 int nLocation, List<CharacterIdentifierNew> characterIds,
                                                 WorldAgentNew worldAgent)
            throws MalformedDataException, CloneNotSupportedException {

        FabulaTree fabulaTree;
        FabulaNode root;
        List<Integer> conceptsDoneAtLocation;

        // Initialize Tree
        fabulaTree = new FabulaTree(fabulaNodeSupport[0]);
        root = fabulaTree.getRoot();
        fabulaTree.setIsInverted(true);

        // Search for concepts related to background
        conceptsDoneAtLocation = DBQueriesNew.getRelatedLeftHandConcepts(nLocation, SemanticRelation.DONE_AT);

        searchForStartingGoalsRec(fabulaNodeSupport[0].getData().getnId(), fabEls, links, possibleStartingGoals, new ArrayList<Integer>(), false,
                false, null, "agent", root, conceptsDoneAtLocation, characterIds, worldAgent);

        return fabulaTree;
    }

    /**
     * Recursive
     *
     * @param nNode
     * @param fabulaElements
     * @param links
     * @param possibleStartingGoals
     * @param visitedNodes
     * @param isActionEncountered
     */
    @Deprecated
    private boolean searchForStartingGoalsRec(int nNode, List<FabulaElementNew> fabulaElements, List<LinkNew> links,
                                              List<FabulaNode> possibleStartingGoals, List<Integer> visitedNodes,
                                              boolean isActionEncountered, boolean isLocationEncountered,
                                              FabulaNode fabNodeCandidate, String sParam, FabulaNode currNode,
                                              List<Integer> conceptsDoneAtLocation, List<CharacterIdentifierNew> characterIds,
                                              WorldAgentNew worldAgent)
            throws MalformedDataException, CloneNotSupportedException {
        HashMap<String, String> paramDependencies;
        FabulaElementNew fabTemp;
        FabulaNode passNode;
        FabulaNode prevFabNodeCandidate;
        String sCategory;
        String sNewParam = null;
        CharacterIdentifierNew characterId;
        int nLinkedNode;
        boolean isValidA, isValidB;
        boolean isFound = true;
        int i;

        List<String> sPreconditions;
        List<String> sParts;
        List<Integer> visitedNodesTemp;
        CandidateCharacterIds candidateCharacterIds;


        for (LinkNew link : links) {
            if (link.getnFb2Id() == nNode) {
                nLinkedNode = link.getnFb1Id();
                isFound = false;
                visitedNodesTemp = new ArrayList<>();
                visitedNodesTemp.addAll(visitedNodes);
                if (!visitedNodesTemp.contains(nLinkedNode)) {
                    prevFabNodeCandidate = fabNodeCandidate;
                    fabTemp = fabulaElements.get(nLinkedNode - 1).clone();
                    sCategory = fabTemp.getsCategory();
                    visitedNodesTemp.add(nLinkedNode);

                    paramDependencies = link.getsParamDependencies();
                    if (sParam != null)
                        sNewParam = paramDependencies.get(sParam);

                    try {
                        linkParameterValues(fabTemp, currNode.getData(), paramDependencies);
                    } catch (DataMismatchException e) {
                        e.printStackTrace();
                        continue;
                    }

                    initializeRequiredParameters(fabTemp);

                    if (sCategory.equals(FabulaElementNew.CATEGORY_GOAL)) {
                        if (isLocationEncountered || conceptsDoneAtLocation.contains(fabTemp.getnConceptId()))
                            isLocationEncountered = true;
                        passNode = currNode.addChild(fabTemp, link);
                        passNode.setParent(currNode, link);
                        if (isActionEncountered && isLocationEncountered) {
                            fabNodeCandidate = passNode;
                        }
                        if (searchForStartingGoalsRec(nLinkedNode, fabulaElements, links,
                                possibleStartingGoals, visitedNodesTemp, isActionEncountered,
                                isLocationEncountered, fabNodeCandidate, sNewParam, passNode,
                                conceptsDoneAtLocation, characterIds, worldAgent)) {
                            if (sNewParam != null && sNewParam.equals("agent")) {
                                if (!containsSameFabulaElem(possibleStartingGoals, fabNodeCandidate)) {

                                    // validate goal's preconditions
                                    sPreconditions = fabTemp.getsPreconditions();
                                    isValidA = true;
                                    isValidB = false;
                                    candidateCharacterIds = new CandidateCharacterIds();
                                    for (i = 0; i < characterIds.size(); i++) {
                                        characterId = characterIds.get(i);
                                        if (sPreconditions != null) {
                                            for (String sPrecondition : sPreconditions) {
                                                try {
                                                    sParts = Arrays.asList(sPrecondition.split(":"));
                                                    if (sParts.get(0).equals("agent") && (sParts.get(1).equals("gender")
                                                            || sParts.get(2).equals("trait"))) {
                                                        isValidA = isValidA && worldAgent.checkCondition(characterId, sParts.get(1),
                                                                sParts.get(2), "agent");
                                                    }
                                                } catch (IndexOutOfBoundsException e) {
                                                    throw new MalformedDataException("Error in parsing " +
                                                            "Fabula Element preconditions.");
                                                }
                                            }
                                        }
                                        if (isValidA) {
                                            candidateCharacterIds.addCandidates(characterId);
                                        }
                                        isValidB = isValidB || isValidA;
                                    }
                                    if (isValidB) {
                                        possibleStartingGoals.add(fabNodeCandidate);
                                        (fabNodeCandidate.getData().getParamValues().get("agent")).setData(candidateCharacterIds);
                                    }
                                }
                            }
                        }
                    } else if (sCategory.equals(FabulaElementNew.CATEGORY_ACTION)) {
                        if (!isActionEncountered) {
                            passNode = currNode.addChild(fabTemp, link);
                            passNode.setParent(currNode, link);
                            searchForStartingGoalsRec(nLinkedNode, fabulaElements, links, possibleStartingGoals,
                                    visitedNodesTemp, true, isLocationEncountered, fabNodeCandidate, sNewParam,
                                    passNode, conceptsDoneAtLocation, characterIds, worldAgent);
                        }
                    } else {
                        if (fabNodeCandidate != null) {
                            isFound = true;
                        } else {
                            passNode = currNode.addChild(fabTemp, link);
                            passNode.setParent(currNode, link);
                            searchForStartingGoalsRec(nLinkedNode, fabulaElements, links, possibleStartingGoals,
                                    visitedNodesTemp, isActionEncountered, isLocationEncountered, null,
                                    sNewParam, passNode, conceptsDoneAtLocation, characterIds, worldAgent);
                        }
                    }
                    fabNodeCandidate = prevFabNodeCandidate;
                }
            }
        }
        return isFound;
    }

    /**
     * Search for a path between two story goals via fabula links.
     *
     * @param contextGoals
     * @param fabulaElements
     * @param links
     * @throws DataMismatchException
     */
    @Deprecated
    public boolean linkStoryGoalsOld(ContextGoals contextGoals, List<FabulaElementNew> fabulaElements,
                                     List<LinkNew> links, FabulaNode[] fabNodeHead,
                                     FabulaNode[] fabNodeSupport)
            throws DataMismatchException, CloneNotSupportedException {
        List<Integer> nVisitedFabElems = new ArrayList<>();
        FabulaTree fabulaTree;
        FabulaElementNew fabElemTemp;
        FabulaNode fabNodeStart;
        FabulaNode[] fabNodeEnd = new FabulaNode[1]; // to allow passing of values via arguments
        boolean isFound;
        int nHead = contextGoals.getnMainGoal();
        int nSupport = -1; // = contextGoals.getnSupport(); // commented on 7-25
        int nDirection = contextGoals.getnSearchDirection();
        int nStart;
        int nEnd;

        if (nDirection == 1) {
            nStart = nHead;
            nEnd = nSupport;
        } else if (nDirection == 0) {
            nStart = nSupport;
            nEnd = nHead;
        } else {
            throw new DataMismatchException("Invalid search direction value of chosen context goals.");
        }

        fabElemTemp = fabulaElements.get(nStart - 1);
        initializeRequiredParameters(fabElemTemp);

        fabulaTree = new FabulaTree(fabulaElements.get(nStart - 1).clone());
        fabulaTree.setIsInverted(true);
        fabNodeStart = fabulaTree.getRoot();

        isFound = linkStoryGoalsRecurse(nStart, nEnd, fabulaElements, links, nVisitedFabElems,
                fabNodeStart, fabNodeEnd);

        if (isFound) {
            if (nDirection == 1) {
                fabNodeHead[0] = fabNodeStart;
                fabNodeSupport[0] = fabNodeEnd[0];
            } else {
                fabNodeHead[0] = fabNodeEnd[0];
                fabNodeSupport[0] = fabNodeStart;
            }
        }

        return isFound;
    }

    /**
     * Recursive method of link story goals.
     *
     * @param nCurrent
     * @param nEnd
     * @param fabulaElements
     * @param links
     * @param nVisitedFabElems
     * @param prevNode
     * @param fabNodeEnd
     */
    @Deprecated
    private boolean linkStoryGoalsRecurse(int nCurrent, int nEnd, List<FabulaElementNew> fabulaElements,
                                          List<LinkNew> links, List<Integer> nVisitedFabElems,
                                          FabulaNode prevNode, FabulaNode[] fabNodeEnd) throws CloneNotSupportedException {
        FabulaElementNew fabElem;
        FabulaNode passNode;
        List<Integer> nVisitedFabElemsTemp;
        HashMap<String, String> sParamDependencies;
        Iterator linksIterator = links.iterator();
        LinkNew link;
        boolean isPathFound = false;
        int nLinkedFabElem;

        while (linksIterator.hasNext() && !isPathFound) {
            link = (LinkNew) linksIterator.next();

            if (link.getnFb2Id() == nCurrent) {
                nLinkedFabElem = link.getnFb1Id();
                nVisitedFabElemsTemp = new ArrayList<>();
                nVisitedFabElemsTemp.addAll(nVisitedFabElems);

                if (!nVisitedFabElems.contains(nLinkedFabElem)) {
                    fabElem = fabulaElements.get(nLinkedFabElem - 1).clone();
                    nVisitedFabElemsTemp.add(nLinkedFabElem);
                    sParamDependencies = link.getsParamDependencies();

                    try {
                        linkParameterValues(fabElem, prevNode.getData(), sParamDependencies);
                    } catch (DataMismatchException e) {
                        e.printStackTrace();
                        continue;
                    }

                    initializeRequiredParameters(fabElem);

                    passNode = prevNode.addChild(fabElem, link);
                    passNode.setParent(prevNode, link);

                    if (nLinkedFabElem == nEnd) {
                        isPathFound = true;
                        fabNodeEnd[0] = passNode;
                    } else {
                        isPathFound = linkStoryGoalsRecurse(nLinkedFabElem, nEnd, fabulaElements, links, nVisitedFabElemsTemp, passNode, fabNodeEnd);
                    }
                }
            }
        }

        return isPathFound;
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

    /**
     * Link parameter values between two fabula elements.
     *
     * @param fabElem
     * @param prevFabElem
     * @param sParamDependencies
     */
    @Deprecated
    private void linkParameterValues(FabulaElementNew fabElem, FabulaElementNew prevFabElem,
                                     HashMap<String, String> sParamDependencies) throws DataMismatchException {
        String key;
        String sDestination;
        Iterator iterator = sParamDependencies.entrySet().iterator();
        HashMap<String, ParameterValueNew> prevParamValues = prevFabElem.getParamValues();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            key = (String) pair.getKey();
            switch (key) {
                case "agent":
                case "patient":
                case "target":
                case "hidden":
                    sDestination = (String) pair.getValue();
                    if (!prevParamValues.containsKey(sDestination)) {
                        prevParamValues.put(sDestination, new ParameterValueNew());
                    }
                    fabElem.getParamValues().put(key, prevParamValues.get(sDestination));
                    break;
                default:
                    if (key.matches("%[a-z_]+")) {
                        sDestination = (String) pair.getValue();
                        if (!prevParamValues.containsKey(sDestination)) {
                            prevParamValues.put(sDestination, new ParameterValueNew());
                        }
                        (prevParamValues.get(sDestination)).setData(key);
                    } else {
                        throw new DataMismatchException("Invalid value of parameter dependency encountered.");
                    }
            }
        }
    }

    /**
     * @param chosenStartingGoals
     * @param fabNodeEndGoal
     * @param storyPlan
     * @param worldAgent
     * @throws MalformedDataException
     * @throws CloneNotSupportedException
     */
    @Deprecated
    public FabulaNode generateExecutableStoryPath(List<FabulaNode> chosenStartingGoals, FabulaNode fabNodeEndGoal,
                                                  StoryPlanNew storyPlan, WorldAgentNew worldAgent,
                                                  FabulaNode lastLinkedFabNode)
            throws MalformedDataException, CloneNotSupportedException, OperationUnavailableException {

        // todo verify if there is a need to check if the fabula tree is inverted and at what scenario should it be done
        // todo implement events that interrupt action ex. A:search_for is interrupted by event E:see
        // todo should the character makeup affect the ordering of chosen starting goals, or somewhat?

        HashMap<FabulaNode, Set<CharacterIdentifierNew>> additionalNodesMap;
        HashMap<String, List<CharacterIdentifierNew>> sUnsatisfiedConditions;
        HashMap<String, List<CharacterIdentifierNew>> sTotalPreconditions;
        List<Pair<List<CharacterIdentifierNew>, FabulaNode>> storyPath;
        FabulaElementNew fabulaElementTemp, fabulaElementTemp2;
        CharacterIdentifierNew[] charIdArray;
        CandidateCharacterIds sceneActors;
        ParameterValueNew primaryParameterValue;
        FabulaNode fabNodeTemp, prevFabNodeTemp;
        FabulaNode lastFabulaNode = null;
        WorldAgentNew worldAgentClone;
        Iterator iteratorStartingGoals, iteratorAgentData;
        boolean isPathFound;
        boolean isGoalNodeReached;
        boolean isAllTrue;
        boolean isExecutable;
        boolean isCurrentGoalUnreachable;
        long seed;
        int j, k;

        // Randomize the list of candidate starting goals
        seed = System.nanoTime();
        Collections.shuffle(chosenStartingGoals, new Random(seed));
        iteratorStartingGoals = chosenStartingGoals.iterator();
        storyPath = new ArrayList<>();
        isPathFound = false;

        while (!isPathFound && iteratorStartingGoals.hasNext()) {

            // Clone world agent and apply changes on the clone
            worldAgentClone = worldAgent.clone();

            // Get starting goal node
            fabNodeTemp = (FabulaNode) iteratorStartingGoals.next();

            // commented on 7-21
//            primaryParameterValue = fabNodeTemp.getData().getParamValues().get("agent");
//            sceneActors = (CandidateCharacterIds) primaryParameterValue.getData();
//            iteratorAgentData = sceneActors.iterator();
//            while(iteratorAgentData.hasNext()) {
//                CharacterIdentifierNew charId = (CharacterIdentifierNew)iteratorAgentData.next();
//                storyPath.put(charId, new ArrayList<FabulaNode>());
//            }

            try {

                if (lastLinkedFabNode == null) {
                    /* ----- STARTING GOAL ----- */
                    // Realize preconditions for starting goal only
                    fabulaElementTemp = fabNodeTemp.getData();
                    if (!fabulaElementTemp.getsPreconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(fabNodeTemp.getData(), fabNodeTemp.getData().getsPreconditions());
                    }
                    storyPath.add(new Pair<List<CharacterIdentifierNew>, FabulaNode>(null, fabNodeTemp));

                    if (!fabulaElementTemp.getsPostconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(fabNodeTemp.getData(), fabNodeTemp.getData().getsPostconditions());
                    }
                    // Transfer to next node
                    prevFabNodeTemp = fabNodeTemp;
                    fabNodeTemp = fabNodeTemp.getParent();
                    /* ------------------------- */
                } else {
                    prevFabNodeTemp = lastLinkedFabNode;
                }

                isGoalNodeReached = false;
                isCurrentGoalUnreachable = false;
                while (!isGoalNodeReached && !isCurrentGoalUnreachable) { // Bottom-up approach
                    sUnsatisfiedConditions = new HashMap<>();
                    sTotalPreconditions = new HashMap<>();
                    additionalNodesMap = new HashMap<>();

                    // Check if current FBE preconditions are all met
                    isExecutable = false;
                    isAllTrue = true;
                    fabulaElementTemp = fabNodeTemp.getData();
                    if (!fabulaElementTemp.getsPreconditions().isEmpty()) {
                        isAllTrue = worldAgentClone.checkPreconditions(fabulaElementTemp, sUnsatisfiedConditions, sTotalPreconditions);
                        // If not, check if any prerequisite FBEs may be added
                        if (!isAllTrue) {
                            additionalNodesMap.clear();
                            isExecutable = false; // just to prevent errors.
//                            isExecutable = checkForPrerequisites(sTotalPreconditions, sUnsatisfiedConditions,
//                                    fabNodeTemp, prevFabNodeTemp, additionalNodesMap); // todo how about making this a recursive call, checking the conditions of the prerequisites themselves and doing the same operation
                        }
                    }

                    if (isAllTrue || isExecutable) { // todo add events for conditions that are false
                        for (Map.Entry<FabulaNode, Set<CharacterIdentifierNew>> pair : additionalNodesMap.entrySet()) {
                            // Add fabula nodes to story path and realize postconditions
                            storyPath.add(new Pair<List<CharacterIdentifierNew>, FabulaNode>(new ArrayList<>(pair.getValue()), pair.getKey()));

                            fabulaElementTemp2 = pair.getKey().getData();
                            if (!fabulaElementTemp2.getsPostconditions().isEmpty()) {
                                worldAgentClone.realizeConditions(fabulaElementTemp2, fabulaElementTemp2.getsPostconditions()); // todo how about the preconditions of these additional nodes
                            }
                        }

                        // Add fabula node to story path and realize postconditions
                        storyPath.add(new Pair<List<CharacterIdentifierNew>, FabulaNode>(null, fabNodeTemp));
                        if (!fabulaElementTemp.getsPostconditions().isEmpty()) {
                            worldAgentClone.realizeConditions(fabulaElementTemp, fabulaElementTemp.getsPostconditions());
                        }

                        // Transfer to next node
                        prevFabNodeTemp = fabNodeTemp;
                        fabNodeTemp = fabNodeTemp.getParent();

                        if (fabNodeTemp == fabNodeEndGoal) {
                            isGoalNodeReached = true;
                            lastFabulaNode = prevFabNodeTemp;
                        }
                    } else
                        isCurrentGoalUnreachable = true;
                }
            } catch (MissingDataException | DataMismatchException | MalformedDataException e) {
                e.printStackTrace();
                continue;
            }

            if (isGoalNodeReached) {
                isPathFound = true;
                worldAgent.update(worldAgentClone);
            }
        } // end of generating story path from the chosen starting goals

        // todo list
        // 1) introduction of character in location and time is not yet incorporated
        // 2) skip other parameters first, generate first the path with the agent
        // 3) incorporate story path into story plan
        // 4) if no path can be generated, throw exception

        return lastFabulaNode;
    }
//
//    public void generateExecutableStoryPath(List<FabulaNode> chosenGoals, FabulaNode cgFabulaTreeRoot,
//                                            StoryPlanNew storyPlan, WorldAgentNew worldAgentBase, List<List<CharacterNew>> agentsLL)
//            throws MalformedDataException, CloneNotSupportedException {
//
//        // todo verify if there is a need to check if the fabula tree is inverted and at what scenario should it be done
//        // todo implement events that interrupt action ex. A:search_for is interrupted by event E:see
//
//        boolean isPathFound = false;
//        boolean isGoalNodeReached;
//        long seed = System.nanoTime();
//        Iterator itr;
//        FabulaNode fabNodeTemp, prevFabNodeTemp;
//        WorldAgentNew worldAgentClone;
//        List<FabulaNode> storyPath;
//        List<CharacterNew> agentsInContext;
//        List<FabulaNode> chosenGoalInOrder = new ArrayList<>();
//        List<String> sUnsatisfiedConditions;
//
//        chosenGoalInOrder.addAll(chosenGoals);
//        Collections.shuffle(chosenGoals, new Random(seed)); // todo should the character makeup affect the ordering of goals, or somewhat
//        itr = chosenGoals.iterator();
//        storyPath = new ArrayList<>();
//
//        while (!isPathFound && itr.hasNext()) {
//
//            storyPath.clear();
//            worldAgentClone = worldAgentBase.clone();
//
//            // Get starting node
//            fabNodeTemp = (FabulaNode) itr.next();
//            agentsInContext = agentsLL.get(chosenGoalInOrder.indexOf(fabNodeTemp));
//
//            try {
//                fillInRequiredParameters(fabNodeTemp.getData(),
//                        agentsInContext.get((int) (Math.random() * agentsInContext.size())));
//                worldAgentClone.realizeConditions(fabNodeTemp.getData(), fabNodeTemp.getData().getsPreconditions());
//
//                executeFabulaElement(fabNodeTemp, storyPath, worldAgentClone);
//
//                prevFabNodeTemp = fabNodeTemp;
//                fabNodeTemp = fabNodeTemp.getParent();
//                isGoalNodeReached = false;
//                while (!isGoalNodeReached) {
//                    // bottom-up approach
//                    fillInRequiredParameters(fabNodeTemp.getData(),
//                            prevFabNodeTemp.getParentLink().getsParamDependencies(),
//                            prevFabNodeTemp.getData());
//                    sUnsatisfiedConditions = new ArrayList<>();
//                    if (worldAgentClone.checkPreconditions(fabNodeTemp.getData(),
//                            fabNodeTemp.getData().getsPreconditions(), sUnsatisfiedConditions)) { // todo add events for conditions that are false
//                        executeFabulaElement(fabNodeTemp, storyPath, worldAgentClone);
//                    }
//                    prevFabNodeTemp = fabNodeTemp;
//                    fabNodeTemp = fabNodeTemp.getParent();
//                    if (fabNodeTemp == cgFabulaTreeRoot) {
//                        isGoalNodeReached = true;
//                    }
//                }
//                isPathFound = true;
//            } catch (MissingDataException | DataMismatchException | MalformedDataException e) {
//                e.printStackTrace();
//                continue;
//            }
//
//            // todo
//            /* 1) introduction of character in location and time is not yet incorporated
//               2) skip other parameters first, generate first the path with the agent
//               3) incorporate story path into story plan
//
//             */
//        }
//        System.out.println("=================END OF PHASE 1=================");
//        System.out.println("List of Fabula Elements in Story Path B:");
//        for (FabulaNode fabNode : storyPath) {
//            System.out.println(fabNode.getData().toString());
//        }
//    }
//
//    public void generateExecutableStoryPath(FabulaNode fabHead, FabulaNode fabSupport,
//                                            StoryPlanNew storyPlan, WorldAgentNew worldAgent, int nDirection,
//                                            List<LinkNew> links)
//            throws DataMismatchException {
//        FabulaNode fabStart;
//        List<FabulaNode> storyPath;
//        final int searchHeuristic = -1; // set value
//
//        if(nDirection == 0) {
//            fabStart = fabHead;
//        }
//        else if(nDirection == 1) {
//            fabStart = fabSupport;
//        }
//        else {
//            throw new DataMismatchException("Value for search direction in Context Goals database " +
//                    "table is invalid.");
//        }
//
//        storyPath = new ArrayList<>();
//
//        // todo complete
//
//    }
//
//    private void fillInRequiredParameters(FabulaElementNew fabEl, HashMap<String, String> sParamDependencies, FabulaElementNew prevFabEl) throws MissingDataException {
//        Iterator itrParamDep = sParamDependencies.entrySet().iterator();
//        List<String> sRequiredParams = fabEl.getsRequiredParams();
//        String sParamValue;
//        HashMap<String, String> sParamValues = prevFabEl.getParamValues();
//        HashMap<String, String> sParamValues2;
//        List<Integer> nDbConceptsTemp;
//        long lRandomSeed;
//
//        if(!sParamValues.isEmpty()) {
//            while (itrParamDep.hasNext()) {
//                Map.Entry pair = (Map.Entry) itrParamDep.next();
//                switch ((String) pair.getKey()) {
//                    case "agent":
//                        if ((sParamValue = sParamValues.get("agent")) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    case "patient":
//                        if ((sParamValue = sParamValues.get("patient")) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    case "target":
//                        if ((sParamValue = sParamValues.get("target")) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    // todo how about location and time?
//                }
//            }
//        }
//
//        sParamValues2 = fabEl.getParamValues();
//        for (String sRequiredParam : sRequiredParams) {
//            switch (sRequiredParam) {
//                case "agent":
//                    if (!sParamValues2.containsKey("agent")) {
//                        throw new MissingDataException("Missing value of agent parameter of a fabula element."); // assumption: agent can only be set in a starting goal or through parameter dependency
//                    }
//                    break;
//                case "patient":
//                    // check knowledge base
//                    // patient in this scenario is a custom made character not a predefined character chosen by the user as input
//                    if (!sParamValues2.containsKey("patient")) {
//                        nDbConceptsTemp = DBQueriesNew.getRelatedRightHandConcepts(fabEl.getnConceptId(),
//                                SemanticRelation.DONE_TO); // todo if this is an action, how about for perception, ie
//                        lRandomSeed = System.nanoTime();
//                        Collections.shuffle(nDbConceptsTemp, new Random(lRandomSeed));
//                        try {
//                            fabEl.getParamValues().put("patient", "concept:" + nDbConceptsTemp.get(0));
//                        } catch (IndexOutOfBoundsException e) {
//                            throw new MissingDataException("Error in filling in required fabula element parameters.");
//                        }
//                    }
//                    break;
//                case "target":
//                    if (!sParamValues2.containsKey("target")) {
//                        // check knowledge base
//                        nDbConceptsTemp = DBQueriesNew.getRelatedRightHandConcepts(fabEl.getnConceptId(),
//                                SemanticRelation.TARGETS);
//                        lRandomSeed = System.nanoTime();
//                        Collections.shuffle(nDbConceptsTemp, new Random(lRandomSeed));
//                        try {
//                            fabEl.getParamValues().put("target", "concept:" + nDbConceptsTemp.get(0));
//                        } catch (IndexOutOfBoundsException e) {
//                            throw new MissingDataException("Error in filling in required fabula element parameters.");
//                        }
//                    }
//                    break;
//            }
//        }
//    }
//
//    /**
//     *
//     * @param fabulaElement
//     * @param agent
//     * @throws MissingDataException
//     */
//    private void fillInRequiredParameters(FabulaElementNew fabulaElement, CharacterNew agent) throws MissingDataException {
//        List<String> sRequiredParams = fabulaElement.getsRequiredParams();
//        List<Integer> nDbConceptsTemp;
//        long lRandomSeed;
//
//        for (String sRequiredParam : sRequiredParams) {
//            switch (sRequiredParam) {
//                case "agent":
//                    fabulaElement.getParamValues().put("agent", "character_instance:" + agent.getnId()); // different from concept ID
//                    break;
//                case "patient":
//                    fabulaElement.getParamValues().put("patient", "unknown");
//
//
//                    /* COMMENTED on 7-13-15 // to be used probably at a later part in the code and not here
//                    nDbConceptsTemp = DBQueriesNew.getRelatedRightHandConcepts(fabulaElement.getnConceptId(),
//                            SemanticRelation.DONE_TO);
//                    lRandomSeed = System.nanoTime();
//                    Collections.shuffle(nDbConceptsTemp, new Random(lRandomSeed));
//                    try {
//                        fabulaElement.getParamValues().put("patient", "concept:" + nDbConceptsTemp.get(0));
//                    } catch (IndexOutOfBoundsException e) {
//                        throw new MissingDataException("Error in filling in required fabula element parameters.");
//                    }
//                    */
//
//                    break;
//                case "target":
//                    // check knowledge base
//                    nDbConceptsTemp = DBQueriesNew.getRelatedRightHandConcepts(fabulaElement.getnConceptId(),
//                            SemanticRelation.TARGETS);
//                    lRandomSeed = System.nanoTime();
//                    Collections.shuffle(nDbConceptsTemp, new Random(lRandomSeed));
//                    try {
//                        fabulaElement.getParamValues().put("target", "concept:" + nDbConceptsTemp.get(0));
//                    } catch (IndexOutOfBoundsException e) {
//                        throw new MissingDataException("Error in filling in required fabula element parameters.");
//                    }
//                    break;
//            }
//        }
//    }
//
//    private void fillInRequiredParameters(FabulaElementNew prereqFabEl, FabulaElementNew fabEl, HashMap<String, String> sParamDependencies) {
//        List<String> sRequiredParams = prereqFabEl.getsRequiredParams();
//        HashMap<String, String> sParamValues = fabEl.getParamValues();
//        Iterator itrParamDep = sParamDependencies.entrySet().iterator();
//        String sParamValue;
//
//        if(!sParamValues.isEmpty()) {
//            while (itrParamDep.hasNext()) {
//                Map.Entry pair = (Map.Entry) itrParamDep.next();
//                switch ((String) pair.getKey()) {
//                    case "agent":
//                        if ((sParamValue = sParamValues.get(pair.getValue())) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    case "patient":
//                        if ((sParamValue = sParamValues.get("patient")) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    case "target":
//                        if ((sParamValue = sParamValues.get("target")) == null) {
//                            throw new MissingDataException("Error in linking parameter dependency values between two fabula elements.");
//                        }
//                        fabEl.getParamValues().put((String) pair.getValue(), sParamValue);
//                        break;
//                    // todo how about location and time?
//                }
//            }
//        }
//    }

    private boolean checkForPrerequisites(
            HashMap<String, List<CharacterIdentifierNew>> totalConditionsMap,
            HashMap<String, List<CharacterIdentifierNew>> unsatisfiedConditionsMap, FabulaNodeNew currentFabNode,
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
        Iterator<Map.Entry<String, List<CharacterIdentifierNew>>> iteratorUCM;
        Iterator<String> iteratorPC;
        Iterator<Pair<FabulaNodeNew, LinkNew>> iteratorPPQ;
        Map.Entry<String, List<CharacterIdentifierNew>> pair;
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
                        totalConditionsMap.get(sTransformedCondition).addAll(pair.getValue());
                        isInclude = true;
                        charactersIncluded.addAll(pair.getValue());
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
        for (Map.Entry<String, List<CharacterIdentifierNew>> pair2 : totalConditionsMap.entrySet()) {
            if (pair2.getValue().isEmpty())
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
        Stack<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStack;
        List<Integer> linkIdsOfExecutedFBEs;
        WorldAgentNew worldAgentClone;
        FabulaNodeNew fabNodeTemp;
        boolean forTermination;
        boolean isFirstLoop;

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
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
                } else {
                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
                }
            } else {
                if (nContextDirection == 1 && authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnSupportingGoal()) {
                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
                } else if (nContextDirection == 1 && authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnMainGoal()) {
                    fabNodeTemp = possibleStartingGoalsSupport.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
                } else if (authorGoalsCopy.peek().getnFabGoalId() == contextGoals.getnSupportingGoal()) {
                    fabNodeTemp = possibleStartingGoalsMain.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsSupport.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
                } else {
                    fabNodeTemp = possibleStartingGoalsSupport.peek();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));

                    fabNodeTemp = possibleStartingGoalsMain.pop();
                    fabNodeTemp.getData().setupExecutionAgents();
                    executionStack.add(new Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>(fabNodeTemp, null, null));
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

        return authorGoalsCopy.empty();
    }

    private boolean execute(Stack<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStack,
                            StoryPlanNew storyPlan, WorldAgentNew worldAgentClone,
                            Stack<AuthorGoal> authorGoals, List<Integer> linkIdsOfExecutedFBEs)
            throws CloneNotSupportedException, DataMismatchException, MissingDataException,
            MalformedDataException, OperationUnavailableException {

        FabulaNodeNew currentFabNodeCandidate;
        FabulaElementNew currentFabElem;
        boolean isSuccessful;
        List<FabulaNodeNew> storyPath;

        isSuccessful = false;
        currentFabNodeCandidate = executionStack.peek().first;
        currentFabElem = currentFabNodeCandidate.getData();
        storyPath = new ArrayList<>();

        switch (currentFabElem.getsCategory()) {
            case FabulaElementNew.CATEGORY_GOAL:
                // Realize preconditions for starting goal only
                if (executionStack.peek().second == null) {
                    if (!currentFabElem.getsPreconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(currentFabElem, currentFabElem.getsPreconditions());
                    }
                    if (!currentFabElem.getsPostconditions().isEmpty()) {
                        worldAgentClone.realizeConditions(currentFabElem, currentFabElem.getsPostconditions());
                    }
                }

                if (authorGoals.peek().getnFabGoalId() == currentFabElem.getnId())
                    authorGoals.pop();

                isSuccessful = executeRecurse(currentFabNodeCandidate, executionStack.peek().third,
                        executionStack.peek().second, storyPath, executionStack,
                        linkIdsOfExecutedFBEs, worldAgentClone); // executeRecurse() returns false
                // when goal is not achievable, outcome FBE cannot be traced
                break;
            case FabulaElementNew.CATEGORY_ACTION:
                isSuccessful = executeRecurse(currentFabNodeCandidate, executionStack.peek().third,
                        executionStack.peek().second, storyPath, executionStack,
                        linkIdsOfExecutedFBEs, worldAgentClone);
                break;
        }

        if (isSuccessful) {
            storyPlan.addAll(storyPath);
        }

        return isSuccessful;
    }

    private boolean executeRecurse(FabulaNodeNew originFabNode, FabulaNodeNew parentOfOriginFabNode,
                                   LinkNew prevLink, List<FabulaNodeNew> storyPath,
                                   Stack<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStack,
                                   List<Integer> linkIdsOfExecutedFBEs,
                                   WorldAgentNew worldAgentClone)
            throws MalformedDataException, DataMismatchException, OperationUnavailableException, MissingDataException {

        List<LinkNew> linksOfCurrentFBE;
        List<LinkNew> motivateLinks;
        List<LinkNew> causesAndSubLinks;
        List<LinkNew> interruptLinks;
        List<LinkNew> enableLinks;
        List<LinkNew> tempLinks;
        Iterator<LinkNew> linksIterator;
        Iterator<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStackIterator;
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
        linksOfCurrentFBE = DBQueriesNew.getLinksOfSourceFabElem(originFabNode.getData().getnId());
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
                case LinkNew.TYPE_INTERRUPT:
                    interruptLinks.add(linkTemp2);
                    break;
            }
        }

        // Check what type of FBE is current FBE in context
        switch (originFabNode.getData().getsCategory()) {
            case FabulaElementNew.CATEGORY_GOAL:

                // Shuffle motivation links and evaluate motivate link conditions
                evaluateLinks(motivateLinks);

                // iterate through them
                linksIterator = motivateLinks.iterator();
                hasPositiveChange = false;
                while (linksIterator.hasNext() && executionStack.peek().first == originFabNode) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                        linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                        originFabNode.addDestination(linkedFabNode, linkTemp);
                        linkedFabNode.addSource(originFabNode, linkTemp);
                    }

                    if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                            storyPath, linkIdsOfExecutedFBEs)) {

                        // if goal, then add to execution stack
                        // if action, continue execution
                        switch (linkedFabNode.getData().getsCategory()) {
                            case FabulaElementNew.CATEGORY_GOAL:
                                linkedFabNode.getData().setupExecutionAgents();
                                executionStack.add(new Triple<>(linkedFabNode, linkTemp, originFabNode));
                                hasPositiveChange = true;
                                break;
                            case FabulaElementNew.CATEGORY_ACTION:
                                hasPositiveChange = executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                        executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                break;
                            default:
                                throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                        }
                    }
                }

                if (executionStack.peek().first == originFabNode && !hasPositiveChange) {
                    isExecuteRecurseSuccessful = false;
                }

                /* Processing of Enable Links */
                seed = System.nanoTime();
                Collections.shuffle(enableLinks, new Random(seed));
                linksIterator = enableLinks.iterator();
                while (linksIterator.hasNext()) {
                    linkTemp = linksIterator.next();
                    if (true) { // todo change to evaluation of link conditions
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        linkParamValuesDownward(originFabNode.getData(), linkedFabNode.getData(), linkTemp.getsParamDependencies());
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_ACTION:

                validInterruptFound = false;
                if (!interruptLinks.isEmpty()) {
                    linksIterator = interruptLinks.iterator();
                    linkedFabNode = null;
                    linkTemp = null;
                    while (linksIterator.hasNext() && !validInterruptFound) {
                        linkTemp = linksIterator.next();
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode != null) {
                            validInterruptFound = true;
                        }
                    }

                    if (validInterruptFound) {
                        if (executeFabulaElement(linkedFabNode, linkTemp, originFabNode, worldAgentClone,
                                storyPath, linkIdsOfExecutedFBEs)) {

                            // Execute only when an interrupt is executed
                            originFabNode.getData().setupExecutionAgents();
                            executionStack.add(new Triple<>(originFabNode, prevLink, parentOfOriginFabNode));

                            // If event, continue execution
                            // Otherwise, throw exception
                            switch (linkedFabNode.getData().getsCategory()) {
                                case FabulaElementNew.CATEGORY_EVENT:
                                    executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                            executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                    break;
                                default:
                                    throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                            }
                        }
                    }
                }

                // If no interrupt was executed, proceed here
                if (!validInterruptFound) {
                    // Evaluate causality and sub action link conditions
                    evaluateLinks(causesAndSubLinks);

                    // iterate through them
                    linksIterator = causesAndSubLinks.iterator();
                    while (linksIterator.hasNext()) {

                        // Get linked FBE
                        linkTemp = linksIterator.next();
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
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
                                    executeRecurse(linkedFabNode, originFabNode, linkTemp, storyPath,
                                            executionStack, linkIdsOfExecutedFBEs, worldAgentClone);
                                    break;
                                default:
                                    throw new DataMismatchException("Unexpected fabula element " + linkedFabNode.getData().getsLabel() + " in current link " + linkTemp.getnLinkId() + " was encountered.");
                            }
                        }
                    }
                }

                /* Processing of Enable Links */
                seed = System.nanoTime();
                Collections.shuffle(enableLinks, new Random(seed));
                linksIterator = enableLinks.iterator();
                while (linksIterator.hasNext()) {
                    linkTemp = linksIterator.next();
                    if (true) { // todo change to evaluation of link conditions
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        linkParamValuesDownward(originFabNode.getData(), linkedFabNode.getData(), linkTemp.getsParamDependencies());
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_INTERN:
            case FabulaElementNew.CATEGORY_PERCEPT:
                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks);

                // Iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
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
                                executionStack.add(new Triple<>(linkedFabNode, linkTemp, originFabNode));
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
                                throw new DataMismatchException("Unexpected fabula element in current link was encountered.");
                        }
                    }
                }

                // Evaluate motivation link conditions
                evaluateLinks(motivateLinks);

                // Iterate through them
                linksIterator = motivateLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
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

                /* Processing of Enable Links */
                seed = System.nanoTime();
                Collections.shuffle(enableLinks, new Random(seed));
                linksIterator = enableLinks.iterator();
                while (linksIterator.hasNext()) {
                    linkTemp = linksIterator.next();
                    if (true) { // todo change to evaluation of link conditions
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        linkParamValuesDownward(originFabNode.getData(), linkedFabNode.getData(), linkTemp.getsParamDependencies());
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_EVENT:
                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks);

                // iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
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

                /* Processing of Enable Links */
                seed = System.nanoTime();
                Collections.shuffle(enableLinks, new Random(seed));
                linksIterator = enableLinks.iterator();
                while (linksIterator.hasNext()) {
                    linkTemp = linksIterator.next();
                    if (true) { // todo change to evaluation of link conditions
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        linkParamValuesDownward(originFabNode.getData(), linkedFabNode.getData(), linkTemp.getsParamDependencies());
                    }
                }

                break;
            case FabulaElementNew.CATEGORY_OUTCOME:
                // Evaluate causality and sub action link conditions
                evaluateLinks(causesAndSubLinks);

                // Iterate through them
                linksIterator = causesAndSubLinks.iterator();
                while (linksIterator.hasNext()) {

                    // Get linked FBE
                    linkTemp = linksIterator.next();
                    linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                    if (linkedFabNode == null) {
                        // Get linked fabula element if not traversed before in previous operations
                        fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
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
                                executionStack.add(new Triple<>(linkedFabNode, linkTemp, originFabNode));
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

                /* Processing of Enable Links */
                seed = System.nanoTime();
                Collections.shuffle(enableLinks, new Random(seed));
                linksIterator = enableLinks.iterator();
                while (linksIterator.hasNext()) {
                    linkTemp = linksIterator.next();
                    if (true) { // todo change to evaluation of link conditions
                        linkedFabNode = originFabNode.getFabNodeInDestinations(linkTemp.getnFb2Id());
                        if (linkedFabNode == null) {
                            // Get linked fabula element if not traversed before in previous operations
                            fabElemTemp = DBQueriesNew.getFabulaElementById(linkTemp.getnFb2Id());
                            linkedFabNode = FabulaNodeNew.getFabulaNode(fabElemTemp);
                            originFabNode.addDestination(linkedFabNode, linkTemp);
                            linkedFabNode.addSource(originFabNode, linkTemp);
                        }

                        linkParamValuesDownward(originFabNode.getData(), linkedFabNode.getData(), linkTemp.getsParamDependencies());
                    }
                }

                break;
        }

        //* when should a link be valid? what are the nature of link preconditions?

        return isExecuteRecurseSuccessful;
    }

    private void updateExecutionStackOnOutcome(FabulaNodeNew linkedFabNode, Stack<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStack) {
        Iterator<Triple<FabulaNodeNew, LinkNew, FabulaNodeNew>> executionStackIterator;
        boolean isFound;
        FabulaNodeNew fabNodeTemp;
        FabulaElementNew fabElemTemp;
        ParameterValueNew parameterValue;

        executionStackIterator = executionStack.iterator();
        isFound = false;
        while (executionStackIterator.hasNext() && !isFound) {
            fabNodeTemp = executionStackIterator.next().first;
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

    private void evaluateLinks(List<LinkNew> links) {
        long seed;
        Iterator<LinkNew> linksIterator;
        LinkNew linkTemp;
        List<LinkNew> tempLinks = new ArrayList<>();

        // Shuffle links and evaluate link conditions
        seed = System.nanoTime();
        Collections.shuffle(links, new Random(seed));
        linksIterator = links.iterator();
        while (linksIterator.hasNext()) {
            linkTemp = linksIterator.next();
            if (true) { // todo evaluate link conditions
                if (linkTemp.getnPriority() == 1)
                    tempLinks.add(0, linkTemp);
                else
                    tempLinks.add(linkTemp);
            }
        }
        links.clear();
        links.addAll(tempLinks);
    }

    private boolean executeFabulaElement(FabulaNodeNew fabNodeTemp, LinkNew linkTemp, FabulaNodeNew originFabNode,
                                         WorldAgentNew worldAgentClone, List<FabulaNodeNew> storyPath,
                                         List<Integer> linkIdsOfExecutedFBEs)
            throws MalformedDataException, DataMismatchException, OperationUnavailableException, MissingDataException {

        FabulaElementNew fabElemTemp;
        FabulaElementNew additionalFabElem;
        HashMap<String, List<CharacterIdentifierNew>> sUnsatisfiedConditions, sTotalPreconditions;
        List<Pair<FabulaNodeNew, LinkNew>> additionalNodesAndLinks;
        boolean isAllTrue, isExecutable, isSuccessful;

        fabElemTemp = fabNodeTemp.getData();
        isSuccessful = false;

        linkParamValuesDownward(originFabNode.getData(), fabElemTemp, linkTemp.getsParamDependencies());
        initializeRequiredParameters(fabElemTemp);
        unlinkParams(fabElemTemp);

        // Initialize the required Collections for the succeeding operations
        sUnsatisfiedConditions = new HashMap<>();
        sTotalPreconditions = new HashMap<>();
        additionalNodesAndLinks = new ArrayList<>();

        // Check if current FBE preconditions are all met
        isExecutable = false;
        isAllTrue = true;
        if (!fabElemTemp.getsPreconditions().isEmpty()) {
            isAllTrue = worldAgentClone.checkPreconditions(fabElemTemp, sUnsatisfiedConditions, sTotalPreconditions);
            if (!isAllTrue) {
                additionalNodesAndLinks.clear();
                isExecutable = checkForPrerequisites(sTotalPreconditions, sUnsatisfiedConditions,
                        fabNodeTemp, originFabNode, additionalNodesAndLinks, storyPath); // todo how about making this a recursive call, checking the conditions of the prerequisites themselves and doing the same operation
            }
        }

        if (isAllTrue || isExecutable) { // todo add events for conditions that are false
            for (Pair<FabulaNodeNew, LinkNew> additionalNodeAndLink : additionalNodesAndLinks) {
                // Add fabula nodes to story path and realize postconditions
                storyPath.add(additionalNodeAndLink.first);
                additionalFabElem = additionalNodeAndLink.first.getData();

                linkParamValuesDownward(fabElemTemp, additionalFabElem, additionalNodeAndLink.second.getsParamDependencies());
                unlinkParams(additionalFabElem);

                if (!additionalFabElem.getsPostconditions().isEmpty()) {
                    worldAgentClone.realizeConditions(additionalFabElem, additionalFabElem.getsPostconditions()); // todo how about the preconditions of these additional nodes
                }

                linkIdsOfExecutedFBEs.add(additionalNodeAndLink.second.getnLinkId());
            }

            if (!fabElemTemp.getsPostconditions().isEmpty()) {
                worldAgentClone.realizeConditions(fabElemTemp, fabElemTemp.getsPostconditions());
            }

            linkIdsOfExecutedFBEs.add(linkTemp.getnLinkId());
            isSuccessful = true;
        }

        return isSuccessful;
    }

    @Deprecated
    private void transferLinkValues(FabulaElementNew fabElemTemp,
                                    HashMap<String, String> sParamDependencies) throws DataMismatchException {
        String key;
        String sDestination;
        Iterator iterator = sParamDependencies.entrySet().iterator();
        HashMap<String, ParameterValueNew> paramValues = fabElemTemp.getParamValues();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            key = (String) pair.getKey();
            if (key.matches("%[a-z_]+")) {
                sDestination = (String) pair.getValue();
                if (!paramValues.containsKey(sDestination)) {
                    paramValues.put(sDestination, new ParameterValueNew());
                }
                (paramValues.get(sDestination)).setData(key);
            } else if (key.matches("#this")) {
                sDestination = (String) pair.getValue();
                if (!paramValues.containsKey(sDestination)) {
                    paramValues.put(sDestination, new ParameterValueNew());
                }
                (paramValues.get(sDestination)).setData(fabElemTemp);
            }
            else {
                throw new DataMismatchException("Invalid value of parameter dependency encountered.");
            }
        }
    }

    /**
     * Replaces the ParameterValue objects in the current fabula element shared with other fabula
     * elements with a new ParameterValue instance but copying the same data contained in the
     * old ParameterValue object.
     *
     * @param fabElemTemp
     */
    private void unlinkParams(FabulaElementNew fabElemTemp) {
        HashMap<String, ParameterValueNew> paramValueMap;
        ParameterValueNew paramValTemp;

        paramValueMap = fabElemTemp.getParamValues();
        for (Map.Entry<String, ParameterValueNew> entry : paramValueMap.entrySet()) {
            paramValTemp = new ParameterValueNew();
            paramValTemp.setData(entry.getValue().getData());
            entry.setValue(paramValTemp);
        }
    }

    private Stack<AuthorGoal> compileAuthorGoals(ContextGoals contextGoals, ConflictGoals conflictGoals,
                                                 ResolutionGoal resolutionGoal) {

        Stack<AuthorGoal> authorGoals = new Stack<>();

        authorGoals.add(new AuthorGoal(resolutionGoal.getnGoal()));
        authorGoals.add(new AuthorGoal(conflictGoals.getnCounterGoal()));
        authorGoals.add(new AuthorGoal(conflictGoals.getnConflictGoal()));

        if (contextGoals.getnSearchDirection() == 1) {
            authorGoals.add(new AuthorGoal(contextGoals.getnMainGoal()));
            authorGoals.add(new AuthorGoal(contextGoals.getnSupportingGoal()));
        } else {
            authorGoals.add(new AuthorGoal(contextGoals.getnSupportingGoal()));
            authorGoals.add(new AuthorGoal(contextGoals.getnMainGoal()));
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

        for (LinkNew link : DBQueriesNew.getLinksOfDestinationFabElem(currFabNode.getData().getnId())) {
            nLinkedNode = link.getnFb1Id();
            isFound = false;

            // Check if the fabula node is visited already in the search path in context
            nVisitedNodesTemp = new ArrayList<>();
            nVisitedNodesTemp.addAll(nVisitedNodes);
            if (!nVisitedNodesTemp.contains(nLinkedNode)) {
                prevFabNodeCandidate = fabNodeCandidate;
                fabTemp = DBQueriesNew.getFabulaElementById(nLinkedNode);
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

        int nStart;
        int nEnd;
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
            nEnd = nSupportingGoal;
        } else if (nDirection == 0) {
            nStart = nSupportingGoal;
            nEnd = nMainGoal;
        } else {
            throw new DataMismatchException("Invalid search direction value of chosen context goals.");
        }

        fabulaElementStart = DBQueriesNew.getFabulaElementById(nStart);
        fabulaElementEnd = DBQueriesNew.getFabulaElementById(nEnd);
        fabulaElementStart.getParamValues().put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
        fabulaElementEnd.getParamValues().put(FabulaElementNew.PARAMS_AGENT, new ParameterValueNew());
        if (nDirection == 1) {
            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidates(mainCharId);
            fabulaElementStart.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);

            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidates(supportingCharsId);
            fabulaElementEnd.getParamValues().get(FabulaElementNew.PARAMS_AGENT).setData(candidateCharsTemp);
        } else {
            candidateCharsTemp = new CandidateCharacterIds();
            candidateCharsTemp.addCandidates(mainCharId);
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

        relatedLinks = DBQueriesNew.getLinksOfDestinationFabElem(previousNode.getData().getnId());
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
                    fabulaElement = DBQueriesNew.getFabulaElementById(nLinkedFabElem);
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
                        parameterValueTemp.setData(objTemp);
                        currFabulaElement.getParamValues().put(key, parameterValueTemp);
                    }
                    break;
            }
        }
    }

    private void linkParamValuesDownward(FabulaElementNew sourceFabulaElement, FabulaElementNew destFabulaElement,
                                         HashMap<String, String> sParamDependencies) throws DataMismatchException {

        String key, key2;
        String[] sParts;
        String sDestination;
        Iterator iterator = sParamDependencies.entrySet().iterator();
        HashMap<String, ParameterValueNew> sourceParamValues = sourceFabulaElement.getParamValues();
        HashMap<String, ParameterValueNew> destParamValues = destFabulaElement.getParamValues();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            key = (String) pair.getKey();

            if (key.matches("[a-z]+\\.[a-z]+")) {
                sParts = key.split(".");
                key = sParts[0];
                key2 = sParts[1];

                switch (key) {
                    case FabulaElementNew.PARAMS_AGENT:
                    case FabulaElementNew.PARAMS_PATIENT:
                    case FabulaElementNew.PARAMS_TARGET:
                    case FabulaElementNew.PARAMS_HIDDEN:
                    case FabulaElementNew.PARAMS_DIRECTION:
                        sDestination = (String) pair.getValue();
                        if (sourceParamValues.get(key) != null) {
                            if (sourceParamValues.containsKey(key2)) {
                                destParamValues.put(sDestination, sourceParamValues.get(key2));
                            }
                        }
                        break;
                }
            }
            else if (key.matches("%[a-z_]+")) {
                sDestination = (String) pair.getValue();
                if (!destParamValues.containsKey(sDestination)) {
                    destParamValues.put(sDestination, new ParameterValueNew());
                }
                (destParamValues.get(sDestination)).setData(key);
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
