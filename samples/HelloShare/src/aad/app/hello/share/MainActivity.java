package aad.app.hello.share;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {
    
    private static String TAG = "HelloShare";
    static final int REQUEST_TAKE_PHOTO = 1;
	private ImageView mImageView;
    
	private String mCurrentPhotoPath;
	
	private ShareActionProvider mShareActionProvider;

	private File createImageFile() throws IOException {
		
	    // Create the photo file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    
	    // Get the Pictures directory
	    File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    File imageFile = File.createTempFile(imageFileName, ".jpg", pictureDirectory);

	    mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();
	    Log.i(TAG, "createImageFile() mCurrentPhotoPath: " + mCurrentPhotoPath);
	    
	    return imageFile;
	}
	
	private Intent getPhotoIntent(Uri currentPhotoUri) {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/jpg");
	    intent.putExtra(Intent.EXTRA_STREAM, currentPhotoUri);
	    return intent;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        
        setContentView(R.layout.activity_main);
        
        mImageView = (ImageView) this.findViewById(R.id.captureImageView);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
        	
        	Log.i(TAG, "onActivityResult() Successfully Took Photo");
        	
        	// If successful, set our ImageView to the newly saved image
        	Uri currentPhotoUri = Uri.parse(mCurrentPhotoPath);
        	mImageView.setImageURI(currentPhotoUri);
        	mShareActionProvider.setShareIntent(getPhotoIntent(currentPhotoUri));
        }
    }
    
    // Initialize the main menu
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        // Get the ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);       
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
                
		return true;
	}
      
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
			case R.id.menu_capture:
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			    	
			    	Log.i(TAG, "onOptionsItemSelected() Taking Photo");
			    	
			    	// Create the File where the photo should go
			        File photoFile = null;
			        try {
			            photoFile = createImageFile();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			        
			        // Start the photo activity the File was successfully created
			        if (photoFile != null) {
			            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			        }			        
			    }
				break;
							
		}
		
		return super.onOptionsItemSelected(item);
	}
        
}