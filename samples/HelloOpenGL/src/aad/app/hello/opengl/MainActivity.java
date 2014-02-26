package aad.app.hello.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

	private GLSurfaceView mMainGLSurfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Instead of using a layout, we create our own SurfaceView and use it as the content
		mMainGLSurfaceView = new MainSurfaceView(this);
		setContentView(mMainGLSurfaceView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMainGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMainGLSurfaceView.onPause();
	}
}