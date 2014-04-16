package aad.app.hello.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class SymbolView extends View {

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();
    
    public SymbolView(Context context) {
        super(context);
    }
    
    public SymbolView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
                
        mPaint.setColor(Color.BLUE);
        mRect.set(0, 0, this.getWidth(), this.getHeight());
        canvas.drawRect(mRect, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(this.getWidth() / 2.0f, this.getHeight() / 2.0f, this.getWidth() / 2, mPaint);
        
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(0, 0, getWidth(), getHeight(), mPaint);
        
        super.onDraw(canvas);
    }
}
