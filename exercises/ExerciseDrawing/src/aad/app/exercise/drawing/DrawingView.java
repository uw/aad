package aad.app.exercise.drawing;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawingView extends View {

	private static String TAG = "DrawingView";
	
	// PHASE 1 Create some needed variables
	
	
	// PHASE 2 Create some more needed variables
	

	public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawingView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Log.d(TAG, "onDraw");
		
		// PHASE 1 Get the Points to draw and draw them to the Canvas

		
		// PHASE 2 Draw our composed result

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            
            case MotionEvent.ACTION_DOWN:
            	Log.d(TAG, "ACTION_DOWN");
                return true;

            case MotionEvent.ACTION_UP:
            	Log.d(TAG, "ACTION_UP");
                return false;
                                
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                
                // PHASE 1 What could we do to improve the quality?

            	
            	// PHASE 1 + 2 Add events as Points
            	
            	
            	// PHASE 1 + 2 How do we get ourselves to call onDraw()?
            	
            	
            	// Return true because we are handling the event
                return true;
        }
        
		return false;
	}
	
	
	private void addPoint(float x, float y, float pressure, float size) {

		// PHASE 1 Add to our Points collection
		
		
		// PHASE 2 Determine an appropriate radius
		

		// PHASE 2 Do our drawing


    }
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		// PHASE 1 + 2 : Intialize the Paint value
		
		
		// PHASE 2 : Initialize our interim storage
		
	}
	
}
