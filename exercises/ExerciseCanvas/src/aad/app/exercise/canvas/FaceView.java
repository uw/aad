package aad.app.exercise.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class FaceView extends View {

	// TODO Create variables to hold our drawing information
	
	
	public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FaceView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		
		// TODO Initialize our drawing information
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
				
		// TODO Draw the Background and Mouth of each Face
		switch (mState) {
		
			// Happy
			case HAPPY:
				// TODO Draw Happy
				
				break;
		
			// Null
			case NULL:
				// TODO Draw Null
				
				break;
		
			// Sad
			case SAD:
				// TODO Draw Sad
				
				break;
		}
		
		// TODO Draw the Outside Stroke - What do we have to do to the Stroke?
		
				
		// TODO Draw the Eyes
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		// TODO Set the values of our drawing information
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public STATE getState() {
		return mState;
	}
	
	public void setState(STATE state) {
		mState = state;	
		
		// TODO Redraw the view - How?
		
	}
	
}
