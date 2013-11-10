package aad.app.hello.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Spot extends View {

    private Paint mPaint = new Paint();
    private int mAlpha = 255;
    
    Runnable fadeRunnable = new Runnable() {

        @Override
        public void run() {
            if (mAlpha <= 2) {
                remove();
            }
            else {
                mAlpha = mAlpha - 16;
                invalidate();
                
                postDelayed(fadeRunnable, 250);
            }
            
        }
        
    };
    
    public Spot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public Spot(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Spot(Context context) {
        super(context);
        initialize();
    }
    
    private void initialize() {
        mPaint.setColor(Color.BLUE); 
        // Start the fade
        this.postDelayed(fadeRunnable, 50);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (mAlpha > 0) {
            mPaint.setAlpha(mAlpha);
            canvas.drawCircle(170, 170, 50, mPaint);
        }
    }
    
    private void remove() {
        ((ViewGroup)getParent()).removeView(this);
    }

    
}
