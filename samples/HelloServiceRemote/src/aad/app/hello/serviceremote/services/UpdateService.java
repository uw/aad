
package aad.app.hello.serviceremote.services;

import aad.app.hello.serviceremote.services.IUpdateService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class UpdateService extends Service {

    public static final String TAG = UpdateService.class.getSimpleName();
    
    private boolean mIsUpdating = false;
    private Handler mHeartbeatHandler = new Handler();
    private UpdateRunnable mUpdateRunnable = new UpdateRunnable();    
    private class UpdateRunnable implements Runnable {

        @Override
        public void run() {
            if (mIsUpdating) {
                int threadID = android.os.Process.myTid();               
                Log.i(TAG, "Updating from threadID: " + threadID);
                mHeartbeatHandler.postDelayed(this, 5000);
            }
        }        
    }

    public class UpdateBinderProxy extends IUpdateService.Stub {

        @Override
        public void getGoodbye() throws RemoteException {
            UpdateService.this.getGoodbye();
        }

        @Override
        public String getHello() throws RemoteException {
            return UpdateService.this.getHello();
        }        
    }
    
    // Call for the actual bind
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() intent: " + intent);
        startUpdating();
        return new UpdateBinderProxy();
    }
    
    
    /**
     * Public method to expose functionality.
     * @return
     */
    public void getGoodbye() {
        Log.d(TAG, "getGoodbye()");
        
        Intent goodbyeIntent = new Intent();
        goodbyeIntent.setAction("aad.app.hello.service.GOODBYE");
        this.sendBroadcast(goodbyeIntent);  
    }    
    
    private void startUpdating() {
        
        mIsUpdating = true;
        mHeartbeatHandler.post(mUpdateRunnable);
    }

    private void stopUpdating() {
        
        mIsUpdating = false;
    }
    
    /**
     * Public method to expose functionality.
     * @return
     */
    public String getHello() {
        Log.d(TAG, "getHello()");
        
        return "Hello";
    }    
    
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart() intent: " + intent);
        startUpdating();
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() intent: " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() intent: " + intent);

        // START_NOT_STICKY - Icky We want to live on, live free!
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        stopUpdating();
        
        // LEARN: We are forcing an exit here just to show the :service process disappears
        System.exit(0);
        
    }

}
