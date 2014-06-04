package aad.app.hello.proguard;

import aad.app.hello.proguard.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		// Create an empty Fragment just to use something from the support library
		Fragment testFragment = new Fragment();
		testFragment.setArguments(null);
		
	}
}