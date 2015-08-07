package process.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import model.storymodel.Story;
import model.storymodel.StoryElement;

public class ImageCompiler {

	private int resX = 1280;
	private int resY = 600;
	private int charX = 300;
	private int charY = 300;
	private int objX = 120;
	private int objY = 120;

	private int canvasWidth;
	private int canvasHeight;
	
	public String compileImage(Context context, Story story, int canvasHeight, int canvasWidth) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		
		String filePath = "";
		String fileName = "";
		
		Bitmap bitmap = Bitmap.createBitmap(resX, resY, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), story.getBackgroundElement().getImageResource()), resX, resY, false), 0, 0, null);
		for (StoryElement c : story.getCharacterElements()) {
			canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), c.getImageResource()), charX, charY, false), c.getX()*(resX/canvasWidth), c.getY()*(resY/canvasHeight), null);
		}
		for (StoryElement o : story.getObjectElements()) {
			canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), o.getImageResource()), objX, objY, false), o.getX()*(resX/canvasWidth), o.getY()*(resY/canvasHeight), null);			
		}
		
		if(bitmap == null) {
            System.out.println("NULL bitmap save\n");
        }
		
		try {
			 filePath = Environment.getExternalStorageDirectory().toString() + "/PictureBooks4";
			 File folder = new File(filePath);
			 
			 if (!folder.exists()) {	
				 System.out.println(folder.mkdirs() + filePath);
			 }

			 fileName = story.getTitle() + "_" + story.getAuthor() + ".png";
			 File file = new File(folder, fileName);
			 if (!file.exists()) {
				 try {
					 file.createNewFile();
				 } catch (IOException e) {
					 e.printStackTrace();
					 System.out.println("Can't create file!!!!!");
				 }
			 }
			 FileOutputStream fileOutputstream = new FileOutputStream(file);
			
			 bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputstream);
			 fileOutputstream.flush();
			 fileOutputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Image not saved!!!!!");
		}
		
		return filePath + "/" + fileName;
	}
	
	public Bitmap getCompiledImage(Context context, Story story, String fullPath) {

		Bitmap imageBitmap = null;

		try {
			imageBitmap = BitmapFactory.decodeFile(fullPath);
		} catch (Exception e) {
			Log.e("getThumbnail() on external storage", e.getMessage());
		}

		// If no file on external storage, look in internal storage
		if (imageBitmap == null) {
			try {
				File fileName = context.getFileStreamPath(story.getTitle() + "_" + story.getAuthor() + ".png");
				FileInputStream inputStream = new FileInputStream(fileName);
				imageBitmap = BitmapFactory.decodeStream(inputStream);
			} catch (Exception ex) {
				Log.e("getThumbnail() on internal storage", ex.getMessage());
			}
		}
		return imageBitmap;
	}
}