
package aad.app.hello.gesture;

import aad.app.hello.gesture.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class HelloGestureActivity extends Activity implements OnTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = HelloGestureActivity.class.getSimpleName();
    
    private RelativeLayout mMainRelativeLayout;
    private View mTouchView;
    private GestureDetector mGestureDetector;
    
    private void addSpot(float x, float y) {
        Spot spot = new Spot(this);
        spot.setX(x);
        spot.setY(y);     
        mMainRelativeLayout.addView(spot);
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mGestureDetector = new GestureDetector(this, this);
        
        mTouchView = this.findViewById(R.id.touchView);
        mTouchView.setOnTouchListener(this);
            
        mMainRelativeLayout = (RelativeLayout) this.findViewById(R.id.mainRelativeLayout);
    }
    
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        
        // Pass Data to the GestureDetector
        mGestureDetector.onTouchEvent(event);
        
        float x = 0;
        float y = 0;
        
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN count: " + event.getPointerCount());
                x = event.getX(event.getPointerCount()-1);
                y = event.getY(event.getPointerCount()-1);
                addSpot(x, y);
                return true;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP count: " + event.getPointerCount());
                return false;
                
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "ACTION_POINTER_DOWN count: " + event.getPointerCount());
                x = event.getX(event.getPointerCount()-1);
                y = event.getY(event.getPointerCount()-1);
                addSpot(x, y);
                return true;
                
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "ACTION_POINTER_UP count: " + event.getPointerCount());
                return true;
                
            //case MotionEvent.ACTION_MOVE:
            //    Log.i("!!!", "ACTION_MOVE");
            //    return true;
        }
        
        return false;
    }


    @Override
    public boolean onDown(MotionEvent event) { 
        Log.d(TAG,"onDown() : " + event.toString()); 
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling() : " + event1.toString()+event2.toString());
        Toast.makeText(this, "Fling", Toast.LENGTH_SHORT).show();;
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(TAG, "onLongPress() : " + event.toString());
        Toast.makeText(this, "Long Press", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll() : " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(TAG, "onShowPress() : " + event.toString());
        Toast.makeText(this, "Press", Toast.LENGTH_SHORT).show();;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(TAG, "onSingleTapUp() : " + event.toString());
        Toast.makeText(this, "Single Tap", Toast.LENGTH_SHORT).show();
        return true;
    }

}
