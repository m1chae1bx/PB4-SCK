package process;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thesis.PictureBooks4.R;

import java.util.ArrayList;

import database.DBFactory;
import model.storymodel.Story;
import process.helpers.DataRetriever;
import process.helpers.ImageCompiler;

public class MainActivity extends Activity {	
	public static final String TAG = "CREATE STORY";
	private int selectedIndex;
	private ArrayList<Story> storyList;
	private RelativeLayout bookContents;
	
	@Override
	protected void onDestroy() {
		DBFactory.db.close();
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DataRetriever retriever;

		if(getIntent().getBooleanExtra("Exit", false)){
		    finish();
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);

        DBFactory.instantiateDBHelper(this);
		retriever = new DataRetriever();

		storyList = retriever.retrieveStories();
		bookContents = (RelativeLayout) findViewById(R.id.book_contents);
	    populateShelf();

		//populateDB();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void populateShelf() {
		//populate with dummy_data
		int left = 0;
		int top = 0;
		for(int i = 0; i < storyList.size(); i++) {
    	    Resources res = getResources();
    	    int width = (int) res.getDimension(R.dimen.story_width);
    	    int height = (int) res.getDimension(R.dimen.story_height);
    	    int leftMargin = (int) res.getDimension(R.dimen.horizontal_margin);
    	    int topMargin = (int) res.getDimension(R.dimen.shelf_division_margin);
    	    
			Button bookButton = new Button(this);
			bookButton.setId(i);
    	    
    	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
    	    params.setMargins(left, top, 0, 0);
    	    bookButton.setLayoutParams(params);

    	    int fontSize = (int) res.getDimension(R.dimen.font_size);
    	    
    	    TextView title = new TextView(this);
    	    title.setText(storyList.get(i).getTitle() + "\nby " + storyList.get(i).getAuthor());
    	    
    	    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width, height);
    	    params2.setMargins(left, top + height, 0, 0);
    	    title.setLayoutParams(params2);
    	    title.setTextColor(Color.WHITE);
    	    title.setTextSize(Color.WHITE);
    	    
    	    if (i % 2 != 0) {
    	    	left += width + leftMargin;
    	    	top = 0;
    	    }
    	    else
    	    	top = height + topMargin;
    	    
			//set picture book as button's bg
    	    Drawable image;
    	    if (!(storyList.get(i).getImagePath()).contains("drawable/")) {
    	    	ImageCompiler compiler = new ImageCompiler();
    	    	image = new BitmapDrawable(getResources(), compiler.getCompiledImage(this, storyList.get(i), storyList.get(i).getImagePath()));
    	    }
    	    else {
	    	    int imageResource = getResources().getIdentifier(storyList.get(i).getImagePath(), null, getPackageName());
	    	    image = getResources().getDrawable(imageResource);
    	    }
    	    bookButton.setBackground(image);
    	    bookButton.setOnClickListener(new BookClickListener());
    	    
    	    bookContents.addView(bookButton);
    	    bookContents.addView(title);
     	}
	}
	
	public void openBook(View view) {

	    selectedIndex = view.getId();
		Intent intent = new Intent(this, BookActivity.class);

		intent.putExtra("source", "MainActivity");
		intent.putExtra("storyId", storyList.get(selectedIndex).getId());
		intent.putExtra("storyTitle", storyList.get(selectedIndex).getTitle());
		intent.putExtra("storyAuthor", storyList.get(selectedIndex).getAuthor());
		intent.putExtra("storyImage", storyList.get(selectedIndex).getImagePath());
		intent.putExtra("storyText", storyList.get(selectedIndex).getStoryText());

		startActivity(intent);
	}
	
	public void goToCreator(View view) {
		Intent intent = new Intent(this, CreatorActivity.class);

		startActivity(intent);
	}
	
	public void exitApp(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.putExtra("Exit", true);
		startActivity(intent);
		finish();
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedBook(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public ArrayList<Story> getStoryList() {
		return storyList;
	}

	public void setStoryList(ArrayList<Story> storyList) {
		this.storyList = storyList;
	}

	public class BookClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			openBook(view);
		}
	}
}