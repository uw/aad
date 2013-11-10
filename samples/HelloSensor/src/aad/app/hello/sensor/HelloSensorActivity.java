package aad.app.hello.sensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HelloSensorActivity extends Activity {

    private static final String TAG = HelloSensorActivity.class.getSimpleName();

    public boolean emulating() {
    		String model = android.os.Build.MODEL;
    		
    		if (model.equals("sdk") || model.equals("Android SDK built for x86"))
    			return true;
    		
    		return false;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");
        
        super.onCreate(savedInstanceState);
        
        if (emulating()) 
            this.startActivity(new Intent(this, SimulatedSensorActivity.class));        
        else
            this.startActivity(new Intent(this, RealSensorActivity.class));
        
        finish();
    }

}