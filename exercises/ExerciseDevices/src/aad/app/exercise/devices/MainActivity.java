package aad.app.exercise.devices;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextView = (TextView) findViewById(R.id.mainTextView);
		
		StringBuilder sb = new StringBuilder();
				
		// TODO 1 Get the Device Information
		
		
		// TODO 2 Check to see if we are running in an emulator
		// Either by looking for google_sdk or vbox86p as the PRODUCT or the FINGERPRINT starting with generic
		
		
		// TODO 3 Get the Language
		
		
		
		// TODO 4 Determine if Facebook is installed
		
		
		
		// TODO 5 Check to see if we have a camera
		
		
		mTextView.setText(sb.toString());
	}

}
