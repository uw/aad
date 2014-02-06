package aad.app.exercise.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ContentActivity extends Activity {

	public TextView mPrimaryTextView;
	protected TextView mSecondaryTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		
		mPrimaryTextView = (TextView) this.findViewById(R.id.primaryTextView);
		mSecondaryTextView = (TextView) this.findViewById(R.id.secondaryTextView);
	}
	
	
}
