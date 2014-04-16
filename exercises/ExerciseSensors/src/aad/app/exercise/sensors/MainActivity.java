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

public class MainActivity extends Activity implements SensorEventListener {
    
    // TODO 2.1  Allocate some members for the Sensor and SensorManager
    private SensorManager mSensorManager;
    private Sensor mOrientationSensor;  
    
    // TODO 3.1 Allocate a Compass
    private Compass mCompass;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // TODO 2.2 Get the SensorManager and Orientation Sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        
        // TODO 3.2 Get a reference to the Compass        
        mCompass = (Compass) this.findViewById(R.id.compass);
    }

	// TODO 2.3 Properly Register/Unregister the SensorManager listener for Orientation
    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }
    
    // TODO 1.2 Implement the required SensorEventListener methods
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    	// TODO 3.3 Set the azimuth of the Compass
        if (event.sensor == mOrientationSensor) {
            mCompass.setAzimuth(Float.valueOf(event.values[0]));           
            return;
        }
    }
}
