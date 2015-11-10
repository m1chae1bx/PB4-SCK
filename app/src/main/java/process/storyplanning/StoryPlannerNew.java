package process.storyplanning;

import java.util.List;
import java.util.Map;

import model.narratologicalmodel.ConflictGoals;
import model.narratologicalmodel.ContextGoals;
import model.narratologicalmodel.ContextGoalsOld;
import model.storyplanmodel.FabulaElementNew;
import model.narratologicalmodel.GoalTraitNew;
import model.storyplanmodel.LinkNew;
import model.narratologicalmodel.ResolutionGoal;
import model.storyplanmodel.ParameterValueNew;
import model.storyplanmodel.StoryPlanNew;
import model.storyworldmodel.CharacterIdentifierNew;
import model.storyworldmodel.CharacterNew;
import model.storyworldmodel.ObjectNew;
import model.storyworldmodel.StoryWorldNew;
import process.exceptions.DataMismatchException;
import process.exceptions.MalformedDataException;
import process.exceptions.MissingDataException;
import process.exceptions.OperationUnavailableException;
import process.helpers.FabulaNode;
import process.helpers.FabulaNodeNew;
import process.helpers.FabulaTree;

/**
 * Created by M. Bonon on 6/20/2015.
 */
public class StoryPlannerNew {

    private static final boolean SCENE_CONTINUATION = true;
    private static final boolean SCENE_BEGINNING = false;

    public StoryPlanNew planStory(StoryWorldNew storyWorld)
            throws MalformedDataException, CloneNotSupportedException, MissingDataException,
            DataMismatchException, OperationUnavailableException {

        PlotAgentNew plotAgent = new PlotAgentNew();
        WorldAgentNew worldAgent = new WorldAgentNew(storyWorld);
        GoalTraitNew goalTrait;

        // after 7-24
        ConflictGoals conflictGoals;
        ContextGoals contextGoals;
        ResolutionGoal resolutionGoal;
        StoryPlanNew storyPlan = new StoryPlanNew();

        // before 7-24
        //ContextGoals contextGoalsOld;
        FabulaTree cgHeadTree, cgSupportTree;
        List<CharacterIdentifierNew> characterIds;
        List<FabulaNodeNew> headChosenGoals;
        List<FabulaNodeNew> supportChosenGoals;
        List<FabulaElementNew> fabulaElements;
        List<LinkNew> links;
        boolean isContextMet;
        boolean isConflictMet;
        FabulaNodeNew[] fabNodeHead = new FabulaNodeNew[1];
        FabulaNodeNew[] fabNodeSupport = new FabulaNodeNew[1];
        FabulaNodeNew lastSupport, lastHead;
        boolean isPlanningSuccessful;

        FabulaElementNew fabElem;

        /* ------ Pick a goal trait ------ */
        goalTrait = plotAgent.selectGoalTrait(worldAgent.getMainCharacter().getnNegativeTraits(),
                worldAgent.getSetting().getnLocationConceptId(), worldAgent.getObjectConceptIds());

        /* ------ Pick conflict goals ------ */
        conflictGoals = plotAgent.selectConflict(goalTrait.getnId());

        /* ------ Pick context goals based on chosen conflict and assign to characters ------ */
        // Pick context main goal (for main character)
        // Pick context supporting goals (for supporting characters)
        contextGoals = plotAgent.selectContext(conflictGoals.getnId());

        /* ------ Pick resolution based on chosen conflict ------ */
        resolutionGoal = plotAgent.selectResolution(conflictGoals.getnId());

        /* ------ Generate story context ------ */
        isPlanningSuccessful = plotAgent.generateStory(contextGoals, conflictGoals, resolutionGoal, storyPlan, worldAgent);

        // commented on 7-28
//        /* ------ Generate post context ------ */
//        plotAgent.generatePostContext(conflictGoals, resolutionGoal, storyPlan, worldAgent);

        if (isPlanningSuccessful) {
            System.out.println(". ");
            System.out.println("*------- A valid story was generated! :)");
        }
        else {
            System.out.println(". ");
            System.out.println("*------- NO valid story was generated! :(");
        }

        System.out.println(". ");
        System.out.println("Story World: ");
        System.out.println("+ Characters:");
        for (CharacterNew character : storyWorld.getCharacters()) {
            System.out.println(".    - " + character.toString());
        }

        System.out.println(". ");
        System.out.println("Story Plan: \n");
        for (FabulaNodeNew fabNodeTemp : storyPlan.getStoryFragments()) {
            fabElem = fabNodeTemp.getData();
            System.out.println("> " + fabElem.toString());
        }

        System.out.println(". ");
        System.out.println("Story Plan (detailed): \n");
        for (FabulaNodeNew fabNodeTemp : storyPlan.getStoryFragments()) {
            fabElem = fabNodeTemp.getData();
            System.out.println("====================================================");
            System.out.println("> " + fabElem.toString());
            if (!fabElem.getsPreconditions().isEmpty())
                System.out.println("+ preconditions:");
            for(String sTemp : fabElem.getsPreconditions()) {
                System.out.println(".    - \"" + sTemp + "\"");
            }
            if (!fabElem.getsPostconditions().isEmpty())
                System.out.println("+ postconditions:");
            for(String sTemp : fabElem.getsPostconditions()) {
                System.out.println(".    - \"" + sTemp + "\"");
            }
            if (!fabElem.getParamValues().isEmpty())
                System.out.println("+ parameter values:");
            for(Map.Entry<String, ParameterValueNew> pair : fabElem.getParamValues().entrySet()) {
                System.out.println(".    - " + pair.getKey() + " = " + pair.getValue().toString());
            }
        }

        return storyPlan;


        // commented on 7-24
        //contextGoalsOld = plotAgent.selectContextGoals(conflictGoals.getnId());

        // commented on 7-24
//        /* ------ Check if conflict and context goals can be met by characterIds ------ */
//        characterIds = worldAgent.getCharacterIds();
//        isContextMet = plotAgent.checkIfContextSuitsChars(characterIds);
//        isConflictMet = plotAgent.checkIfConflictSuitsChars(characterIds);
//
//        if (isContextMet && isConflictMet) {
//
//            fabulaElements = DBQueriesNew.getFabulaElements();
//            links = DBQueriesNew.getLinks();
//
//            /* ------ Link context goals ------ */
//            if (!plotAgent.linkStoryGoalsOld(contextGoalsOld, fabulaElements, links, fabNodeHead, fabNodeSupport)) {
//                throw new MissingDataException("No path has been found between the context goals. " +
//                        "Story generation terminated.");
//            }
//
//            /* ------ Search and link possible starting goals of main character ------ */
//            headChosenGoals = new ArrayList<>();
//            cgHeadTree = plotAgent.searchAndLinkStartingGoals(fabNodeHead, fabulaElements,
//                    links, headChosenGoals, worldAgent.getSetting().getnLocationConceptId(),
//                    worldAgent.getMainCharacter().getIdentifier(), worldAgent);
//
//            /* ------ (?) Search and link possible starting goals of other characterIds------ */
//            supportChosenGoals = new ArrayList<>();
//            cgSupportTree = plotAgent.searchAndLinkStartingGoals(fabNodeSupport, fabulaElements,
//                    links, supportChosenGoals, worldAgent.getSetting().getnLocationConceptId(),
//                    worldAgent.getCharacterIds().subList(1, worldAgent.getCharacterIds().size()),
//                    worldAgent);
//
//            if (!headChosenGoals.isEmpty() && !supportChosenGoals.isEmpty()) {
//                /* ------ (?) Search for executable story path of main character achieving ContextGoals.Head ------ */
//                lastHead = plotAgent.generateExecutableStoryPath(headChosenGoals, cgHeadTree.getRoot(),
//                        storyPlan, worldAgent, null);
//
//                /* ------ (?) Search for executable story path of remaining character achieving ContextGoals.Support ------ */
//                lastSupport = plotAgent.generateExecutableStoryPath(supportChosenGoals, cgSupportTree.getRoot(),
//                        storyPlan, worldAgent, null);
//
//                /* ------ (?) Search for executable story path between context goals ------ */
//                if(contextGoalsOld.getnSearchDirection() == 1) {
//                    plotAgent.generateExecutableStoryPath(new ArrayList<>(Arrays.asList(cgSupportTree.getRoot())),
//                            cgHeadTree.getRoot(), storyPlan, worldAgent, lastSupport);
//                }
//                else {
//                    plotAgent.generateExecutableStoryPath(new ArrayList<>(Arrays.asList(cgHeadTree.getRoot())),
//                            cgHeadTree.getRoot(), storyPlan, worldAgent, lastHead);
//                }
//
//
//            } else {
//                // todo throw exception
//            }



            /* ------ (7) Search for story path of secondary suitable character achieving ContextGoals.Support ------ */
//            plotAgent.generateExecutableStoryPath(supportChosenGoals, cgSupportTree.getRoot(), storyPlan, worldAgent,
//                    goalPossibleCharacters);


            // todo /* Generate path from head goal → support goal or support goal → head goal */
            // todo - Put a flag in the Context Goal KB to indicate how the planner should search for the
            // path (search the graph backward or forward).

//            plotAgent.generateExecutableStoryPath(cgHeadTree.getRoot(), cgSupportTree.getRoot(), storyPlan,
//                    worldAgent, contextGoalsOld.getnSearchDirection(), links);


            //chosenStartingGoals.add(plotAgent.checkValidityAndPickGoal(headChosenGoals, worldAgent.getMainCharacter()));
            //cgSupportTree = plotAgent.searchAndLinkStartingGoals(contextGoalsOld.getnSupport(), fabulaElements, links, supportChosenGoals, visitedNodes);
            //chosenStartingGoals.add(plotAgent.checkValidityAndPickGoal(supportChosenGoals, worldAgent.getCharacters()));
            //plotAgent.assignGoalsToRemainingChars(chosenStartingGoals, worldAgent.getCharacters());
            //plotAgent.generateExposition(storyPlan, goalTrait, contextGoalsOld, worldAgent);

            /**
             * (6)  Assign starting goals to character and initialize starting character states
             *      (a) From contextGoalsOld.head, find the uppermost/farthest of the consecutive GOALs
             *          that is after the nearest ACTION (excluding the contextGoalsOld.head if it is
             *          an ACTION). Return a list of chosen GOALs for each possible path.
             *      (b) From list in (a), choose one that is suitable to the main character's makeup
             *      (c) From contextGoalsOld.support, find the uppermost/farthest of the consecutive
             *          GOALs that is after the nearest ACTION (excluding the contextGoalsOld.support
             *          if it is an ACTION). Return a list of chosen GOALs for each possible path.
             *      (d) If the agent of contextGoalsOld.head is similar to the one in contextGoalsOld.support,
             *          choose one from list in (c) that is suitable to the main character's makeup.
             *          However, if the agents are different, assign one from the list in (c) to another
             *          suitable character.
             *      (e) If there are other characterIds, identify which of the two chosen GOALs fit
             *          them. If there is none, remove character.
             */
// commented on 7-24
//        }
    }

    // STORY PLANNING PROCESS
    // Pick a goal trait
    // Pick a desire
    // Pick a difficulty
    // Generate exposition
    // Pick an initiating incident (II)
    // Generate a series of events from II to desire
    // Generate a series of events from desire to difficulty...?


}
