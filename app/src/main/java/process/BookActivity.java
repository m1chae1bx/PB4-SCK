package process;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.PictureBooks4.R;

import java.util.ArrayList;

import database.DBFactory;
import model.storymodel.Story;
import process.helpers.DataRetriever;
import process.helpers.ImageCompiler;

public class BookActivity extends Activity {

	private String source;
	
	private Story story;
	@Override
	protected void onDestroy() {
		DBFactory.db.close();
		super.onDestroy();
		
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        DBFactory.instantiateDBHelper(this);
	    story = new Story();
		Intent intent = getIntent();
	    source = intent.getStringExtra("source");
	    story.setId(intent.getIntExtra("storyId", 0));
	    story.setTitle(intent.getStringExtra("storyTitle"));
	    story.setAuthor(intent.getStringExtra("storyAuthor"));
	    story.setImagePath(intent.getStringExtra("storyImage"));
	    story.setStoryText(intent.getStringExtra("storyText"));
	    
		setContentView(R.layout.activity_book);
		
	    loadBook();
	}
	
	public void loadBook() {
		TextView storyTextView = (TextView) findViewById(R.id.textView_story);
		TextView infoTextView = (TextView) findViewById(R.id.textView_info);
		ImageView bookImageView = (ImageView) findViewById(R.id.imageView_book);
		
		infoTextView.setText(story.getTitle() + "\nby " + story.getAuthor());
		storyTextView.setText(story.getStoryText());
		
		Drawable image;
	    if (!(story.getImagePath()).contains("drawable/")) {
	    	ImageCompiler compiler = new ImageCompiler();
	    	image = new BitmapDrawable(getResources(), compiler.getCompiledImage(this, story, story.getImagePath()));
	    }
	    else {
    	    int imageResource = getResources().getIdentifier(story.getImagePath(), null, getPackageName());
    	    image = getResources().getDrawable(imageResource);
	    }
	    bookImageView.setImageDrawable(image);
	}
	
	public void saveStory() {
		DataRetriever retriever = new DataRetriever();
		ArrayList<Story> storedStories = retriever.retrieveStories();
		int book = 0;
		
		for(Story s : storedStories) {
			if (s.getTitle().equals(story.getTitle()) && s.getAuthor().equals(story.getAuthor())) {
				book++;
				story.setTitle(story.getTitle()+Integer.toString(book));
			}
		}
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.thesis.PictureBooks4/databases/PB4", null,
				SQLiteDatabase.OPEN_READWRITE);
	    
	    story.addStory(db, story.getTitle(), story.getAuthor(), story.getImagePath(), story.getStoryText());
		
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}
	
	public void removeStory() {
		if (story.getId() > 0) {
			SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.thesis.PictureBooks4/databases/PB4", null,
					SQLiteDatabase.OPEN_READWRITE);
		    
		    story.deleteStory(db, story);
		}
		
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}
	
	public void goToLibrary(View view) {
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}
	
	public void goToCreator(View view) {
		Intent intent = new Intent(this, CreatorActivity.class);

		startActivity(intent);
	}
	
	public void savePrompt(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Saving...");
		alert.setMessage("Do you really want to save your story?");
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				saveStory();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}
	
	public void removePrompt(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Deleting...");
		alert.setMessage("Do you really want to delete your story?");
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				removeStory();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}
}