package aad.app.exercise.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TitleListFragment extends ListFragment {

	private static final String TAG = "TitleListFragment";

	private ArrayAdapter<String> mTitlesArrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate()");
		
		// Just dump some data into the List
		String[] titles = new String[] { "Star Wars", "Watership Down", "Blade Runner", "Raiders of the Lost Ark", "Tron" };
        mTitlesArrayAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        setListAdapter(mTitlesArrayAdapter);		
	}	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    Log.i(TAG, "onCreateView()");
	    
	    return inflater.inflate(R.layout.fragment_titlelist, container, false);
    }

    @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	
	}

	@Override
	public void onAttach(Activity activity) {
		
	    Log.i(TAG, "onAttach()");
	    
	    super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		
	    Log.i(TAG, "onDetach()");
	    
	    super.onDetach();
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {

	    // HACK We don't really care what was clicked right now...
	    ((MainActivity) getActivity()).showDetail();
	    
	    super.onListItemClick(listView, view, position, id);
	}

	
}
