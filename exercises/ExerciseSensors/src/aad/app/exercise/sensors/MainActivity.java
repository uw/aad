package aad.app.exercise.sensors;

import aad.app.exercise.sensors.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


// TODO 1.1 Implement a SensorEventListener
public class MainActivity extends Activity {
    
    // TODO 2.1  Allocate some members for the Sensor and SensorManager
	
    
    // TODO 3.1 Allocate a Compass
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // TODO 2.2 Get the SensorManager and Orientation Sensor
        
        
        // TODO 3.2 Get a reference to the Compass        
        
    }

	// TODO 2.3 Properly Register/Unregister the SensorManager listener for Orientation
    
    
    // TODO 1.2 Implement the required SensorEventListener methods
    
}
