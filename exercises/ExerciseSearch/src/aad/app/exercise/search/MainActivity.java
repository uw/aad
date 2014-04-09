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
public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {

	public static String TAG = "ExerciseSearch";
	
	// TODO 1.3 Define the Loader ID
	private static int CONTACT_LOADER = 99;
		
	// TODO 3.1 Set a SimpleCursorAdapter to use
	private SimpleCursorAdapter mSimpleCursorAdapter;
	
	// TODO 4.1 Set an AutoCompleteTextView
	private AutoCompleteTextView mAutoCompleteTextView;
	
	// TODO 2.1 Create our ContactsContract projection
	private String[] mContactProjection = new String[] { 
			ContactsContract.Data._ID, 
			ContactsContract.Data.DISPLAY_NAME, 
			ContactsContract.Data.CONTACT_ID, 
			ContactsContract.Data.LOOKUP_KEY };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO 3.2 Create a SimpleCursorAdapter - Inline
		mSimpleCursorAdapter = new SimpleCursorAdapter(
				this, 
				android.R.layout.simple_list_item_1, 
				null, 
				new String[] { ContactsContract.Data.DISPLAY_NAME }, 
				new int[] { android.R.id.text1 }, 
				0);
	
		// TODO 3.3 Set the Cursor String conversion
		mSimpleCursorAdapter.setStringConversionColumn(1);
		
		// Alternate way to convert your column to a String
//		mSimpleCursorAdapter.setCursorToStringConverter(new CursorToStringConverter() {              
//	        @Override
//	        public CharSequence convertToString(Cursor cursor) {
//	            final int columnIndex = cursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME);
//	            final String str = cursor.getString(columnIndex);
//	            return str;
//	        }
//		});
		
		// TODO 4.2 Get a reference to the AutoCompleteTextView
		mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.mainAutoCompleteTextView);

		// TODO 4.3 Set the Adapter on the AutoCompleteTextView
		mAutoCompleteTextView.setAdapter(mSimpleCursorAdapter);	
				
		// TODO 7.1 Filter our AutoCompleteTextView to update a query with a TextWatcher
		mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {  
			
	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	        		        	
	        	// TODO 7.2 Set a FilterQueryProvider on our Adapter
	        	mSimpleCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
	        		
	        		String s = "";
	    		    public Cursor runQuery(CharSequence constraint) {
	    		        
	    		    	if (constraint != null)
	    		        	s = '%' + constraint.toString() + '%';
	    		        
	    		        return getContentResolver().query(
	    		        		ContactsContract.Data.CONTENT_URI, 
	    		        		mContactProjection,
	    		        		ContactsContract.Data.DISPLAY_NAME + " LIKE ? ",
	    		        		new String[] { s },
	    		        		null);
	    		    }
	    		});
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {                
	        	// NOT USED
	        }

	        @Override
	        public void afterTextChanged(Editable s) {
	        	// NOT USED
	        }
	    });

		// TODO 5.1 Register our button an handle an onClick to lookup the Contact
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// TODO 5.2 Get our selected Contact by Name
		        Cursor c = getContentResolver().query(
		        		ContactsContract.Data.CONTENT_URI, 
		        		mContactProjection, 
		        		ContactsContract.Data.DISPLAY_NAME + " = ? ",
		        		new String[] { mAutoCompleteTextView.getText().toString() },
		        		null);
		        
		        // TODO 5.3 Iterate our Cursor
		        if (c != null && c.moveToFirst()) {
		        	long contactId = c.getLong(c.getColumnIndex(ContactsContract.Data.CONTACT_ID));
		        	String lookupKey = c.getString(c.getColumnIndex(ContactsContract.Data.LOOKUP_KEY));       	
		        	
					Uri lookupUri = Contacts.getLookupUri(contactId, lookupKey);
					Log.i(TAG, "lookupUri: " + lookupUri.toString());
				
					Intent intent = new Intent(Intent.ACTION_VIEW, lookupUri);
					startActivity(intent);
					
		        } else {
		        	Toast.makeText(MainActivity.this, "Not a valid Contact", Toast.LENGTH_LONG).show();
		        }		        	
			}
			
		});
		
		// TODO 1.4 Initialize our CursorLoader
		getLoaderManager().initLoader(CONTACT_LOADER , null, this);
	}

	// TODO 1.2 Define the LoaderCallBacks
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO 2.2 Return a new CursorLoader
		return new CursorLoader(this, ContactsContract.Data.CONTENT_URI, mContactProjection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {		
		// TODO 2.3 Swap out our adapter with the fresh one
		mSimpleCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {		
		// TODO 2.3 Null out our adapter cursor
		mSimpleCursorAdapter.swapCursor(null);
	}

}
