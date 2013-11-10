
package aad.app.hello.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloTask extends Activity {

    private static final String TAG = HelloTask.class.getSimpleName();
    private int mCurrentValue = 0;
    private TextView mMainTextView;
    private TickTask mTickTask;

    // This task does absolutely nothing...
    private class NothingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
    
    // This task increments an integer...
    private class TickTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            while (true) {
                mCurrentValue++;

                Log.d(TAG, "doInBackground() " + mCurrentValue);

                try {
                    Thread.sleep(1000); // sleep or wait
                    this.publishProgress(mCurrentValue);
                }
                catch (InterruptedException e) {
                    // We don't care about this Exception
                    Log.w(TAG, "doInBackground() Cancelling");
                    break;
                }
            }
            
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate()");
            
            String value = String.valueOf(values[0]);

            if (mMainTextView != null)
                mMainTextView.setText(value);

            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main);

        mMainTextView = (TextView) this.findViewById(R.id.mainTextView);
        ((Button) this.findViewById(R.id.mainCancelButton)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mTickTask.cancel(true);
            }
        });

        // Just do some work that doesn't require any interaction
        NothingTask nothingTask = new NothingTask();
        nothingTask.execute();

        // Lets update our display every second
        mTickTask = new TickTask();
        mTickTask.execute(Integer.valueOf(mCurrentValue));
    }

}
