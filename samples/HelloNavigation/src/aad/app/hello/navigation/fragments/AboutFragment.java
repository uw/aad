package aad.app.hello.navigation.fragments;

import aad.app.hello.navigation.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AboutFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the fragment layout
		return inflater.inflate(R.layout.fragment_about, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		Button okButton = (Button) this.getActivity().findViewById(R.id.okButton);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				getActivity().getFragmentManager().beginTransaction().remove(AboutFragment.this).commit();
				
			}});
		
		super.onActivityCreated(savedInstanceState);
	}

}
