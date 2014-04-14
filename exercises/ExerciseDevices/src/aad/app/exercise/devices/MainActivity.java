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
		sb.append("Manufacturer: ");
		sb.append(Build.MANUFACTURER);
		sb.append(System.getProperty("line.separator"));
		sb.append("Model: ");
		sb.append(Build.MODEL);		
		sb.append(System.getProperty("line.separator"));
		sb.append("SDK: ");
		sb.append(Build.VERSION.SDK_INT);
		
		
		// TODO 2 Check to see if we are running in an emulator
		// Either by looking for google_sdk or vbox86p as the PRODUCT or the FINGERPRINT starting with generic
		
		sb.append(System.getProperty("line.separator"));
		sb.append("Product: ");
		String product = Build.PRODUCT;
		sb.append(product);
		sb.append(System.getProperty("line.separator"));
		sb.append("Fingerprint: ");
		String fingerprint = Build.FINGERPRINT;
		sb.append(Build.FINGERPRINT);
		
		sb.append(System.getProperty("line.separator"));
		if (product.startsWith("vbox") || product.equalsIgnoreCase("google_sdk") || fingerprint.startsWith("generic"))
			sb.append("This is an Emulator");	
		else
			sb.append("This is not an Emulator");
		
		
		// TODO 3 Get the Language
		sb.append(System.getProperty("line.separator"));
		sb.append("Default Language: ");
		sb.append(Locale.getDefault().getDisplayLanguage());
		
		
		// TODO 4 Determine if Facebook is installed
		sb.append(System.getProperty("line.separator"));		
		PackageManager pm = getPackageManager();
		try {
			pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
			sb.append("Facebook is installed");
    	} catch (NameNotFoundException e) {
    		sb.append("Facebook is not installed");
    	}
		
		
		// TODO 5 Check to see if we have a camera
		sb.append(System.getProperty("line.separator"));
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    String cameraPackageName = intent.resolveActivity(pm).getPackageName();
		try {
			pm.getPackageInfo(cameraPackageName, PackageManager.GET_META_DATA);
			sb.append("A camera is available");
    	} catch (NameNotFoundException e) {
    		sb.append("A camera is not available");
    	}
		
		mTextView.setText(sb.toString());
	}

}
