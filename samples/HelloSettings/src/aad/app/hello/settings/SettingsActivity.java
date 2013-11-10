package aad.app.hello.settings;

import aad.app.hello.settings.fragments.SettingsFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        
        ft.replace(android.R.id.content, new SettingsFragment());
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if (item.getItemId() == android.R.id.home)
            finish();
        
        return super.onOptionsItemSelected(item);
    }

}
