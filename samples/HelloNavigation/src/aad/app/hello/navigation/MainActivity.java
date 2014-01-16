package aad.app.hello.navigation;

import aad.app.hello.navigation.fragments.AboutFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private ActionBar mActionBar;
	
	private Fragment mAboutFragment;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// We need to do this before we set the Content View		
		SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
		boolean isSplit = sp.getBoolean("isSplit", false);
				
		if (isSplit)					
			getWindow().setUiOptions(0);
		else
			getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
		
		setContentView(R.layout.activity_main);		
		
		// This is how to do it in API 11 and higher
		mActionBar = getActionBar();
		
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
			case R.id.action_about:
				mAboutFragment = getFragmentManager().findFragmentByTag("AboutFragment");
				
				if (mAboutFragment == null) {
					Log.i("@@@", "Adding AboutFragment");
					mAboutFragment = new AboutFragment();
					getFragmentManager().beginTransaction().add(R.id.mainLinearLayout, mAboutFragment, "AboutFragment").show(mAboutFragment).commit();
				}
				
			break;
		
			case R.id.action_search:
			break;
			
			case R.id.action_split:

				// Toggle the isSplit value in SharedPreferences	
				SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
				boolean isSplit = sp.getBoolean("isSplit", false);
				sp.edit().putBoolean("isSplit", !isSplit).commit();
				
				// To show this off we need to restart the activity
				Intent mainActivity = new Intent(this, MainActivity.class);
				
				int mPendingIntentId = 666;
				PendingIntent pendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mainActivity, PendingIntent.FLAG_CANCEL_CURRENT);
				
				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 150, pendingIntent);
				
				// Kill the application
				System.exit(0);
				
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_menu, menu);
	    
	    return true;
		//return super.onCreateOptionsMenu(menu);
	}
	
	
	
}