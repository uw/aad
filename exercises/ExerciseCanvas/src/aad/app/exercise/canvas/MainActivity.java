package aad.app.exercise.canvas;

import aad.app.exercise.canvas.FaceView.STATE;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO Handle a click to the face...
		findViewById(R.id.faceView).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				FaceView fv = (FaceView) view;
				if (fv.getState() == STATE.HAPPY)
					fv.setState(STATE.NULL);
				else if (fv.getState() == STATE.NULL)
					fv.setState(STATE.SAD);
				else
					fv.setState(STATE.HAPPY);

			}
		});

	}
}
