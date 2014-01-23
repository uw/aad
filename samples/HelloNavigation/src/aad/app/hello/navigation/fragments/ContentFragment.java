package aad.app.hello.navigation.fragments;

import aad.app.hello.navigation.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the fragment layout
		return inflater.inflate(R.layout.fragment_content, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		Bundle args = getArguments();
		
		String[] levels = this.getResources().getStringArray(R.array.array_navigation);
		
		int position = args.getInt("position", 0);
		String level = levels[position];	
		
		TextView levelTextView = (TextView) this.getView().findViewById(R.id.levelTextView);		
		
		levelTextView.setText(level);

		super.onActivityCreated(savedInstanceState);
	}


}
