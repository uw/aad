package aad.app.hello.proguard;

import aad.app.hello.proguard.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class MainActivity extends Activity {

	private static String TAG = "HelloProGuard";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		// Create an empty Fragment just to use something from the support library
		Fragment testFragment = new Fragment();
		testFragment.setArguments(null);
		
		Log.d(TAG, "DEBUG LOGGING");
		Log.v(TAG, "VERBOSE LOGGING");
		Log.i(TAG, "INFORMATION LOGGING");
		Log.w(TAG, "WARN LOGGING");
		Log.e(TAG, "ERROR LOGGING");
		
	}
}