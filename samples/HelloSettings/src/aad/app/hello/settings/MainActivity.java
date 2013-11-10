
package aad.app.hello.settings;

import aad.app.hello.settings.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_notification:
                showNotification();
                return true;
                
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
                
            case R.id.menu_show_help:
                showHelp();
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    
    
    @Override
    protected void onResume() {
        
        // Check to see if we have preferences stored and show them        
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString("pref_your_name", "");
        Boolean approves = sp.getBoolean("pref_kitten_approval", true);
        String approvesString = approves ? "does" : "does not";
      
        String statement = String.format("%s %s approve of kittens", name, approvesString);

        if (!name.isEmpty()) {
            TextView approvalTextView = (TextView) this.findViewById(R.id.approvalTextView);
            approvalTextView.setText(statement);
        }
        
        
        super.onResume();
    }

    private void showHelp() {
        
        WebView wv = new WebView(this);

        String data = "<html><body>This is some help right here.<br/><br/>Hope it was helpful.<br/></body></html>";
        wv.loadData(data, "text/html", "utf-8");
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setView(wv).setTitle(R.string.help).create();
        dialog.show();    
        
    }
    
    private void showNotification() {
        
        // Build the notification        
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        Notification n = 
                nb
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle("Notification!")
                .setContentText("This is a notification... get used to it.")
                .build();

        // Notify via the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
        notificationManager.notify(0, n);
    }
    
}
