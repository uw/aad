package aad.app.hello.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class ColorActivity extends Activity {

    public static final String TAG = "ColorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.color_layout);

        // Turn off animations
        getWindow().setWindowAnimations(android.R.style.Animation);
        
        // Enable the up affordance
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Bundle b = getIntent().getExtras();
        String colorString = b.getString("color");
        if (colorString != null)
            this.findViewById(R.id.color_layout).setBackgroundColor(Color.parseColor(colorString));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected()");
        
        // Handle the up affordance
        if (item.getItemId() == android.R.id.home)
            finish();
        
        return super.onOptionsItemSelected(item);
    }

    
    
}
