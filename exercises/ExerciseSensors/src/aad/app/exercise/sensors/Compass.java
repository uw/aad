package aad.app.exercise.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Compass extends ImageView
{
    private float mAzimuth = 0f; 
    private float mCenterHeight;
    private float mCenterWidth;

    public Compass(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Compass(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Compass(Context context) {
        super(context);
    }

    public void setAzimuth(float azimuth)
    {
        // Invert the incoming azimuth
        mAzimuth = -azimuth;
        
        mCenterHeight = getHeight() / 2;
        mCenterWidth = getWidth() / 2; 
        
        // Redraw the ImageView
        invalidate();
    }
    
	@Override
	public void onDraw (Canvas canvas) 
	{	
		canvas.rotate(mAzimuth, mCenterWidth, mCenterHeight);		
		super.onDraw(canvas);
	}
	
}
