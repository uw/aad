package aad.app.hello.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MainSurfaceView extends GLSurfaceView {

	public class MainRenderer implements GLSurfaceView.Renderer {

		@Override
		public void onDrawFrame(GL10 gl) {

			// Clear color and depth buffers using clear-value set earlier
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

			// Reset for drawing
			gl.glLoadIdentity();

			// Translate the position back a bit
			gl.glTranslatef(0.0f, 0.0f, -3.0f);

			// Rotate
			gl.glRotatef(mRotation, 1.0f, 1.0f, 1.0f);

			// Actually draw the cube
			mCube.draw(gl);

			// Update our rotation
			mRotation += 0.4f;
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {

			if (height == 0)
				height = 1;

			float aspectRatio = (float) width / height;

			// Set the viewport (display area) to cover the entire window
			gl.glViewport(0, 0, width, height);

			// Set the perspective projection
			gl.glMatrixMode(GL10.GL_PROJECTION);

			// Use perspective projection
			GLU.gluPerspective(gl, 90, aspectRatio, 0.1f, 100.f);

			// Set matrix mode to operate on the modelview stack
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

			// Set up the surface options
			gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			gl.glClearDepthf(1.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDepthFunc(GL10.GL_LEQUAL);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glDisable(GL10.GL_DITHER);

		}

	}

	private Cube mCube;

	private float mRotation = 0;

	public MainSurfaceView(Context context) {
		super(context);

		// Create our cube
		mCube = new Cube();

		// !!! Order really matters here !!!

		// Set the Renderer we want to use
		setRenderer(new MainRenderer());

		// Only render when there is a change
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

}
