
package aad.app.hello.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class HelloNetworkActivity extends Activity {

    private static final String TAG = HelloNetworkActivity.class.getSimpleName();
    private static final String UPDATE_ACTION = "aad.app.e15.Update";

    private TextView mTextView;
    private ConnectivityManager mConnectivityManager;
    private WifiManager mWifiManager;
    private UpdateReceiver mUpdateReceiver;

    public static class ConnectivityChangeReceiver extends BroadcastReceiver {

        public ConnectivityChangeReceiver() {
            super();
        }

        public void onReceive(Context context, Intent intent) {

            Log.i(TAG, "ConnectivityChangeReceiver::onReceive()");

            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    Toast.makeText(context, "Connected to " + ni.getTypeName(), Toast.LENGTH_LONG).show();
                }
            }

            if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
            }

            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(UPDATE_ACTION));
        }
    }

    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "UpdateReceiver::onReceive()");
            mHandler.sendEmptyMessage(0);
        }
    }

    private static class UpdateHandler extends Handler {
        private final WeakReference<HelloNetworkActivity> mActivity;

        public UpdateHandler(HelloNetworkActivity activity) {
            mActivity = new WeakReference<HelloNetworkActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HelloNetworkActivity activity = mActivity.get();
            if (activity != null) {
                activity.updateUI();
            }
        }
    }

    private final UpdateHandler mHandler = new UpdateHandler(this);

    /** 
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        mUpdateReceiver = new UpdateReceiver();
        updateUI();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume()");
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdateReceiver, new IntentFilter(UPDATE_ACTION));
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause()");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdateReceiver);
        super.onPause();
    }

    /**
     * Update the TextView.
     */
    private void updateUI() {
        Log.i(TAG, "updateUI()");

        mTextView = (TextView) this.findViewById(R.id.mainTextView);

        StringBuilder sb = new StringBuilder();
        // Get the ConnectivityManager
        mConnectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();

        if (activeNetwork == null) {
            sb.append("No Active Network!");
        }
        else {

            boolean isConnected = activeNetwork.isConnectedOrConnecting();
            sb.append("ActiveNetwork: " + activeNetwork.getTypeName());
            sb.append("\nConnected: " + isConnected + "\n\n");

            sb.append("All Networks:");
            NetworkInfo[] networks = mConnectivityManager.getAllNetworkInfo();
            for (NetworkInfo ni : networks) {
                sb.append("\nNetwork: " + ni.getTypeName() + ":" + ni.getSubtypeName());

                if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Get the WifiManager
                    mWifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);

                    if (mWifiManager == null)
                        break;

                    sb.append("\n  Wifi State: " + mWifiManager.getWifiState());
                    List<ScanResult> scanResults = mWifiManager.getScanResults();

                    if (scanResults != null) {
                        for (ScanResult sr : scanResults) {
                            sb.append("\n  AP: " + sr.SSID + " @ " + sr.level + "db");
                        }
                    }
                }
            }
        }

        mTextView.setText(sb.toString());

    }

}
