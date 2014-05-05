package aad.odd.shared;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
	
    public static final String TAG = "MainService";
    
    private Context mContext = this;
    
    private UpdateRunnable mUpdateRunnable = new UpdateRunnable();    
    private class UpdateRunnable implements Runnable {

        @Override
        public void run() {
        	
        	Log.i(TAG, "run|START");
        	
        	int i = 0;
        	while (i < 5) {
        	
        		i++;
        		
        		String key = mContext.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS).getString("key", "UNSET");
        		Log.i(TAG, "key: " + key);
        		
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		
        		SharedPreferences.Editor editor = mContext.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS).edit();
        		editor.putString("key", "UNSET");
        		editor.apply();
        	}
        	
        	Log.i(TAG, "run|END");
        	
        }        
    }
    
    // Call for the actual bind
    @Override
    public IBinder onBind(Intent intent) {
        
        return null;
    }
        
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() intent: " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() intent: " + intent);

        mUpdateRunnable.run();
        
        return 0;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        
        System.exit(0);        
    }	

}
