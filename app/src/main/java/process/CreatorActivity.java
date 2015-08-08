package process;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thesis.PictureBooks4.R;

import java.util.ArrayList;
import java.util.List;

import database.DBQueriesNew;
import model.ontologymodel.SemanticRelation;
import model.storymodel.Story;
import model.storymodel.StoryElement;
import model.storyplanmodel.StoryPlanNew;
import model.storyworldmodel.CharacterNew;
import model.storyworldmodel.ObjectNew;
import model.storyworldmodel.SettingNew;
import model.storyworldmodel.StoryWorldNew;
import process.exceptions.DataMismatchException;
import process.exceptions.MalformedDataException;
import process.exceptions.MissingDataException;
import process.exceptions.OperationUnavailableException;
import process.helpers.DataRetriever;
import process.storyplanning.StoryPlannerNew;

public class CreatorActivity extends Activity {

    private int CHARACTER = 1;
    private int OBJECT = 2;

    private ArrayList<StoryElement> backgroundStickers;
    private ArrayList<StoryElement> charStickers;
    private ArrayList<StoryElement> objStickers;

    private Story story;

    private RelativeLayout canvasLayout;
    private LinearLayout backgroundContents;
    private LinearLayout charContents;
    private LinearLayout objContents;
    private TextView instructionsTextView;

    private int selectedType;
    private int selectedIndex;

    private int canvasWidth;
    private int canvasHeight;

    private boolean isHolding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_creator);

        story = new Story();
        story.setBackgroundElement(new StoryElement());
        story.setCharacterElements(new ArrayList<StoryElement>());
        story.setObjectElements(new ArrayList<StoryElement>());

        backgroundContents = (LinearLayout) findViewById(R.id.bg_contents);
        charContents = (LinearLayout) findViewById(R.id.char_contents);
        objContents = (LinearLayout) findViewById(R.id.obj_contents);

        canvasLayout = (RelativeLayout) findViewById(R.id.layout_canvas);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        canvasWidth = (int) (displaymetrics.widthPixels * 0.65);
        canvasHeight = (int) (canvasWidth * 0.4);
        canvasLayout.setMinimumWidth(canvasWidth);
        canvasLayout.setMinimumHeight(canvasHeight);

        DataRetriever retriever = new DataRetriever();

        backgroundStickers = retriever.retrieveBackgrounds(this);
        populateBackgrounds();

        charStickers = retriever.retrieveChars(this);
        populateChars();

        objStickers = retriever.retrieveObjs(this);
        populateObjs();

        refreshChars(findViewById(R.id.button_char));

        isHolding = false;
        instructionsTextView = (TextView) findViewById(R.id.textView_instructions);
        instructionsTextView.setText("Let's get started! First, click a background that you like.");
    }

    public void populateBackgrounds() {
        for (int i = 0; i < backgroundStickers.size(); i++) {
            ImageView backgroundButton = new ImageView(this);

            backgroundButton.setId(i);
            backgroundButton.setImageResource(backgroundStickers.get(i).getImageResource());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
            params.setMargins(10, 0, 10, 0);
            backgroundButton.setLayoutParams(params);
            backgroundButton.setAdjustViewBounds(true);

            backgroundButton.setOnClickListener(new BackgroundClickListener(this));

            backgroundContents.addView(backgroundButton);
        }
    }

    public void populateChars() {
        charContents.removeAllViews();
        for (int i = 0; i < charStickers.size(); i++) {
            ImageView charImage = new ImageView(this);

            charImage.setId(i);

            charImage.setImageResource(charStickers.get(i).getImageResource());

//    	    Resources res = getResources();
//    	    int width = (int) res.getDimension(R.dimen.character);
            int width = (int) (canvasWidth * 0.2353);
            charImage.setOnTouchListener(new CharTouchListener());

            charImage.setAdjustViewBounds(true);

            charContents.addView(charImage, new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT));
        }
    }

    public void populateObjs() {
        //populate with dummy_data
        objContents.removeAllViews();
        for (int i = 0; i < objStickers.size(); i++) {
            ImageView objImage = new ImageView(this);

            objImage.setId(i);

            objImage.setImageResource(objStickers.get(i).getImageResource());

//    	    Resources res = getResources();
//    	    int width = (int) res.getDimension(R.dimen.object);
            int width = (int) (canvasWidth * 0.1176);

            objImage.setOnTouchListener(new ObjTouchListener());

            objImage.setAdjustViewBounds(true);

            objContents.addView(objImage, new LayoutParams(width, LayoutParams.WRAP_CONTENT));
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void refreshChars(View view) {

        findViewById(R.id.scroll_objects).setVisibility(View.INVISIBLE);
        findViewById(R.id.scroll_characters).setVisibility(View.VISIBLE);

        int clickedResource = getResources().getIdentifier("drawable/button_char_clicked", null, getPackageName());
        Drawable clickedImage = getResources().getDrawable(clickedResource);
        view.setBackground(clickedImage);

        int elemResource = getResources().getIdentifier("drawable/button_obj", null, getPackageName());
        Drawable elemImage = getResources().getDrawable(elemResource);

        Button objButton = (Button) findViewById(R.id.button_obj);
        objButton.setBackground(elemImage);

        canvasLayout.setOnDragListener(new ElemDragListener(this));
        charContents.setOnDragListener(new ElemDragListener(this));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void refreshObjs(View view) {

        if (story.getBackgroundElement().getsName() != null) {
            findViewById(R.id.scroll_characters).setVisibility(View.INVISIBLE);
            findViewById(R.id.scroll_objects).setVisibility(View.VISIBLE);

            int clickedResource = getResources().getIdentifier("drawable/button_obj_clicked", null, getPackageName());
            Drawable clickedImage = getResources().getDrawable(clickedResource);
            view.setBackground(clickedImage);

            int elemResource = getResources().getIdentifier("drawable/button_char", null, getPackageName());
            Drawable elemImage = getResources().getDrawable(elemResource);

            Button objButton = (Button) findViewById(R.id.button_char);
            objButton.setBackground(elemImage);

            objContents.setOnDragListener(new ElemDragListener(this));
            canvasLayout.setOnDragListener(new ElemDragListener(this));
        }
    }

    public void goToLibrary(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void getAuthor(View view) {
        System.out.println("Background");
        System.out.println(story.getBackgroundElement().getsName() + ":" + story.getBackgroundElement().getX() + "," + story.getBackgroundElement().getY());
        System.out.println("Characters");
        for (StoryElement e : story.getCharacterElements()) {
            System.out.println(e.getsName() + ":" + e.getX() + "," + e.getY());
        }
        System.out.println("Objects");
        for (StoryElement e : story.getObjectElements()) {
            System.out.println(e.getsName() + ":" + e.getX() + "," + e.getY());
        }

        if (story.getBackgroundElement().getsName() == null) {
            // todo prompt: you must select a background first!
        } else if (story.getCharacterElements().size() < 2 || story.getCharacterElements().size() > 4) {
            // todo prompt: there must only be 2-4 characters!
        } else if (story.getObjectElements().size() > 3) {
            // todo prompt: there must only be 0-3 objects!
        } else {

            // todo remove soon
            try {
                createStory();
            } catch (MissingDataException e) {
                e.printStackTrace();
            }
            // replaces below
            // commented on Aug 8
            // todo restore after successful testing

            /*
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Creating your Story...");
            alert.setMessage("What is your name?");

            // Set an EditText view to get user input
            final EditText authorText = new EditText(this);
            authorText.setHint("Author");

            alert.setView(authorText);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String author = authorText.getText().toString();
                    // Do something with value!
                    story.setAuthor(author);
                    try {
                        createStory();
                    } catch (MissingDataException e) {
                        e.printStackTrace();
                        // todo handle missingdataexception
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();
            */
        }
    }

    // edited starting 6-20
    public void createStory() throws MissingDataException { // todo check if input is validated
        StoryWorldNew storyWorld = new StoryWorldNew();
        ArrayList<CharacterNew> characters = new ArrayList<>();
        ArrayList<ObjectNew> objects = new ArrayList<>();
        ArrayList<StoryElement> characterElements;
        StoryPlannerNew storyPlanner;
        StoryPlanNew storyPlan;
        CharacterNew c;
        int i;
        int j;
        int nGender;
        StoryElement element;
        List<List<Integer>> nStatusRelations;
        List<String> nStatusRelSimplified;
        List<Integer> nTraits;
        List<Integer> nPosTraits;
        List<Integer> nNegTraits;
        int nLocTemp;

        /* --------------- Initialize story world setting --------------- */
        element = story.getBackgroundElement();
        storyWorld.setSetting(new SettingNew(element.getnId(), element.getsName(),
                element.getnConceptId(), element.getsImagePath())); // todo params
        nLocTemp = element.getnConceptId();

        /* --------------- Initialize story world characters --------------- */
        characterElements = story.getCharacterElements();
        for (i = 0; i < characterElements.size(); i++) {
            element = characterElements.get(i);

            // Set basic info
            c = new CharacterNew(element.getnId(),
                    element.getnConceptId(), element.getsName(),
                    element.getsImagePath(), nLocTemp);

            // Set gender
            try {
                nGender = (DBQueriesNew.getRelatedRightHandConcepts(element.getnConceptId(),
                        SemanticRelation.GENDER_IS)).get(0);
                c.setnGender(nGender);
            } catch (IndexOutOfBoundsException e) {
                throw new MissingDataException("There is no gender specified in the knowledge " +
                        "base for character '" + c.getsName() + "'.");
            }

            // Set negative and positive traits
            nTraits = DBQueriesNew.getRelatedRightHandConcepts(element.getnConceptId(),
                    SemanticRelation.HAS_TRAIT);
            nPosTraits = new ArrayList<>();
            nNegTraits = new ArrayList<>();
            for (int nTrait : nTraits) {
                try {
                    j = DBQueriesNew.getRelatedRightHandConcepts(nTrait,
                            SemanticRelation.POLARITY).get(0);
                    if (DBQueriesNew.getConceptName(j).equals("positive"))
                        nPosTraits.add(nTrait);
                    else
                        nNegTraits.add(nTrait);
                } catch (IndexOutOfBoundsException e) {
                    throw new MissingDataException("Missing character trait information (trait " + nTrait + ") in the knowledge base.");
                }
            }
            c.setnPositiveTraits(nPosTraits);
            c.setnNegativeTraits(nNegTraits);

            // Set preferences
            c.setnPreferences(DBQueriesNew.getRelatedRightHandConcepts(element.getnConceptId(),
                    SemanticRelation.PREFERS));

            // Set status relations
            nStatusRelations = DBQueriesNew.getRelatedRightHandTernaryConcepts(
                    element.getnConceptId(), SemanticRelation.OCCUPIES_STATUS);
            nStatusRelSimplified = new ArrayList<>();
            try {
                for (List<Integer> temp : nStatusRelations)
                    nStatusRelSimplified.add(temp.get(0) + ":" + temp.get(1));
            } catch (Exception e) {
                throw new MissingDataException("There are no status relations for character '" +
                        c.getsName() + "'.");
            }
            c.setsStatusRelations(nStatusRelSimplified);

            // Add new character to list
            characters.add(c);
        }
        storyWorld.setCharacters(characters);

        /* --------------- Initialize story world objects --------------- */
        for (StoryElement e : story.getObjectElements()) {
            objects.add(new ObjectNew(e.getnId(), e.getnConceptId(), e.getsName(), e.getsImagePath()));
        }
        storyWorld.setObjects(objects);

        /* --------------- Plan story --------------- */
        storyPlanner = new StoryPlannerNew();
        try {
            storyPlan = storyPlanner.planStory(storyWorld);
        } catch (MalformedDataException | CloneNotSupportedException | MissingDataException |
                DataMismatchException | OperationUnavailableException e) {
            // todo handle exception
            e.printStackTrace();
        }

        // temporary, log story plan first
        // todo log story plan

// 		commented on 6-20 finish story plan first
//    	try {
//	    	StoryGenerator storygenerator = new StoryGenerator(storyPlan);
//
//	    	String generatedStory = storygenerator.generateStory();
//	    	story.setTitle(storygenerator.generateTitle());
//	    	Log.i("STORY GENERATOR", generatedStory);
//
//	    	ImageCompiler compiler = new ImageCompiler();
//	    	String imagePath = compiler.compileImage(this, story, canvasLayout.getHeight(), canvasLayout.getWidth());
//
//			Intent intent = new Intent(this, BookActivity.class);
//
//			intent.putExtra("source", "CreatorActivity");
//			intent.putExtra("storyId", 0);
//			intent.putExtra("storyTitle", story.getTitle());
//			intent.putExtra("storyAuthor", story.getAuthor());
//			intent.putExtra("storyImage", imagePath);
//			intent.putExtra("storyText", generatedStory);
//
//			startActivity(intent);
//
//    	} catch(NullPointerException npe) {
//
//    		AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//			alert.setTitle("Creating your Story...");
//			alert.setMessage("No stories can be generated.");
//
//			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int whichButton) {
//
//				}
//			});
//
//			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			  public void onClick(DialogInterface dialog, int whichButton) {
//			    // Canceled.
//			  }
//			});
//
//			alert.show();
//
//
//    	}

    }

    public class BackgroundClickListener implements OnClickListener {

        Context context;

        public BackgroundClickListener(Context context) {
            this.context = context;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            int index = view.getId();
            Drawable image = getResources().getDrawable(backgroundStickers.get(index).getImageResource());
            canvasLayout.setBackground(image);

            DataRetriever retriever = new DataRetriever();
            objStickers = retriever.retrieveObjsOnBg(context, backgroundStickers.get(index));
            populateObjs();

            ((TextView) findViewById(R.id.textView_info)).setText(backgroundStickers.get(index).getsName() + "\n" + backgroundStickers.get(index).getsDescription());
            canvasLayout.removeAllViews();
            populateChars();
            refreshChars(view);
            populateObjs();
            story.setObjectElements(new ArrayList<StoryElement>());
            story.setCharacterElements(new ArrayList<StoryElement>());
//			int subtrahend = 0;
//			for (int i = 0; i < story.getObjectElements().size(); i++) {
//				canvasLayout.removeViewAt(story.getObjectElements().get(i).getnLocationId()-subtrahend);
//				story.removeObjectElement(story.getObjectElements().get(i));
//				subtrahend++;
//			}

            story.setBackgroundElement(backgroundStickers.get(index));

            instructionsTextView.setText("Good! Second, drag a character of your choice and drop it into the working area!");
        }
    }

    public class CharTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (!isHolding) {
                selectedIndex = view.getId();
                selectedType = CHARACTER;

                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);

//		        commented on 6-20
// 				String gender;
//		        if(charStickers.get(selectedIndex).getGender() == Character.MALE ){
//		        	gender = "Male";
//		        }else{
//		        	gender = "Female";
//		        }

                ((TextView) findViewById(R.id.textView_info)).setText(charStickers.get(selectedIndex).getsName());

                return true;
            }
            return false;
        }
    }

    public class ObjTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (!isHolding && story.getBackgroundElement().getsName() != null) {
                selectedIndex = view.getId();
                selectedType = OBJECT;

                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);

                ((TextView) findViewById(R.id.textView_info)).setText(objStickers.get(selectedIndex).getsName() + "\n" + objStickers.get(selectedIndex).getsDescription());

                return true;
            }
            return false;
        }
    }

    public class ElemDragListener implements OnDragListener {
        Context context;

        public ElemDragListener(Context context) {
            this.context = context;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onDrag(View to, DragEvent e) {
            ImageView sticker = (ImageView) e.getLocalState();
            ViewGroup from = (ViewGroup) sticker.getParent();
            ViewGroup drop = (ViewGroup) to;
            int imgResource;
            Drawable imgDrawable;

            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    isHolding = true;
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    if (to == findViewById(R.id.layout_canvas)) {
                        if (selectedType == CHARACTER) {
                            if (from == findViewById(R.id.char_contents)) {
                                if (story.getCharacterElements().size() < 4) {

                                    charStickers.get(selectedIndex).setX((int) (e.getX() - (sticker.getWidth() / 2)));
                                    charStickers.get(selectedIndex).setY((int) (e.getY() - (sticker.getHeight() / 2)));
                                    story.addCharacterElement(charStickers.get(selectedIndex));

                                    from.removeView(sticker);

                                    sticker.setX(charStickers.get(selectedIndex).getX());
                                    sticker.setY(charStickers.get(selectedIndex).getY());
                                    canvasLayout.addView(sticker);
                                } else if (story.getCharacterElements().size() >= 4) {
                                    instructionsTextView.setText("I'm so sorry. You are only allowed to have a maximum of 4 characters in the working area. :(");
                                }
                            } else {
                                from.removeView(sticker);

                                sticker.setX(e.getX() - (sticker.getWidth() / 2));
                                sticker.setY(e.getY() - (sticker.getHeight() / 2));
                                canvasLayout.addView(sticker);
                                story.modifyCharacterLocation(charStickers.get(selectedIndex), (int) e.getX(), (int) e.getY());
                            }
                        } else if (selectedType == OBJECT) {
                            if (from == findViewById(R.id.obj_contents)) {
                                if (story.getObjectElements().size() < 4) {
                                    from.removeView(sticker);

                                    objStickers.get(selectedIndex).setX((int) e.getX());
                                    objStickers.get(selectedIndex).setY((int) e.getY());
                                    story.addObjectElement(objStickers.get(selectedIndex));

                                    sticker.setX(e.getX() - (sticker.getWidth() / 2));
                                    sticker.setY(e.getY() - (sticker.getHeight() / 2));
                                    canvasLayout.addView(sticker);
                                } else if (selectedType == OBJECT && story.getObjectElements().size() == 3) {
                                    instructionsTextView.setText("I'm so sorry. You are only allowed to have a maximum of 3 objects in the working area. :(");
                                }
                            } else {
                                from.removeView(sticker);

                                sticker.setX(e.getX() - (sticker.getWidth() / 2));
                                sticker.setY(e.getY() - (sticker.getHeight() / 2));
                                canvasLayout.addView(sticker);
                                story.modifyCharacterLocation(objStickers.get(selectedIndex), (int) e.getX(), (int) e.getY());
                                story.modifyCharacterLocation(objStickers.get(selectedIndex), (int) e.getX(), (int) e.getY());
                            }
                        }
                    } else if (to == findViewById(R.id.char_contents) || to == findViewById(R.id.obj_contents)) {
                        if (from != findViewById(R.id.char_contents) && from != findViewById(R.id.obj_contents)) {
                            from.removeView(sticker);

//							sticker.layout(0,0,0,0);
//							sticker.setnLocationId(from.getChildCount());
                            sticker.setX(0);
                            sticker.setY(0);
                            if (selectedType == CHARACTER) {
                                charContents.addView(sticker);
                                story.removeCharacterElement(charStickers.get(selectedIndex));
                            } else if (selectedType == OBJECT) {
                                objContents.addView(sticker);
                                story.removeObjectElement(objStickers.get(selectedIndex));
                            }
                        }
                    }
                    sticker.setVisibility(View.VISIBLE);

                    imgResource = R.drawable.textview_canvas;
                    imgDrawable = getResources().getDrawable(imgResource);
                    if (selectedType == CHARACTER) {
                        charContents.setBackground(imgDrawable);
                    } else if (selectedType == OBJECT) {
                        objContents.setBackground(imgDrawable);
                    }

                    return true;
                case DragEvent.ACTION_DRAG_ENDED:

                    if (canvasLayout.getChildCount() > 0) {
                        int n = (int) canvasLayout.getChildAt(canvasLayout.getChildCount() - 1).getY();
                        if (n / Math.abs(n) == -1) {
                            if (selectedType == CHARACTER) {
                                canvasLayout.getChildAt(canvasLayout.getChildCount() - 1).setX(story.getCharacterElements().get(story.getCharacterElements().size() - 1).getX());
                                canvasLayout.getChildAt(canvasLayout.getChildCount() - 1).setY(story.getCharacterElements().get(story.getCharacterElements().size() - 1).getY());
                            } else if (selectedType == OBJECT) {
                                canvasLayout.getChildAt(canvasLayout.getChildCount() - 1).setX(story.getObjectElements().get(story.getObjectElements().size() - 1).getX());
                                canvasLayout.getChildAt(canvasLayout.getChildCount() - 1).setY(story.getObjectElements().get(story.getObjectElements().size() - 1).getY());
                            }
                        }
                    }

                    System.out.println("Elements in canvas");
                    for (int i = 0; i < ((ViewGroup) findViewById(R.id.layout_canvas)).getChildCount(); i++) {
                        System.out.println(((ViewGroup) findViewById(R.id.layout_canvas)).getChildAt(i).getId() + ": " + ((ViewGroup) findViewById(R.id.layout_canvas)).getChildAt(i).getX() + ", " + ((ViewGroup) findViewById(R.id.layout_canvas)).getChildAt(i).getY());
                    }

                    sticker.setVisibility(View.VISIBLE);
                    isHolding = false;
                    if (selectedType == CHARACTER) {
                        if (story.getCharacterElements().size() == 1)
                            instructionsTextView.setText("Good choice of character! You should put at least 2 characters into the working area.");
                        else if (story.getCharacterElements().size() == 2)
                            instructionsTextView.setText("That's great! Now, you may drag an object of your choice and drop it into the working area to enhance your story.");
                    } else if (selectedType == OBJECT) {
                        if (story.getObjectElements().size() == 1)
                            instructionsTextView.setText("Wonderful! Now, click Create Button to create your story!");
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
