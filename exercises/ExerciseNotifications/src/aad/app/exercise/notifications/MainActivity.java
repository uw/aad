package aad.app.exercise.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private static int BOOK_ID = 0;
	private static int MESSAGE_ID = 1;
	
	private NotificationManager mNotificationManager;
	private Notification mBookNotification;
	private Notification mMessageNotification;
	private MediaPlayer mMediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Wire the buttons to the click listener
		findViewById(R.id.bookButton).setOnClickListener(this);
		findViewById(R.id.messageButton).setOnClickListener(this);
		findViewById(R.id.cancelButton).setOnClickListener(this);
		
		// TODO Get the NotificationManager
		mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
			case R.id.bookButton:
				showBookNotification();
				break;
			
			case R.id.messageButton:
				showMessageNotification();
				break;
				
			case R.id.cancelButton:
				// TODO Cancel all notifications
				mNotificationManager.cancelAll();
				break;			
		}
		
	}
	
	@Override
	protected void onPause() {
		mMediaPlayer.release();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMediaPlayer = MediaPlayer.create(this, R.raw.notification);
		super.onResume();
	}

	private void playAudioNotification() {
		
		if (mMediaPlayer != null)
			mMediaPlayer.start();
	}
	
	private void showBookNotification() {
		
		// Set the content with this text
		String author = "Herman Melville";
		String title = "Moby Dick";
		
		// TODO Create a Bundle to describe the book
		Bundle b = new Bundle();
		b.putString("author", author);
		b.putString("title", title);
		
		// TODO Create the Book Intent and add the extras to it
		Intent bookIntent = new Intent(this, BookActivity.class);
		bookIntent.putExtras(b);
		
		// TODO Create the Book PendingIntent
		PendingIntent bookPendingIntent = PendingIntent.getActivity(this, BOOK_ID, bookIntent, PendingIntent.FLAG_ONE_SHOT, null);
		
		// Format the strings to use for the Notification
		String notificationTitle = String.format(getString(R.string.book_title), title);
		String notificationContent = String.format(getString(R.string.book_content), title, author);
		
		// TODO Create and use a Book Notification
		mBookNotification = new Notification.Builder(this)
			.setContentTitle(notificationTitle)
			.setContentText(notificationContent)
			.setContentIntent(bookPendingIntent)
			.setSmallIcon(R.drawable.ic_stat_book)
			.build();
		
		// TODO Use the NotificationManager to notify
		mNotificationManager.notify(BOOK_ID, mBookNotification);
		
		playAudioNotification();
	}
	
	private void showMessageNotification() {
		
		// Set the content with this text
		String message = "Hello, was it me you were looking for?";
		String sender = "Lionel Richie";
		
		// TODO Create a Bundle to describe the message
		Bundle b = new Bundle();
		b.putString("message", message);
		b.putString("sender", sender);
		
		// TODO Create the Message Intent and add the extras to it
		Intent messageIntent = new Intent(this, MessageActivity.class);
		messageIntent.putExtras(b);

		// TODO Create the Message PendingIntent
		PendingIntent messagePendingIntent = PendingIntent.getActivity(this, MESSAGE_ID, messageIntent, PendingIntent.FLAG_ONE_SHOT, null);
		
		// We might find this Bitmap useful...
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.lr);
		
		// TODO Create and use a Message Notification
		mMessageNotification = new Notification.Builder(this)
		.setContentTitle(sender)
		.setContentText(message)
		.setContentIntent(messagePendingIntent)
		.setSmallIcon(R.drawable.ic_stat_message)
		.setLargeIcon(bm)
		.build();
		
		
		// TODO Use the NotificationManager to notify
		mNotificationManager.notify(MESSAGE_ID, mMessageNotification);
		
		playAudioNotification();
	}

}
