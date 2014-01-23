package aad.app.hello.actionbar;

import aad.app.hello.actionbar.fragments.AboutFragment;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements ActionMode.Callback {

	int PICK_CONTACT = 1;
	int SORT_CONTACT = 2;
	
	private ActionBar mActionBar;
	private ActionMode mActionMode;
	private Fragment mAboutFragment;
	private String mSelectedHouse;

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.sorting_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false; 
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		
			case R.id.action_people:
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, SORT_CONTACT);
				return true;
				
			default:
				return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mActionMode = null;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == PICK_CONTACT) {
			Toast.makeText(this, "Picked Someone, now where?", Toast.LENGTH_LONG).show();
		}
		
		if (requestCode == SORT_CONTACT) {
			
			// Get the URI we Picked for Sorting...
			Uri uri = data.getData();
				
			String[] projection = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
			Cursor c = MainActivity.this.getContentResolver().query(uri, projection, null, null, null);
		    while (c.moveToNext()) {
		        String displayName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		        Toast.makeText(this, "Picked " + displayName + " for " + mSelectedHouse, Toast.LENGTH_LONG).show();
		    }
		    c.close();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

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

		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		// Setup the List
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hogwarts_houses, android.R.layout.simple_list_item_single_choice);
		this.setListAdapter(adapter);
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getListView().setOnItemLongClickListener(
				new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

						// Check to see if we are already in the Contextual Action Mode
						if (mActionMode != null)
							return false;

						mSelectedHouse = (String) ((TextView) view).getText();
						mActionMode = startActionMode(MainActivity.this);
						view.setSelected(true);
						return true;
					}
				});
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_about:
			mAboutFragment = getFragmentManager().findFragmentByTag("AboutFragment");

			if (mAboutFragment == null) {
				Log.i("@@@", "Adding AboutFragment");
				mAboutFragment = new AboutFragment();
				getFragmentManager()
						.beginTransaction()
						.add(R.id.mainLinearLayout, mAboutFragment, "AboutFragment").show(mAboutFragment).commit();
			}

			break;

		case R.id.action_people:

			// Just launch the activity, we aren't really going to do anything with it
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT);

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

			// Suicide
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
	}

}