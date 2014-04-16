package aad.app.exercise.tags;

import android.graphics.Color;
import android.net.Uri;

public class TestActivity extends BaseActivity {

	@Override
	public void onResume() {
		super.onResume();

		// TODO 4.2 Extract our URI
		Uri xsUri = getNDEFUri(getIntent());
		if (xsUri != null) {
			mPathTextView.setBackgroundColor(Color.RED);
			mPathTextView.setText(xsUri.getLastPathSegment());
		}
	}

}
