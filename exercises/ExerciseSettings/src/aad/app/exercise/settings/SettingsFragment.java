package aad.app.exercise.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.prefscreen_default);
		
		
	}

	
	
}
