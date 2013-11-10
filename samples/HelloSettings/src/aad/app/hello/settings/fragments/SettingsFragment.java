package aad.app.hello.settings.fragments;

import aad.app.hello.settings.R;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference toastPreference = (Preference) findPreference("pref_show_toast");
        toastPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Would you like some toast?", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }
}
