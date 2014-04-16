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
	public static enum STATE { HAPPY, NULL, SAD };
	private STATE mState = STATE.HAPPY;
	
	private Paint mFillPaint;
	private Paint mStrokePaint;
	private Paint mEyePaint;
	
	private RectF mCircleRectF;
	private RectF mMouthRectF;
	
	private int mStrokeWidth = 4;
	private float mPadding = 0;
	
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
		mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(Color.YELLOW);
        
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.BLACK);
        mStrokePaint.setStrokeWidth(mStrokeWidth);
        
        mEyePaint = new Paint(mStrokePaint);
        mEyePaint.setStyle(Paint.Style.FILL);
        
        mCircleRectF = new RectF();
        mMouthRectF = new RectF();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
				
		// TODO Draw the Background and Mouth of each Face
		switch (mState) {
		
			// Happy
			case HAPPY:
				// TODO Draw Happy
				mFillPaint.setColor(Color.YELLOW);
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mFillPaint);
				// We are reusing the RectF, so we need to reset the values here
				mMouthRectF.top = mPadding;
				mMouthRectF.bottom = getHeight() - mPadding;
				canvas.drawArc(mMouthRectF, 0, 180, false, mStrokePaint);
				break;
		
			// Null
			case NULL:
				// TODO Draw Null
				mFillPaint.setColor(Color.LTGRAY);
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mFillPaint);
				canvas.drawLine(mPadding, mPadding * 2.5f, mPadding * 3, mPadding * 2.5f, mStrokePaint);
				break;
		
			// Sad
			case SAD:
				// TODO Draw Sad
				mFillPaint.setColor(Color.RED);
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mFillPaint);
				mMouthRectF.top += mPadding;
				mMouthRectF.bottom += mPadding;
				canvas.drawArc(mMouthRectF, 180, 180, false, mStrokePaint);
				break;
		}
		
		// TODO Draw the Outside Stroke - What do we have to do to the Stroke?
		canvas.drawCircle(
		           getWidth() / 2,
		           getHeight() / 2,
		           getWidth() / 2 - (mStrokeWidth / 2),
		           mStrokePaint);
		
		// TODO Draw the Eyes
		canvas.drawCircle(mPadding * 1.25f, getHeight() / 3, 8, mEyePaint); // Left
		canvas.drawCircle(getWidth() - (mPadding * 1.25f), getHeight() / 3, 8, mEyePaint); // Right
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		// TODO Set the values of our drawing information

		if (w <= 0 || h <= 0)
			return;
		
		mCircleRectF.left = 0;
		mCircleRectF.top = 0;
		mCircleRectF.right = getWidth();
		mCircleRectF.bottom = getHeight();
		
		mPadding = w / 4;
		mMouthRectF.left = mPadding;
		mMouthRectF.top = mPadding;
		mMouthRectF.right = w - mPadding;
		mMouthRectF.bottom = h - mPadding;

		super.onSizeChanged(w, h, oldw, oldh);
	}

	public STATE getState() {
		return mState;
	}
	
	public void setState(STATE state) {
		mState = state;	
		
		// TODO Redraw the view - How?
		invalidate();
	}
	
}
