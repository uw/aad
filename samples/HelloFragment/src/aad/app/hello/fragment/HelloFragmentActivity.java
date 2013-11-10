package aad.app.hello.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import aad.app.hello.fragment.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.FragmentManager;

public class HelloFragmentActivity extends Activity implements ActionBar.TabListener {

    public static final String TAG = "HelloFragmentActivity";

    private ActionBar mActionBar;
    private boolean mDualPane = true;
    private ListFragment mListFragment;
    private ColorFragment mColorFragment;
    private TextFragment mTextFragment;
    

    /** A representation of each of our list items
     * 
     * @author Mike
     *
     */
    public class ListItem {

        public String type;
        public String value;

        public ListItem(String type, String value) {

            this.type = type;
            this.value = value;
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Turn off animations
        getWindow().setWindowAnimations(android.R.style.Animation);

        View fragmentContainer = this.findViewById(R.id.fragmentContainer);
        mDualPane = fragmentContainer != null && fragmentContainer.getVisibility() == View.VISIBLE;

        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Fill our colors panel
        final ArrayList<ListItem> items = readXML();
        ArrayAdapter<ListItem> itemsAdapter = new ArrayAdapter<ListItem>(this, android.R.layout.list_content, items) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View row;

                if (convertView == null) {
                    row = inflater.inflate(android.R.layout.simple_list_item_2, null);
                }
                else {
                    row = convertView;
                }

                TextView tv1 = (TextView) row.findViewById(android.R.id.text1);
                tv1.setText(getItem(position).type);

                TextView tv2 = (TextView) row.findViewById(android.R.id.text2);
                tv2.setText(getItem(position).value);

                return row;
            }

        };

        FragmentManager fragmentManager = getFragmentManager();
        ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.listFragment);
        listFragment.getListView().setBackgroundColor(Color.parseColor("#333333"));
        listFragment.setListAdapter(itemsAdapter);
        listFragment.getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long id) {

                TextView tv = (TextView) view.findViewById(android.R.id.text2);
                showColor(position, tv.getText().toString());
            }

        });
        
        
        mListFragment = (ListFragment) getFragmentManager().findFragmentById(R.id.listFragment);        
        mColorFragment = new ColorFragment();
        mTextFragment = new TextFragment();        
        
        // Setup the Action Bar        
        mActionBar = this.getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab t1 = mActionBar.newTab();
        t1.setText("Color");
        t1.setTabListener(this);
        
        Tab t2 = mActionBar.newTab();
        t2.setText("Text");
        t2.setTabListener(this);
        
        mActionBar.addTab(t1);
        mActionBar.addTab(t2);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    /** Read the XML resource R.xml.list into an ArrayList */
    private ArrayList<ListItem> readXML() {

        ArrayList<ListItem> items = new ArrayList<ListItem>();

        try {

            XmlResourceParser xrp = this.getResources().getXml(R.xml.list);

            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xrp.getName().equalsIgnoreCase("item")) {
                        items.add(new ListItem(xrp.getAttributeValue(null, "type"), xrp.getAttributeValue(null, "value")));
                    }
                }
                eventType = xrp.next();
            }

        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    /** Show either a fragment or activity in the selected color */
    void showColor(int index, String colorString) {
        Log.d(TAG, "showColor() index:" + index + " colorString: " + colorString);

        if (mDualPane) {
            
            mColorFragment.setColor(Color.parseColor(colorString));

            // This was the old way, when not using Tabs
            //FragmentTransaction ft = getFragmentManager().beginTransaction();            
            //ft.replace(R.id.fragmentContainer, mColorFragment);
            //ft.commit();
        }
        else {

            Intent intent = new Intent();
            intent.setClass(this, ColorActivity.class);
            intent.putExtra("index", index);
            intent.putExtra("color", colorString);
            startActivity(intent);
        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // Currently we do nothing        
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

        int position = tab.getPosition();
        switch (position) {
            
            case 0:
                if (mDualPane)
                    ft.add(R.id.fragmentContainer, mColorFragment);
                break;
                
            case 1:
                ft.hide(mListFragment);
                ft.add(R.id.mainLayout, mTextFragment);
                break;
        }
        
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

        int position = tab.getPosition();
        switch (position) {
            
            case 0:
                if (mDualPane)
                    ft.remove(mColorFragment);
                break;
                
            case 1:
                ft.show(mListFragment);
                ft.remove(mTextFragment);
                break;
        }
        
    }

}