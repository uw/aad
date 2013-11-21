package aad.app.hello.thread;

import aad.app.hello.thread.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends Activity {

    private static final String TAG = "HelloThreadActivity";

    public TextView mHelloTextView;
    public TextView mCountdownTextView;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            mHelloTextView.setText("HelloThread!");
            super.handleMessage(msg);
        }
    };
    
    private class CountdownAsyncTask extends AsyncTask<String, Integer, String> {

        private String preamble;
        private int countdown;
        
        @Override
        protected String doInBackground(String... params) {
            
            preamble = params[0];
            
            Thread.currentThread().setName("CountdownAsyncTask Thread");
            
            while (countdown > 0) {
                try {
                    Thread.sleep(1000);
                    countdown -= 1;
                    publishProgress(countdown);
                }
                catch (InterruptedException e) {
                    // Don't care if we are interrupted - why might we care?
                }                
            }
            
            return "Blast off!";
        }

        
        @Override
        protected void onPreExecute() {
            
            countdown = 45;
            
            // Normally you would also setup your Dialog or other progress views here
            
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            
            mCountdownTextView.setText(result);
            
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            
            mCountdownTextView.setText(preamble + values[0].toString());
            
            super.onProgressUpdate(values);
        }        
        
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloTextView = (TextView) this.findViewById(R.id.helloTextView);
        mCountdownTextView = (TextView) this.findViewById(R.id.countdownTextView);
        
        Thread t = new Thread() {

            @Override
            public void run() {

                // !!! Don't do this !!!
                // mTextView.setText("HelloThread!");

                // Slow loop to keep the thread around to be viewed
                while (true) {
                    try {
                        
                        // Sleep for 10 seconds
                        Thread.sleep(10000);
                        
                        // Do this
                        mHandler.sendEmptyMessage(0);
                        
                        // Sleep for 35 seconds
                        Thread.sleep(35000);
                   }
                    catch (InterruptedException e) {
                        // Don't care if we are interrupted - why might we care?
                    }
                }

            }
        };

        t.setName("HelloThread Thread");
        t.start();
        
        // This is very naughty - but I want it to be...
        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(45000);
                }
                catch (InterruptedException e) {
                 // Don't care if we are interrupted - why might we care?
                    e.printStackTrace();
                }
                super.run();
            }
            
        }.start();
        
        // Fire off our AsyncTask
        new CountdownAsyncTask().execute("T minus ");
        
        // Why might we not want an anonymous AsyncTask?
        
    }

}