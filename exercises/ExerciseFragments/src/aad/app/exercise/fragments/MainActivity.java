package aad.app.exercise.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    public final static String TAG = "MainActivity";
    
    private TitleListFragment mTitleListFragment;
    private WelcomeFragment mWelcomeFragment;
    private DetailFragment mDetailFragment;
    private FrameLayout mWelcomePlaceholder;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "onCreate()");
        
        setContentView(R.layout.activity_main); 
        
        // Add the TitleListFragment
        mTitleListFragment  = (TitleListFragment) getFragmentManager().findFragmentByTag("TitleListFragment");
        if (mTitleListFragment == null) {
            mTitleListFragment = new TitleListFragment();
            
            if (mTitleListFragment.isAdded()) {
            	
	            getFragmentManager()
                .beginTransaction()
                .show(mTitleListFragment)
                .commit();
            	
            } else {
            	
	            getFragmentManager()
	                .beginTransaction()
	                .add(R.id.titlePlaceholder, mTitleListFragment, "TitleListFragment")
	                .commit();
            }
            
        }
        
        // Add the WelcomeFragment if we have a placeholder to write to
        mWelcomePlaceholder = (FrameLayout) findViewById(R.id.welcomePlaceholder);
        if (mWelcomePlaceholder != null) {
            
            mWelcomeFragment = (WelcomeFragment) getFragmentManager().findFragmentByTag("WelcomeFragment");
            
            if (mWelcomeFragment == null)
                mWelcomeFragment = new WelcomeFragment();
            
            if (mWelcomeFragment.isAdded()) {
            	
	            getFragmentManager()
                .beginTransaction()
                .show(mWelcomeFragment)
                .commit();
            	
            } else {
            	
	            getFragmentManager()
	                .beginTransaction()
	                .add(R.id.welcomePlaceholder, mWelcomeFragment, "WelcomeFragment")
	                .commit();
            }
        }
        
    }

    @Override
    protected void onDestroy() {
        
        Log.i(TAG, "onDestroy()");
        
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        
        Log.i(TAG, "onPause()");
        
        super.onPause();
    }

    @Override
    protected void onResume() {
        
        Log.i(TAG, "onResume()");
        
        super.onResume();
    }

    @Override
    protected void onStart() {
        
        Log.i(TAG, "onStart()");
        
        super.onStart();
    }

    @Override
    protected void onStop() {
        
        Log.i(TAG, "onStop()");
        
        super.onStop();
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                hideDetail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    
    public void showDetail() {
        
        Log.i(TAG, "showDetail()");
        
        mDetailFragment = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
        if (mDetailFragment == null) {           
            mDetailFragment = new DetailFragment();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Add it to the welcome placeholder if available, otherwise just to our top layout
        if (mWelcomePlaceholder != null) {

        	if (!mDetailFragment.isAdded()) {
	            getFragmentManager()
	                .beginTransaction()
	                .add(R.id.welcomePlaceholder, mDetailFragment, "DetailFragment")
	                .addToBackStack(null)
	                .commit();
        	}
            
        } else {
            
        	if (!mDetailFragment.isAdded()) {
	            getFragmentManager()
	                .beginTransaction()
	                .add(R.id.mainPlaceholder, mDetailFragment, "DetailFragment")
	                .addToBackStack(null)
	                .commit();            
        	}
        }
        
    }
    
    public void hideDetail() {
        
        Log.i(TAG, "hideDetail()");
        
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getFragmentManager().popBackStack();
        
    }

    
}
