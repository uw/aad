package aad.app.exercise.search;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.Toast;

// TODO 1.1 Implement the LoaderCallbacks for a Cursor Loader
public class MainActivity extends Activity {

	public static String TAG = "ExerciseSearch";
	
	// TODO 1.3 Define the Loader ID
	
		
	// TODO 3.1 Set a SimpleCursorAdapter to use
	
	
	// TODO 4.1 Set an AutoCompleteTextView
	
	
	// TODO 2.1 Create our ContactsContract projection
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO 3.2 Create a SimpleCursorAdapter - Inline
		
	
		// TODO 3.3 Set the Cursor String conversion
		
		
		// TODO 4.2 Get a reference to the AutoCompleteTextView
		

		// TODO 4.3 Set the Adapter on the AutoCompleteTextView
			
				
		// TODO 7.1 Filter our AutoCompleteTextView to update a query with a TextWatcher
		
		
		// TODO 5.1 Register our button an handle an onClick to lookup the Contact
		
				
		// TODO 1.4 Initialize our CursorLoader
		
	}

	// TODO 1.2 Define the LoaderCallBacks
	
	
}
