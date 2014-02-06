package aad.app.hello.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews;

public class MainActivity extends Activity implements OnClickListener {

	private int NOTIFICATION_ID = 999;
	private int CUSTOM_NOTIFICATION_ID = 666;
	
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private Notification mCustomNotification;
	
	private int mNumberOfElements = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		// Get the NotificationManager
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// Create a PendingIntent so that the Notification knows who to call...
		Intent mainIntent = new Intent(this, MainActivity.class);
		PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0);
		
		// NOTE This creates a problem we will have to address in the manifest
		
		// Create the Normal Notification
		mNotification =  new Notification.Builder(this)
			.setContentIntent(mainPendingIntent)
			.setContentTitle("Title")
			.setContentText("Text")
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();
		
        //.setLargeIcon(R.drawable.ic_launcher)
		
		
		// Create the Custom Notification
		RemoteViews customView = new RemoteViews(getPackageName(), R.layout.notification_custom);
		
		// Create a PendingIntent for our Settings Button
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		customView.setOnClickPendingIntent(R.id.actionButton, PendingIntent.getActivity(this, 1, settingsIntent, 0));
		
		// Reuse the MainActivity PendingIntent as well
		mCustomNotification =  new Notification.Builder(this)
			.setContent(customView)
			.setContentIntent(mainPendingIntent)
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();
		
		// Wire up all the buttons
		findViewById(R.id.addToNotificationButton).setOnClickListener(this);
		findViewById(R.id.cancelNotificationButton).setOnClickListener(this);
		findViewById(R.id.sendNotificationButton).setOnClickListener(this);		
		findViewById(R.id.cancelCustomNotificationButton).setOnClickListener(this);
		findViewById(R.id.sendCustomNotificationButton).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		
			case R.id.addToNotificationButton:
				
				mNumberOfElements++;
				mNotification = new Notification.Builder(this)
					.setContentTitle("Title")
					.setContentText("Updated")
					.setSmallIcon(R.drawable.ic_launcher)
					.setNumber(mNumberOfElements)
					.getNotification();
				
				
				mNotificationManager.notify(NOTIFICATION_ID, mNotification);
				break;
			
			case R.id.cancelNotificationButton:
				mNotificationManager.cancel(NOTIFICATION_ID);
				break;
								
			case R.id.sendNotificationButton:
				mNotificationManager.notify(NOTIFICATION_ID, mNotification);
				break;
								
			case R.id.cancelCustomNotificationButton:
				mNotificationManager.cancel(CUSTOM_NOTIFICATION_ID);
				break;
				
			case R.id.sendCustomNotificationButton:
				mNotificationManager.notify(CUSTOM_NOTIFICATION_ID, mCustomNotification);
				break;
				
		}		
	}


}