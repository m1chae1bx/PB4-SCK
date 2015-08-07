package process.storyrealisation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import model.storyplanmodel.Agent;
import model.storyplanmodel.Event;
import model.storyplanmodel.Patient;
import model.storyplanmodel.StoryPlan;
import model.storyworldmodel.Character;
import model.storyworldmodel.Element;
import model.storyworldmodel.StoryWorldOld;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class SurfaceRealiser {

    static Lexicon lexicon = Lexicon.getDefaultLexicon();
    static NLGFactory nlgFactory = new NLGFactory(lexicon);
    static Realiser realiser = new Realiser(lexicon);
    static String storyTitle;
    static StoryPlan storyPlan;
    static DocumentElement section;
    static Random randomGenerator = new Random();

    public static CoordinatedPhraseElement coordinateCharacter(ArrayList<Element> characterList) {
        CoordinatedPhraseElement subject = nlgFactory.createCoordinatedPhrase();
        for (Element c : characterList) {

            subject.addCoordinate(c.getName());
        }
        return subject;

    }

    public static DocumentElement generateIntro() {
        DocumentElement paragraph = nlgFactory.createParagraph();
        StoryWorldOld storyworld = storyPlan.getInitialStoryWorld();


        NPPhraseSpec np = nlgFactory.createNounPhrase("a", storyworld.getBackground().getTimeDesc());
        NLGElement elem = realiser.realise(np);
        String bgDesc = elem.toString();

        DocumentElement template = nlgFactory.createSentence("It was " + bgDesc + " " + storyworld.getBackground().getTime());
        paragraph.addComponent(template);


        SPhraseSpec sentence = nlgFactory.createClause();

        ArrayList<Element> characterElements = new ArrayList<Element>();
        for (Character c : storyworld.getCharacters()) {
            characterElements.add(c);
        }

        sentence.setSubject(coordinateCharacter(characterElements));
        sentence.setVerb("is");
        sentence.addComplement("in the " + storyworld.getBackground().getName());
        sentence.setFeature(Feature.TENSE, Tense.PAST);
        paragraph.addComponent(sentence);

        DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan, Dialogue.INTRODUCTION);
        Log.i("Finish", "intro");
        return paragraph;
    }

    public static DocumentElement generateResolution() {
        DocumentElement paragraph = nlgFactory.createParagraph();
        StoryWorldOld storyworld = storyPlan.getInitialStoryWorld();

        DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan, Dialogue.RESOLUTION);

        DocumentElement template = nlgFactory.createSentence("From then on, " + storyworld.getMainCharacter().getName() + " learned to be " + storyPlan.getTheme().getMoralLesson());
        paragraph.addComponent(template);


        Log.i("Finish", "resolution");
        return paragraph;
    }


    public static DocumentElement generateBody() {
        DocumentElement paragraph = nlgFactory.createParagraph();


        for (int i = 0; i < storyPlan.getStoryEvents().size(); i++) {
            SPhraseSpec sentence = nlgFactory.createClause();
            String[] dType = null;
            String dialogueType = "";
            boolean hasDialogue = false;
            if (storyPlan.getStoryEvents().get(i).getDialogueType() != null) {

                Log.i("DialogueType", "Event : " + storyPlan.getStoryEvents().get(i).getAction());
                Log.i("DialogueType", "DialogueTypes : " + storyPlan.getStoryEvents().get(i).getDialogueType());

                dType = storyPlan.getStoryEvents().get(i).getDialogueType().split(",");
                dialogueType = dType[randomGenerator.nextInt(dType.length)];
                Log.i("DialogueType", "Selected DialogueType : " + dialogueType);
                hasDialogue = true;
            }

            if (storyPlan.getStoryEvents().get(i).getAction().equals(storyPlan.getConflict().getAction()) && (dialogueType.equals(Dialogue.DELIBERATION) || dialogueType.equals(Dialogue.PERSUASION)) && storyPlan.getStoryEvents().get(i).getMainAgent().equals(storyPlan.getInitialStoryWorld().getMainCharacter())) {

                DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan.getStoryEvents().get(i), storyPlan, dialogueType);

            } else if ((dialogueType.equals(Dialogue.DELIBERATION) || dialogueType.equals(Dialogue.PERSUASION)) && hasDialogue && randomGenerator.nextInt(2) == 1) {
                DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan.getStoryEvents().get(i), storyPlan, dialogueType);
                hasDialogue = false;

            }

            //agent
            CoordinatedPhraseElement subject = nlgFactory.createCoordinatedPhrase();
            for (Agent c : storyPlan.getStoryEvents().get(i).getAgents()) {

                subject.addCoordinate(c.getName());
            }


            //action
            sentence.setVerb(storyPlan.getStoryEvents().get(i).getAction());

            //patient
            if (storyPlan.getStoryEvents().get(i).getPatients() != null) {
                if (storyPlan.getStoryEvents().get(i).getEventType().equals(Event.TYPE_AGENTS_SUPPORT)) {
                    for (Patient c : storyPlan.getStoryEvents().get(i).getPatients()) {
                        subject.addCoordinate(c.getName());
                    }
                } else {
                    ArrayList<Patient> patientList = storyPlan.getStoryEvents().get(i).getPatients();
                    CoordinatedPhraseElement cp = nlgFactory.createCoordinatedPhrase();

                    ArrayList<Element> elementList = new ArrayList<Element>();
                    for (Patient p : patientList) {
                        if (p instanceof model.storyworldmodel.Object) {
                            cp.addCoordinate("the " + p.getName());
                        } else {
                            cp.addCoordinate(p.getName());
                        }
                    }
                    sentence.addComplement(cp);
                }
            }

            sentence.setSubject(subject);

            //object / instrument
            Log.i("GENERATOR", storyPlan.getStoryEvents().get(i).getAction() + " " + storyPlan.getStoryEvents().get(i).getInstrument());
            if (storyPlan.getStoryEvents().get(i).getInstrument() != null) {

                Log.i("GENERATOR", storyPlan.getStoryEvents().get(i).getAction() + " " + storyPlan.getStoryEvents().get(i).getInstrument() + "*");

                if (randomGenerator.nextBoolean())
                    sentence.addComplement("using the " + storyPlan.getStoryEvents().get(i).getInstrument().getName());
                else
                    sentence.addComplement("with the " + storyPlan.getStoryEvents().get(i).getInstrument().getName());
            }


            sentence.setFeature(Feature.TENSE, Tense.PAST);
            paragraph.addComponent(sentence);
            //Log.i("storygen", realiser.realise(sentence).getRealisation());


            //post condition
            for (String s : storyPlan.getStoryEvents().get(i).getPostconditions()) {

                StringTokenizer tokenizer = new StringTokenizer(s, ":");
                tokenizer.nextToken();

                //dialogue for post condition
                if (randomGenerator.nextInt(1) == 0 && !s.contains("asleep") && tokenizer.hasMoreTokens()) {
                    DialogueGenerator.generateEmotionDialogue(nlgFactory, realiser, paragraph, storyPlan.getStoryEvents().get(i), s);

                } else {
                    sentence = nlgFactory.createClause();
                    CoordinatedPhraseElement complement = nlgFactory.createCoordinatedPhrase();

                    if (s.contains("character:") && tokenizer.hasMoreTokens()) {
                        if (storyPlan.getStoryEvents().get(i).getPatients() != null) {
                            NPPhraseSpec np = nlgFactory.createNounPhrase();
                            np.setNoun("they");
                            np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
                            np.setFeature(Feature.PERSON, Person.THIRD);

                            sentence.setSubject(np);


                            while (tokenizer.hasMoreTokens()) {

                                complement.addCoordinate(tokenizer.nextToken().toString());
                            }


                        }

                    } else if (s.contains("agent:") && tokenizer.hasMoreTokens()) {

                        if (storyPlan.getStoryEvents().get(i).getAgents().size() == 1) {
                            NPPhraseSpec np = nlgFactory.createNounPhrase();

                            if (((Character) storyPlan.getStoryEvents().get(i).getAgents().get(0)).getGender() == (Character.FEMALE))
                                np.setNoun("she");
                            else
                                np.setNoun("he");
                            np.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);


                            np.setFeature(Feature.PERSON, Person.THIRD);
                            sentence.setSubject(np);
                        } else {

                            boolean hasPatientChar = false;
                            if (storyPlan.getStoryEvents().get(i).getPatients() != null) {
                                for (Patient p : storyPlan.getStoryEvents().get(i).getPatients()) {
                                    if (p instanceof Character) {
                                        hasPatientChar = true;
                                        break;

                                    }
                                }
                            }
                            if (hasPatientChar) {
                                CoordinatedPhraseElement coordinatedphrase = nlgFactory.createCoordinatedPhrase();
                                for (Agent a : storyPlan.getStoryEvents().get(i).getAgents()) {
                                    coordinatedphrase.addCoordinate(a.getName());

                                }
                                sentence.setSubject(coordinatedphrase);

                            } else {
                                NPPhraseSpec np = nlgFactory.createNounPhrase();
                                np.setNoun("they");
                                np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
                                np.setFeature(Feature.PERSON, Person.THIRD);
                                sentence.setSubject(np);
                            }

                        }

                        while (tokenizer.hasMoreTokens()) {
                            complement.addCoordinate(tokenizer.nextToken().toString());
                        }


                    } else if (s.contains("patient:") && tokenizer.hasMoreTokens()) {
                        if (storyPlan.getStoryEvents().get(i).getPatients() != null) {
                            ArrayList<Patient> patientList = storyPlan.getStoryEvents().get(i).getPatients();
                            ArrayList<Element> characterList = new ArrayList<Element>();
                            for (Patient p : patientList) {
                                if (p instanceof Character) {
                                    characterList.add((Character) p);
                                }
                            }

                            sentence.addComplement(coordinateCharacter(characterList));

                            while (tokenizer.hasMoreTokens()) {

                                complement.addCoordinate(tokenizer.nextToken().toString());
                            }

                        }

                    } else continue;


                    if (randomGenerator.nextInt(2) == 1 && !s.contains("asleep")) {
                        sentence.setVerb("feel");
                    } else {
                        if (randomGenerator.nextInt(2) == 1 && s.contains("asleep")) {
                            sentence.setVerb("fell");
                        } else {
                            sentence.setVerb("is");
                        }

                    }

                    sentence.setComplement(complement);
                    sentence.setFeature(Feature.TENSE, Tense.PAST);


                    //Log.i("storygen", realiser.realise(sentence).getRealisation());
                    paragraph.addComponent(sentence);
                }

            }

            if (storyPlan.getStoryEvents().get(i).getAction().equals(storyPlan.getConflict().getAction()) && dialogueType.equals(Dialogue.NEGOTIATION) && (storyPlan.getStoryEvents().get(i).getMainAgent().equals(storyPlan.getInitialStoryWorld().getMainCharacter()))) {

                DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan.getStoryEvents().get(i), storyPlan, dialogueType);

            } else if (dialogueType.equals(Dialogue.NEGOTIATION) && hasDialogue && randomGenerator.nextInt(2) == 1) {
                DialogueGenerator.generateDialogue(nlgFactory, realiser, paragraph, storyPlan.getStoryEvents().get(i), storyPlan, dialogueType);
                hasDialogue = false;

            }


        }
        Log.i("Finish", "bodyfinish");

        return paragraph;


    }

    public static String getOutputStory(StoryPlan x) {
        storyPlan = x;

        storyTitle = storyPlan.getInitialStoryWorld().getMainCharacter().getName() + " learns to be " + storyPlan.getTheme().getMoralLesson();

        section = nlgFactory.createSection(storyTitle);

        section.addComponent(generateIntro());
        section.addComponent(generateBody());
        section.addComponent(generateResolution());
        String realisedStory = realiser.realise(section).getRealisation();
        Log.i("storygen", realisedStory);
        return realisedStory;

    }

    public static String getStoryTitle() {
        return storyTitle;
    }

}
