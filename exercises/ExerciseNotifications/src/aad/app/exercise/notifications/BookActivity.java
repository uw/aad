package aad.app.exercise.notifications;

import android.os.Bundle;


public class BookActivity extends ContentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Crack open and display the passed Intent extras
		Bundle extras = this.getIntent().getExtras();
		String title = extras.getString("title");
		String author = extras.getString("author");
				
		mPrimaryTextView.setText(title);
		mSecondaryTextView.setText(author);

	}

}
