
package aad.app.hello.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class HelloSearchActivity extends Activity implements OnQueryTextListener {

    private static final String TAG = HelloSearchActivity.class.getSimpleName();
        
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Search Dialog Button
        Button searchButton = (Button) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                // Default call
                onSearchRequested();
                
            }});
        
        
        // SearchViews 
        SearchView searchView = (SearchView) this.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        SearchView searchView2 = (SearchView) this.findViewById(R.id.searchView2);
        searchView2.setOnQueryTextListener(this);       

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
    
    
}
