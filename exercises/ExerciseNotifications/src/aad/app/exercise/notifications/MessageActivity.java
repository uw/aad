package aad.app.exercise.notifications;

import android.os.Bundle;


public class MessageActivity extends ContentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Crack open and display the passed Intent extras
		Bundle extras = this.getIntent().getExtras();
		String sender = extras.getString("sender");
		String message = extras.getString("message");
		
		mPrimaryTextView.setText(sender);
		mSecondaryTextView.setText(message);

	}

}
