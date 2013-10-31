
package aad.app.hello.orientation;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Surface;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }        
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged() orientation: " + newConfig.orientation);          
        super.onConfigurationChanged(newConfig);
        
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main);
        
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
        
        dumpOrientationInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();        
        Log.d(TAG, "onPause()");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        
        dumpOrientationInformation();
    }

    private void dumpOrientationInformation() {
        
        StringBuilder sb = new StringBuilder();
        int orientation = this.getWindowManager().getDefaultDisplay().getOrientation();
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        
        sb.append(String.format("Orientation: %d and rotated ", orientation));
        switch (rotation) {
            case Surface.ROTATION_0:
                sb.append("0");
            break;
            
            case Surface.ROTATION_90:
                sb.append("90");
            break;
            
            case Surface.ROTATION_180:
                sb.append("180");
            break;
            
            case Surface.ROTATION_270:
                sb.append("270");
            break;
            
        }
        
        sb.append(" degrees from natural orientation");
        
        // Set the text of the TextView
        TextView tv = (TextView) this.findViewById(R.id.textView2);
        tv.setText(sb.toString());        
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

}
