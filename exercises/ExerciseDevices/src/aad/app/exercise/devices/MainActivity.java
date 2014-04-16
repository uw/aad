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
		sb.append("Manufacturer: " + Build.MANUFACTURER);
		sb.append("\nModel: " + Build.MODEL);
		sb.append("\nAPI Level: " + Build.VERSION.SDK_INT);
		
		// TODO 2 Check to see if we are running in an emulator
		// Either by looking for google_sdk or vbox86p as the PRODUCT or the FINGERPRINT starting with generic		
		boolean isEmulator = Build.PRODUCT.equalsIgnoreCase("google_sdk");
		sb.append("\nIs in emulator: " + isEmulator);
		
		// TODO 3 Get the Language
		Locale defaultLocale = Locale.getDefault();
		sb.append("\nLocale: " + defaultLocale.getDisplayLanguage());
		
		// TODO 4 Determine if Facebook is installed
		try {
			getPackageManager().getPackageInfo("com.facebook.katana", 0);
			sb.append("\nFacebook is installed");
		} catch (NameNotFoundException e) {
			sb.append("\nFacebook is not installed");
		}
		
		
		// TODO 5 Check to see if we have a camera
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    String cameraPackageName = intent.resolveActivity(getPackageManager()).getPackageName();
		try {
			getPackageManager().getPackageInfo(cameraPackageName, PackageManager.GET_META_DATA);
			sb.append("\nA camera is available");
    	} catch (NameNotFoundException e) {
    		sb.append("\nA camera is not available");
    	}

		
		mTextView.setText(sb.toString());
	}

}
