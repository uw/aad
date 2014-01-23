package aad.app.hello.navigation;


import aad.app.hello.navigation.fragments.ContentFragment;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity {

	private ActionBar mActionBar;
	private ViewPager mPager;
	private ContentAdapter mContentAdapter;
	
	public class ContentAdapter extends FragmentPagerAdapter {
		
        public ContentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {        	
            return getResources().getStringArray(R.array.array_navigation).length;
        }

        @Override
        public Fragment getItem(int position) {
        	
        	Fragment contentFragment = new ContentFragment();
        	Bundle args = new Bundle();
        	args.putInt("position", position);
        	contentFragment.setArguments(args);
        	
            return contentFragment;
        }
    }
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);		
		
		mActionBar = getActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    	    
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_navigation, android.R.layout.simple_spinner_dropdown_item);

	    mActionBar.setListNavigationCallbacks(adapter, new OnNavigationListener(){

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				mPager.setCurrentItem(itemPosition);
				return false;
			}});

	    
	    mContentAdapter = new ContentAdapter(getSupportFragmentManager());
	    mPager = (ViewPager) findViewById(R.id.pager);
	    mPager.setAdapter(mContentAdapter);
	    
	    mPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int state) {			
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {				
			}

			@Override
			public void onPageSelected(int position) {
				mActionBar.setSelectedNavigationItem(position);
				
			}});
	    
	}	
	
}