package aad.app.exercise.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// TODO Inflate our menu
		
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// TODO Handle a MenuItem selection
		
		return super.onOptionsItemSelected(item);
	}
	
	

}
