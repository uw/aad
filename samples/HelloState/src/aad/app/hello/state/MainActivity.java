package aad.app.hello.state;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView mArrow;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		

		mArrow = new ImageView(this);
		mArrow.setImageResource(R.drawable.arrow);
		mArrow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		FrameLayout frameLayout = (FrameLayout) this.findViewById(R.id.mainFrameLayout);
		frameLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				
				mArrow.setX(event.getX());
				mArrow.setY(event.getY());
				
				return true;
			}
			
		});
		
		frameLayout.addView(mArrow);

	}

//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		
//		// Restore our arrow position
//		if (mArrow != null) {
//			mArrow.setX(savedInstanceState.getFloat("arrowX"));
//			mArrow.setY(savedInstanceState.getFloat("arrowY"));
//		}
//		
//		super.onRestoreInstanceState(savedInstanceState);
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		
//		// Save our arrow position
//		if (mArrow != null) {
//			outState.putFloat("arrowX", mArrow.getX());
//			outState.putFloat("arrowY", mArrow.getY());
//		}
//		
//		super.onSaveInstanceState(outState);
//	}
	
	
}