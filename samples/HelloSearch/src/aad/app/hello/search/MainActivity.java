
package aad.app.hello.search;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.SearchManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity implements OnQueryTextListener, LoaderCallbacks<ArrayList<String>>  {

    private static final String TAG = "HelloSearch";
    public static int LOADER_CURSOR = 1972;
    private ArrayAdapter<String> mAutoCompleteAdapter;
    private AutoCompleteTextView mAutoCompleteTextView;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Search Dialog Button
        Button searchButton = (Button) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                // Default call
                onSearchRequested();
                
            }});        
        
        // AutoComplete    
        mAutoCompleteTextView = (AutoCompleteTextView) this.findViewById(R.id.searchAutocomplete);
        mAutoCompleteTextView.setThreshold(1);
        Button searchAutoButton = (Button) this.findViewById(R.id.searchAutoButton);
        searchAutoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				String query = mAutoCompleteTextView.getText().toString();
				
				//Reuse the onQueryTextSubmit call
				onQueryTextSubmit(query);
				
			}       
        });
        
        // SearchViews 
        SearchView searchView = (SearchView) this.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        SearchView searchView2 = (SearchView) this.findViewById(R.id.searchView2);
        searchView2.setOnQueryTextListener(this);    
                
        getLoaderManager().initLoader(LOADER_CURSOR, null, this);
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        // Set the ActionBar SearchView
        SearchView abSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        abSearchView.setOnQueryTextListener(this);
        
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Do the search
        Intent searchIntent = new Intent();
        searchIntent.setClass(this, SearchResultsActivity.class);
        searchIntent.setAction(Intent.ACTION_SEARCH);
        searchIntent.putExtra(SearchManager.QUERY, query);
        this.startActivity(searchIntent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // We might do some suggestions here...
        return false;
    }

	@Override
	public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {		        
		return new BookLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> bookTitles) {
		
		Log.i(TAG, "onLoadFinished() arrayList:");
		for (String book : bookTitles) {
			Log.i(TAG, "book: " + book);	
		}
		
		mAutoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookTitles);	 
		mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<String>> args) {
		// Do nothing right now		
	}
    
    
}
