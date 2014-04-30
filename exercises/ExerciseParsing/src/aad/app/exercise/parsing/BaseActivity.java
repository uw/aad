package aad.app.exercise.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class BaseActivity extends Activity {
	
	protected static class UpdateHandler extends Handler {
		private final WeakReference<BaseActivity> mActivity;

		public UpdateHandler(BaseActivity activity) {
			mActivity = new WeakReference<BaseActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseActivity activity = mActivity.get();
			if (activity != null) {
				TextView mainTextView = (TextView) activity.findViewById(R.id.mainTextView);
				mainTextView.setText((String) msg.obj);
			}
		}
	}
	protected UpdateHandler mUpdateHandler = new UpdateHandler(this);

	protected InputStream getHTTPInputStream(String site) throws IOException {

		InputStream inputStream = null;

		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(site);

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != 200) {
			// We have had some kind of error
			Log.e("ExerciseParsing", "HTTP Error: " + statusCode);
			return null;
		}

		HttpEntity entity = response.getEntity();
		inputStream = entity.getContent();

		return inputStream;
	}

	protected void sendTextMessage(String message) {

		Message msg = new Message();
		msg.obj = message;
		mUpdateHandler.sendMessage(msg);
	}
}
